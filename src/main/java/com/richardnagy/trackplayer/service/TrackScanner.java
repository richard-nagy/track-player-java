package com.richardnagy.trackplayer.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

import com.richardnagy.trackplayer.model.Track;
import com.richardnagy.trackplayer.model.TrackType;

public class TrackScanner {
    public static ArrayList<Path> findTracks(String path) {
        // Somehow handle spaces, and other special characters as well
        String escapedPath = path.replaceAll("\\\\", "\\\\\\\\\\\\");
        Path startPath = Paths.get(escapedPath);
        ArrayList<Path> trackFilePaths = new ArrayList<>();

        Set<String> trackTypes = Arrays.stream(TrackType.values()).map(TrackType::name).collect(Collectors.toSet());
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Path fileName = file.getFileName();

                    if (fileName != null) {
                        String nameString = fileName.toString().toUpperCase();
                        int typeSeparatorIndex = nameString.lastIndexOf(".");

                        if (typeSeparatorIndex > 0) {
                            String trackType = nameString.substring(typeSeparatorIndex + 1);

                            if (trackTypes.contains(trackType)) {
                                trackFilePaths.add(file);
                            }
                        }
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println("Error traversing directory: " + e.getMessage());
        }

        return trackFilePaths;
    }

    public static List<Track> getTrackTags(List<Path> paths) {
        ArrayList<Track> trackList = new ArrayList<Track>();

        paths.stream().forEach(path -> {
            try {
                File audioFileHandle = path.toFile();
                AudioFile audioFile = AudioFileIO.read(audioFileHandle);
                AudioHeader audioHeader = audioFile.getAudioHeader();
                Tag tag = audioFile.getTag();

                if (tag == null) {
                    System.out.println("No metadata tags found for this file.");
                    return;
                }

                String title = getTagString(tag, FieldKey.TITLE, "(Unknown)");

                String hash = calculateFileHash(path);

                int trackNumber = getTrackNumber(tag, 0);

                // List<String> tags = getAllTagStrings(tag, FieldKey.GENRE,
                // List.of("(Unknown)"));

                int lengthSeconds = audioHeader.getTrackLength();

                Artwork coverArt = tag.getFirstArtwork();
                byte[] coverArtBytes = null;

                if (coverArt != null) {
                    coverArtBytes = coverArt.getBinaryData();
                }

                trackList.add(new Track(
                        hash,
                        path,
                        title,
                        null,
                        0,
                        lengthSeconds,
                        null,
                        trackNumber,
                        coverArtBytes));

            } catch (CannotReadException
                    | IOException
                    | TagException
                    | ReadOnlyFileException
                    | InvalidAudioFrameException e) {
                System.err.println("Error reading metadata from file: " + path.toString());
                System.err.println("Specific error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        });

        return trackList;
    }

    private static String calculateFileHash(Path filePath) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = Files.readAllBytes(filePath);
        byte[] encodedHash = digest.digest(fileBytes);
        return bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static List<String> getAllTagStrings(Tag tag, FieldKey key, List<String> fallbackValue) {
        try {
            return tag.getAll(key);
        } catch (KeyNotFoundException e) {
            System.err.println("Error trying to get the key: " + e.toString());
            return fallbackValue;
        }
    }

    private static String getTagString(Tag tag, FieldKey key, String fallbackValue) {
        try {
            return tag.getFirst(key);
        } catch (KeyNotFoundException e) {
            System.err.println("Error trying to get the key: " + e.toString());
            return fallbackValue;
        }
    }

    private static int getTrackNumber(Tag tag, int fallbackValue) {
        try {
            String value = tag.getFirst(FieldKey.TRACK);

            if (value.isEmpty()) {
                return fallbackValue;
            }

            if (value.contains("/")) {
                value = value.substring(0, value.indexOf("/"));
            }

            value = value.trim();

            return Integer.parseInt(value);
        } catch (KeyNotFoundException e) {
            return fallbackValue;
        } catch (NumberFormatException e) {
            System.err.println("Track number tag value is not a valid number.");
            return fallbackValue;
        }
    }
}

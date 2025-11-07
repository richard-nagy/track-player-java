package com.richardnagy.trackplayer.model;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents a single track file in the user's library.
 * Acts as the core data structure, storing metadata and file location.
 * Uses fileHash as the unique identifier.
 */
public class Track {
    /** Acts as the <b>unique identifier<b>. */
    private String fileHash;

    /** Path of the file. */
    private Path filePath;
    /** Title of the track. */
    private String title;
    /** Length of the track in seconds. */
    private int lengthSeconds;
    /** Number of the tracks order in it's album. */
    private int trackNumber;
    /** Raw image data of the cover art (byte array). */
    private byte[] coverArtData;

    /** ID of the album. */
    private int albumId;
    /** IDs of all contributing artists. */
    private ArrayList<Integer> artistIds;
    /** IDs of the track's genres. */
    private ArrayList<Integer> genreIds;

    public Track(
            String hash, Path path, String title, ArrayList<Integer> artistIds, int albumId, int length,
            ArrayList<Integer> genreIds, int number, byte[] coverArt) {
        this.fileHash = hash;
        this.filePath = path;
        this.title = title;
        this.artistIds = artistIds;
        this.albumId = albumId;
        this.lengthSeconds = length;
        this.genreIds = genreIds;
        this.trackNumber = number;
        this.coverArtData = coverArt;
    }
}

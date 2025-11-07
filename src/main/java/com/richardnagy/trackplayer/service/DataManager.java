package com.richardnagy.trackplayer.service;

import java.util.HashMap;
import java.util.Map;

import com.richardnagy.trackplayer.model.Album;
import com.richardnagy.trackplayer.model.Artist;
import com.richardnagy.trackplayer.model.Track;

public class DataManager {
    private final Map<String, Track> trackMap = new HashMap<>();
    private final Map<String, Artist> artistMap = new HashMap<>();
    private final Map<String, Album> albumMap = new HashMap<>();

    // public void loadDataFromDatabase() {
    // // Method logic to connect to DB and populate trackMap...
    // }

    // public Map<Integer, Track> getAllTrackMap() {
    // return Collections.unmodifiableMap(trackMap);
    // }

    // public Map<Integer, Track> getAllArtistMap() {
    // return Collections.unmodifiableMap(artistMap);
    // }

    // public Map<Integer, Track> getAllAlbumMap() {
    // return Collections.unmodifiableMap(albumMap);
    // }
}
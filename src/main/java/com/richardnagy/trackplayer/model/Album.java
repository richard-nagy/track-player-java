package com.richardnagy.trackplayer.model;

import java.util.ArrayList;

/** Represents a single album entity, linked to an Artist. */
public class Album extends MetadataEntity {
    /** Release year of the album. */
    private int releaseYear;
    /** IDs of all contributing artists. */
    private ArrayList<Integer> artistIds;

    public Album(int id, String title, int releaseYear, ArrayList<Integer> artistIds) {
        super(id, title);
        this.releaseYear = releaseYear;
        this.artistIds = artistIds;
    }
}

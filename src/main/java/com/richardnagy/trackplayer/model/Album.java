package com.richardnagy.trackplayer.model;

import java.util.ArrayList;

public class Album {
    /** Identifier of the album. */
    private int id;

    /** Title of the album. */
    private String title;
    /** Release year of the album. */
    private int releaseYear;

    /** IDs of all contributing artists. */
    private ArrayList<Integer> artistIds;

    public Album(int id, String title, int releaseYear, ArrayList<Integer> artistIds) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.artistIds = artistIds;
    }
}

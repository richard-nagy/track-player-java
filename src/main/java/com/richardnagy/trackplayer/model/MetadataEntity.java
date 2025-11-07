package com.richardnagy.trackplayer.model;

/** Superclass for all entities that have a unique ID and a display a name. */
public class MetadataEntity {
    /** Identifier of the entity. */
    private int id;
    /** Name of the entity. */
    private String name;

    public MetadataEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

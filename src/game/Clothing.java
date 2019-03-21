package com.yamizee.discordbots.game;

/**
 * Created by YamiZee on 7/31/2016.
 */

//import org.apache.commons.lang.WordUtils;

public class Clothing extends Item {

    private int defense;

    private final Bearing.Slot[] slots;

    public Clothing (Bearing.Slot[] slots) {
        this.slots = slots;
    }
    public Clothing (String id, Bearing.Slot[] slots) {
        setId(id);
        setName(id);
        this.slots = slots;
        setDescriptor("");
    }
    public Clothing (String id, Bearing.Slot[] slots, String descriptor) {
        setId(id);
        setName(id);
        this.slots = slots;
        setDescriptor("");
    }
    public Clothing (String id, String name, Bearing.Slot[] slots, String descriptor) {
        setId(id);
        setName(name);
        this.slots = slots;
        setDescriptor("");
    }

    public Bearing.Slot[] getSlots () {
        return slots;
    }

    public int getDefense() {
        return defense;
    }
}
package com.yamizee.discordbots.game;

import com.iwebpp.crypto.TweetNaclFast;
import com.yamizee.discordbots.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YamiZee on 7/31/2016.
 */

public class Bearing {

    private HashMap<Slot, Clothing> wearing = new HashMap<>();
    private HashMap<Slot, Item> holding = new HashMap<>();
    private ArrayList<Item> carrying = new ArrayList<>();
    private HashMap<Slot, Slot> opposite = new HashMap<>();
    private PlayerInterface player;

    public Bearing (PlayerInterface player) {
        this.player = player;
        opposite.put(Slot.GRIPL, Slot.GRIPR);
        opposite.put(Slot.GRIPR, Slot.GRIPL);
    }

    //get item from specified slot (both wearing and holding)
    public Item getItem (Slot slot) {
        switch (slot.getType()) {
            case WEARING: return wearing.get(slot);
            case HOLDING: return holding.get(slot);
        }
        return null;
    }
    //set an item to a slot (both wearing and holding)
    private void setItem (Slot slot, Item item) { //dangerous function, dont unprivate
        switch (slot.getType()) {
            case WEARING:
                wearing.put(slot, (Clothing) item);
            break;
            case HOLDING:
                holding.put(slot, item);
            break;
        }
    }
    //add item to inventory
    public void addItem (Item item) {
        carrying.add(item);
    }

    //WEARING FUNCTIONS
    public boolean wear (String idname) {
        //go through holding & carrying to see if there is clothing that matches the idname
        Map.Entry<Slot, Item> entry = getFromHolding(idname);
        if (entry.getValue() instanceof Clothing) {
            wear((Clothing) entry.getValue());
            holding.remove(entry.getKey());
            return true;
        }
        Item item = getFromCarrying(idname);
        if (item instanceof Clothing) {
            wear((Clothing) item);
            carrying.remove(item);
            return true;
        }
        return false;
    }
    private void wear (Clothing cloth) {
        //go through slots
        for (Slot slot : cloth.getSlots()) {
            //if a slot is already occupied, remove the occupying clothing
            if (wearing.containsKey(slot)) {
                strip(slot);
            }
            //add the new clothing
            setItem(slot, cloth);
            Main.messageHandler.sendMessage(player.getName() + " now wearing " + cloth.getName() + ".");
        }
    }
    public boolean strip (String idname) {
        Clothing cloth = getFromWearing(idname).getValue();
        if (cloth != null) {
            strip(cloth);
            return true;
        }
        return false;
    }
    private void strip (Clothing cloth) {
        strip(cloth.getSlots()[0]);
    }
    private void strip (Slot slot) {
        addItem(extract(slot));
        Main.messageHandler.sendMessage(player.getName() + " no longer wearing " + wearing.get(slot).getName() + ".");
    }

    //HOLDING FUNCTIONS
    public void hold (String idname) {
        Item item = getFromCarrying(idname);
        hold(item);
        carrying.remove(item);
    }
    public void hold (String idname, Slot slot) {
        Item item = getFromCarrying(idname);
        hold(item, slot);
        carrying.remove(item);
    }
    public void hold (Item item) {
        if (item instanceof Weapon && ((Weapon) item).isTwoHanded()) {
            extract(Slot.GRIPL);
            setItem(Slot.GRIPL, item);
            setItem(Slot.GRIPR, item);
            Main.messageHandler.sendMessage(player.getName() + " now holding " + item.getName() + ".");
        }else if (!holding.containsKey(Slot.GRIPR)) {
            hold (item, Slot.GRIPR);
        }else if (!holding.containsKey(Slot.GRIPL)){
            hold (item, Slot.GRIPL);
        }
    }
    public void hold (Item item, Slot slot) { //WIPWIPWIPWIPW
        if (item instanceof Weapon && ((Weapon) item).isTwoHanded()) {
            hold (item);
        }else{
            if (holding.containsKey(slot)) {
                if (holding.containsKey(opposite.get(slot))) {
                    unhold(slot);
                } else {
                    if (holding.get(slot) instanceof Weapon && ((Weapon) holding.get(slot)).isTwoHanded()) {
                        unhold(slot);
                    }else{
                        //swap hands
                        holding.put(opposite.get(slot), holding.get(slot));
                        holding.remove(slot);
                    }
                }
            }
            setItem(slot, item);
            Main.messageHandler.sendMessage(player.getName() + " now holding " + item.getName() + ".");
        }
    }
    public void unhold (Slot slot) {
        Item item = extract(slot);
        carrying.add(item);
        Main.messageHandler.sendMessage(player.getName() + " no longer holding " + item.getName() + ".");
    }
    public void unhold (String id) {
        if (holding.get(Slot.GRIPL).getId().equals(id)) {
            unhold(Slot.GRIPL);
        }else if (holding.get(Slot.GRIPR).getId().equals(id)) {
            unhold(Slot.GRIPR);
        }
    }
    public void unhold (Item item) {
        if (holding.get(Slot.GRIPL) == item) {
            unhold(Slot.GRIPL);
        }else if (holding.get(Slot.GRIPR) == item) {
            unhold(Slot.GRIPR);
        }
    }
    public void swapHands () {
        Item temp = holding.get(Slot.GRIPR);
        holding.put(Slot.GRIPR, holding.get(Slot.GRIPL));
        holding.put(Slot.GRIPL, temp);
        Main.messageHandler.sendMessage(player.getName() + " swapped hands. ");
    }
    //primary purpose is to remove items completely, like multi slotted clothing
    public Item extract (Slot slot) {
        Item item = null;
        switch (slot.getType()) {
            case HOLDING:
                item = holding.get(slot);
                if (item instanceof Weapon && ((Weapon) item).isTwoHanded()) {
                    holding.clear();
                }else{
                    holding.remove(slot);
                }
                break;
            case WEARING:
                item = wearing.get(slot);
                if (item != null) {
                    for (Slot tempSlot : ((Clothing) item).getSlots()) {
                        wearing.remove(tempSlot);
                    }
                }
                break;
        }
        return item;
    }
    //goes through all items and extracts first item found
    public Item extract (String idname) {
        Map.Entry<Slot, Item> holdEntry = getFromHolding(idname);
        Item carryItem = getFromCarrying(idname);
        Map.Entry<Slot, Clothing> wearEntry = getFromWearing(idname);
        if (holdEntry != null) {
            return extract(holdEntry.getKey());
        }else if (carryItem != null) {
            carrying.remove(carryItem);
            return carryItem;
        }else if (wearEntry != null) {
            return extract(wearEntry.getKey());
        }
        return null;
    }

    //GET ITEMS / ENTRIES BY ID
    public Map.Entry<Slot, Clothing> getFromWearing (String idname) {
        for (Map.Entry<Slot, Clothing> entry : wearing.entrySet()) {
            if (entry.getValue().getId().equals(idname)) {
                return entry;
            }
        }
        return null;
    }
    public Map.Entry<Slot, Item> getFromHolding (String idname) {
        for (Map.Entry<Slot, Item> entry : holding.entrySet()) {
            if (entry.getValue().getId().equals(idname)) {
                return entry;
            }
        }
        return null;
    }
    public Item getFromCarrying (String idname) {
        for (Item item : carrying) {
            if (item.getId().equals(idname)) {
                return item;
            }
        }
        return null;
    }

    public HashMap<Slot, Clothing> getWearing() {
        return wearing;
    }
    public HashMap<Slot, Item> getHolding() {
        return holding;
    }
    public ArrayList<Item> getCarrying() {
        return carrying;
    }

    public enum Slot {

        GRIPL (Type.HOLDING),  //left hand holding
        GRIPR (Type.HOLDING),  //right hand holding
        HANDS (Type.WEARING),  //gloves
        HEAD1 (Type.WEARING),  //in hair
        HEAD2 (Type.WEARING),  //on hair
        HEAD3 (Type.WEARING),  //hat
        SHIRT1 (Type.WEARING), //under-shirt
        SHIRT2 (Type.WEARING), //shirt
        SHIRT3 (Type.WEARING), //coat shirt
        PANTS1 (Type.WEARING), //underwear
        PANTS2 (Type.WEARING), //pants
        PANTS3 (Type.WEARING), //skirt? (a dress takes up shirt3 and pants3 for example)
        FEET1 (Type.WEARING),  //socks
        FEET2 (Type.WEARING);   //shoes

        private Type type;

        Slot (Type type) {
            this.type = type;
        }
        Type getType () {
            return type;
        }
        enum Type {
            WEARING,
            HOLDING
        }



    }
}

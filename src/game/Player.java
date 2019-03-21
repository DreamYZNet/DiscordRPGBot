package com.yamizee.discordbots.game;

import com.yamizee.discordbots.Main;

import java.awt.*;
import java.util.Map;

/**
 * Created by YamiZee on 6/3/2016.
 */
public class Player extends Monster implements PlayerInterface {

    private Bearing bearing = new Bearing(this);

    private String id;
    private String discordId;
    private String name;
    private int level = 1;
    private int exp = 0;
    private int health = 100;
    Color color = null;

    public Player () {

    }

    public Player (String id, String discordId, String name) {
        this.id = id;
        this.name = name;
        this.discordId = discordId;
    }

    //Gain exp and calculate levels gained
    public void gainExp (int amount) {
        exp += amount;
        boolean looping = true;
        int count = 0;
        while (looping) {
            if (exp >= getStatForLevel(level+count, "exp")) {
                count++;
            }else{
                gainLevel(count);
                looping = false;
            }
        }
    }

    public void gainLevel(int amount) {
        if (amount > 0) {
            String output = "";
            level += amount;
            output += name + " has gained " + amount + " levels, and is now level " + level + "!!\n";
            int atk = getAttack();
            int def = getDefense();
            int mhp = getMaxHealth();
            setAttack( getStatForLevel(level, "atk") );
            setDefense( getStatForLevel(level, "def") );
            setMaxHealth( getStatForLevel(level, "maxhp") );
            setHealth(getMaxHealth());
            output += "Atk: "+atk+" -> "+getAttack()+" | ";
            output += "Def: "+def+" -> "+getDefense()+" | ";
            output += "Max HP: "+mhp+" -> "+getMaxHealth()+" ";
            Main.messageHandler.sendMessage(output);
        }
    }

    @Override
    public int getStatForLevel (int level, String stat) {
        switch (stat) {
            case "exp":
                return getStatForLevel(level, 100, 0, 1, 0);
            case "atk":
            case "attack":
                return getStatForLevel(level, 5, 0, 0, 1.2);
            case "def":
            case "defense":
                return getStatForLevel(level, 6, 0, 0, 1.18);
            case "maxhp":
            case "maxHealth":
                return getStatForLevel(level, 5, 5, 0, 0);
        }
        return 0;
    }
    @Override
    public int getAttack () {
        int bonus = 0;
        for (Map.Entry<Bearing.Slot, Item> entry : bearing.getHolding().entrySet()) {
            if (entry.getValue() instanceof Weapon)
                bonus += ((Weapon) entry.getValue()).getDamage();
        }
        return super.getAttack() * bonus;
    }
    @Override
    public int getDefense () {
        int bonus = 0;
        for (Map.Entry<Bearing.Slot, Clothing> entry : bearing.getWearing().entrySet()) {
            bonus += entry.getValue().getDefense();
        }
        return super.getDefense() * bonus;
    }

    public void setDiscordId (String id)    { this.discordId = id;   }
    public void setLevel (int level)    { this.level = level;   }
    public void setExp (int exp)        { this.exp = exp;       }
    public void setColor (Color color)  { this.color = color; }

    public String getDiscordId () { return discordId;   }
    public int getLevel ()   { return level;   }
    public int getExp ()     { return exp;       }
    public Bearing getBearing() { return bearing; }
    public Color getColor() { return color; }

}

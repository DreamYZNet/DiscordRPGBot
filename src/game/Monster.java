package com.yamizee.discordbots.game;

import com.yamizee.discordbots.Main;

import java.util.Map;

/**
 * Created by YamiZee on 6/3/2016.
 */
public class Monster implements MonsterInterface {

    private String name;
    private String id;
    private int maxHealth = 8;
    private int health = maxHealth;
    private int attack = 5;
    private int defense = 2;
    private int givesExp = 100;

    public Monster () {

    }
    public Monster (String name) {
        this.name = name;
    }

    public void attack (Monster monster) {
        if (Main.gameHandler.game().getNotMovedYet().contains(this)) {
            Main.messageHandler.sendMessage(name + " attacked " + monster.getName() + "!");
            monster.takeDamage(getAttack());
            Main.gameHandler.game().getNotMovedYet().remove(this);
            Main.gameHandler.game().getAlreadyMoved().add(this);
        }
    }

    public void takeDamage (int damage) {
        damage = calculateDamage(damage);
        if (damage > 0) {
            Main.messageHandler.sendMessage(name + " took "+damage+ " damage! ");
            incHealth(-damage);
            //Main.messageHandler.sendMessage(name + " has "+ health +" health left. ");
        }
    }

    public int calculateDamage (int damage) {
        damage -= getDefense();
        return damage;
    }

    public void incHealth (int amount) {
        health += amount;
        if (health <= 0)
            die();
    }

    public void die () {
        Main.gameHandler.game().removeMonster(this);
        Main.messageHandler.sendMessage(name + " has died! ");
        Main.gameHandler.game().distributeExp(givesExp);
    }

    public void rename (String name) {
        setName(name);
        setId(name.replaceAll("\\s", "").toLowerCase());
    }

    //LEVEL CALCS
    public static int getStatForLevel (int level, int base, int additive, int multiplicative, double exponential) {
        level -= 1;
        int stat = 0;
        stat += base * additive * level;//add (requires base*additive more)
        stat += additiveThing(base, level) * multiplicative;//multi (requires base*level more)
        if (exponential != 0)
            stat += base * Math.pow(exponential, level);//expo (requires base*expo more)
        return stat;
    }
    private static int additiveThing (int amount, int times) {
        if (times == 0) return 0;
        return amount*times + additiveThing(amount, times-1);
    }
    public String printScaling (String  stat, int min, int max) {
        if (getStatForLevel(20, stat) != 0) {
            String output = stat;
            for (int i = min; i <= max; i++) {
                output += "\nLevel " + i + ": " + getStatForLevel(i, stat);
            }
            return output;
        }
        return "Invalid stat.";
    }
    public int getStatForLevel (int level, String stat) {
        switch (stat) {
            case "atk":
            case "attack":
                return attack;
            case "def":
            case "defense":
                return defense;
            case "maxhp":
            case "maxHealth":
                return maxHealth;
        }
        return 0;
    }

    public String getId ()    { return id;   }
    public String getName ()  { return name;   }
    public int getHealth()      { return health;    }
    public int getMaxHealth()   { return maxHealth; }
    public int getAttack()      { return attack;    }
    public int getDefense()     { return defense;   }
    public int givesExp()       { return givesExp;  }
    public void setId (String id)        { this.id = id;        }
    public void setName (String name)    { this.name = name;   }
    public void setHealth(int hp)       { health = hp;          }
    public void setMaxHealth(int maxHp) { maxHealth = maxHp;    }
    public void setAttack(int atk)      { attack = atk;         }
    public void setDefense(int def)     { defense = def;        }

}

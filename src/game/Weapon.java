package com.yamizee.discordbots.game;

/**
 * Created by YamiZee on 8/8/2016.
 */
public class Weapon extends Item {

    private boolean twoHanded = false;
    private double damage = 1;

    public Weapon (int damage, boolean twoHanded) {
        this.damage = damage;
        this.twoHanded = twoHanded;
    }

    public boolean isTwoHanded()    { return twoHanded;    }
    public double getDamage()       { return damage;        }
    public void setTwoHanded(boolean twohanded) { this.twoHanded = twohanded;   }
    public void setDamage(double damage)        { this.damage = damage;          }
}

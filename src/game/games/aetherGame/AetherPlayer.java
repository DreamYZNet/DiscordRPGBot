package com.yamizee.discordbots.game.games.aetherGame;

import com.yamizee.discordbots.game.Bearing;
import com.yamizee.discordbots.game.Player;
import com.yamizee.discordbots.game.PlayerInterface;

import java.awt.*;

/**
 * Created by YamiZee on 8/17/2016.
 */
public class AetherPlayer extends Player {

    @Override
    public int calculateDamage (int damage) {
        if (damage <= 0) return 0;
        System.out.print("damage"+damage +" defense"+getDefense()+ " ");
        damage = 100-((getDefense() / damage)*100);
        System.out.println(damage);
        return damage;
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
                return getStatForLevel(level, 6, 0, 0, 1.15);
            case "maxhp":
            case "maxHealth":
                return getStatForLevel(level, 5, 1, 0, 0) + 100;
        }
        return 0;
    }

}

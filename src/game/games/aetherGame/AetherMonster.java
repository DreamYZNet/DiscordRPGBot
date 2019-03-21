package com.yamizee.discordbots.game.games.aetherGame;

import com.yamizee.discordbots.game.Monster;

/**
 * Created by YamiZee on 8/17/2016.
 */
public class AetherMonster extends Monster {

    @Override
    public int calculateDamage (int damage) {
        if (damage <= 0) return 0;
        System.out.print("damage"+damage +" defense"+getDefense()+ " ");
        damage = 100-((getDefense() / damage)*100);
        System.out.println(damage);
        return damage;
    }

}

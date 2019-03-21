package com.yamizee.discordbots.game;

/**
 * Created by YamiZee on 8/17/2016.
 */
public interface MonsterInterface {

    String getId ();
    String getName ();
    int getHealth();
    int getMaxHealth();
    int getAttack();
    int getDefense();
    int givesExp();
    void setId (String id);
    void setName (String name);
    void setHealth(int hp);
    void setMaxHealth(int maxHp);
    void setAttack(int atk);
    void setDefense(int def);

}

package com.yamizee.discordbots.game;

import java.awt.*;

/**
 * Created by YamiZee on 8/17/2016.
 */
public interface PlayerInterface extends MonsterInterface {

    void gainExp (int amount);
    void gainLevel(int amount);

    void setDiscordId (String id);
    void setLevel (int level);
    void setExp (int exp);
    void setColor (Color color);

    String getDiscordId ();
    int getLevel ();
    int getExp ();
    Color getColor();
    Bearing getBearing();

}

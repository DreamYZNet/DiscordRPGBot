package com.yamizee.discordbots.game.games.aetherGame;

import com.yamizee.discordbots.game.Monster;
import com.yamizee.discordbots.game.Player;
import com.yamizee.discordbots.game.games.Game;
import net.dv8tion.jda.entities.User;

/**
 * Created by YamiZee on 8/13/2016.
 */
public class AetherGame extends Game {

    @Override
    public Player getNewPlayer () {
        return new AetherPlayer();
    }
    @Override
    public Monster getNewMonster () {
        return new AetherMonster();
    }

}

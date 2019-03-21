package com.yamizee.discordbots.game;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.commands.*;
import com.yamizee.discordbots.game.games.aetherGame.AetherGame;
import com.yamizee.discordbots.game.games.Game;

import java.util.*;

/**
 * Created by YamiZee on 6/3/2016.
 */
public class GameHandler {

    private Game game;
    public DataHandler dataHandler = new DataHandler();

    private static LinkedHashMap<String, Command> commands = new LinkedHashMap <String, Command>();

    public GameHandler () {
        commands.put("load", new LoadCommand());
        commands.put("exit", new UnloadCommand());
        loadCommands(commands);
    }

    public Game game() {
        return game;
    }

    public void load (String gameName) {
        unload();
        switch (gameName) {
            case "game":
                game = new Game();
                Main.messageHandler.sendMessage("Game loaded. ");
                break;
            case "dream":
                game = new AetherGame();
                Main.messageHandler.sendMessage("Dream loaded. ");
                break;
        }
        if (game != null) {
            loadCommands(game.getCommands());
        }
    }
    public void unload () {
        if (game != null) {
            game.terminate();
            unloadCommands(game.getCommands());
            game = null;
            Main.messageHandler.sendMessage("Game unloaded. ");
        }
    }
    private void loadCommands (Map <String, Command> commands) {
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            Main.commands.put(entry.getKey(), entry.getValue());
        }
    }
    private void unloadCommands (Map <String, Command> commands) {
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            Main.commands.remove(entry.getKey());
        }
    }


    public LinkedHashMap<String, Command> getCommands () {
        return commands;
    }

}





































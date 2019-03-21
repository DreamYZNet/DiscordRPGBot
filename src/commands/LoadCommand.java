package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 8/13/2016.
 */
public class LoadCommand extends Command {

    public LoadCommand() {
        USAGE = "load [game]";
        DESCRIPTION = "Load specified game.";
        REQUIRED = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Main.gameHandler.load(args[0]);

        return true;
    }
}

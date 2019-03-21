package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 6/9/2016.
 */
public class ScaleCommand extends Command {

    public ScaleCommand() {
        REQUIRED = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Main.messageHandler.sendMessage(Main.gameHandler.game().getPlayer(event.getAuthor()).printScaling(args[0], 1, 20));

        return true;
    }
}

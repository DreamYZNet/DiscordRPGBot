package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 6/1/2016.
 */
public class EchoCommand extends Command {

    public EchoCommand() {
        USAGE = "echo [message...]";
        DESCRIPTION = "Sends a message.";
        HELP = "Usage: " + USAGE + "\nDescription: " + DESCRIPTION;
        REQUIRED = 1;
        TRAIL = true;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {
        Main.messageHandler.sendMessage(args[0]);
        return true;
    }
}

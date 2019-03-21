package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 5/30/2016.
 */
public class PingCommand extends Command {

    public PingCommand() {
        USAGE = "ping";
        DESCRIPTION = "Returns pong.";
        HELP = "Usage: " + USAGE + "\nDescription: " + DESCRIPTION;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {
        Main.messageHandler.sendMessage("PONG");
        return true;
    }

}

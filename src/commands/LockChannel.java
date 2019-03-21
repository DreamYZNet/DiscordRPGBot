package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 6/3/2016.
 */
public class LockChannel extends Command {


    public LockChannel() {
        USAGE = "lock";
        DESCRIPTION = "Locks all bot output to the current channel.";
        ADMIN = true;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {
        Main.messageHandler.setChannelLocked(false);
        Main.messageHandler.setChannel(event.getTextChannel());
        Main.messageHandler.setChannelLocked(true);
        Main.messageHandler.sendMessage("Channel locked.");
        return true;
    }
}

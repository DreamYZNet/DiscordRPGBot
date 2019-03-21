package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 6/3/2016.
 */
public class UnlockChannel extends Command {

    public UnlockChannel() {
        USAGE = "unlock";
        DESCRIPTION = "Unlocks bot channel output.";
        ADMIN = true;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {
        Main.messageHandler.setChannelLocked(false);
        Main.messageHandler.setChannel(event.getTextChannel());
        Main.messageHandler.sendMessage("Channel unlocked.");
        return true;
    }

}

package com.yamizee.discordbots;

import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.events.Event;

/**
 * Created by YamiZee on 6/3/2016.
 */
public class MessageHandler {

    private boolean holding = true;
    private String heldOutput = "";

    private TextChannel channel;
    private boolean channelLocked = false;
    private boolean printInCode = true;

    private boolean deleteMessages = true;
    private boolean deleteAllMessages = false;

    public void sendMessage (String message) {
        heldOutput += message + "\n";
        if (holding == false) {
            flush();
        }
    }
    public void sendMessageNow (String message) {
        channel.sendMessage(message);
    }
    public void flush () {
        if (!heldOutput.equals("")) {
            try {
                if (printInCode) {
                    heldOutput = "```\n"+heldOutput+"```";
                }
                channel.sendMessage(heldOutput);
            } catch (Exception e) {
                channel.sendMessage("Error: Message too large!");
            }
            heldOutput = "";
        }
    }

    public void setChannel(TextChannel channel) {
        if (!channelLocked) {
            this.channel = channel;
        }
    }

    public void deleteMessage (Message message) {
        if (deleteMessages == true) {
            message.deleteMessage();
        }
    }

    public void setDeleteMessages (boolean bool) {
        deleteMessages = bool;
    }
    public boolean isChannelLocked() {
        return channelLocked;
    }
    public void setChannelLocked (boolean bool) {
        channelLocked = bool;
    }
    public boolean getDeleteAllMessages () { return deleteAllMessages; }

}

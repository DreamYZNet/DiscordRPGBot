package com.yamizee.discordbots.graveyard;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 5/30/2016.
 */
public interface CommandO {

    public boolean called (String[] args, MessageReceivedEvent event);
    public void action (String[] args, MessageReceivedEvent event);
    public String help();
    public void executed (boolean success, MessageReceivedEvent event);

}

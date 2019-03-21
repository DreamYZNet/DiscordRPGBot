package com.yamizee.discordbots;

import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 * Created by YamiZee on 5/30/2016.
 */
public class BotListener extends ListenerAdapter {

    @Override
    //When a message is sent to a channel the bot is participating in
    public void onMessageReceived(MessageReceivedEvent event) {
        Main.handleEvent (event);
    }

    @Override
    public void onReady (ReadyEvent event) {
        //Main.log("status", "Logged in as: " + event.getJDA().getSelfInfo().getUsername());
    }

}

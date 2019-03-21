package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.Event;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 8/8/2016.
 */
public class NukeCommand extends Command implements Runnable {

    MessageReceivedEvent event;

    public NukeCommand () {
        USAGE = "nuke";
        ADMIN = true;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        this.event = event;
        new Thread(this).start();

        return true;
    }

    public void run() {
        int count = 0;
        while (event.getChannel().getHistory().retrieveAll().size() > 1) {
            int size = event.getChannel().getHistory().retrieveAll().size();
            //delete last 100 messages
            if (size >= 100) {
                event.getTextChannel().deleteMessages(event.getChannel().getHistory().retrieve(100));
                count += 100;
            }else{
                event.getTextChannel().deleteMessages(event.getChannel().getHistory().retrieve(size));
                count += size;
            }
            Main.messageHandler.sendMessageNow(count + " messages deleted...");
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.out.println("Err something went wrong...");
            }
        }
        Main.messageHandler.sendMessageNow("Channel Nuked. ");
    }

}

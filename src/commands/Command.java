package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 5/30/2016.
 */

public abstract class Command {

    protected String USAGE = "N/A";
    protected String DESCRIPTION = "N/A";
    protected String HELP = "N/A";
    protected int REQUIRED = 0;
    protected int OPTIONAL = 0;
    protected boolean TRAIL = false;
    protected boolean ADMIN = false;

    //consider removing args requirement. also forcing usage reqs in this too, with either false bool or throw exception
    public abstract boolean execute (String[] args, MessageReceivedEvent event);

    public String getHelp() {
        if (HELP.equals("N/A"))
            return "Usage: " + USAGE + "\nDescription: " + DESCRIPTION;
        return HELP;
    }
    public String getUsage() {
        return USAGE;
    }
    public String getDescription() { return DESCRIPTION; }
    public int getRequired() {
        return REQUIRED;
    }
    public int getOptional() { return OPTIONAL; }
    public boolean hasTrail() {
        return TRAIL;
    }
    public boolean requireAdmin() { return ADMIN; }

    public String[] getArgs (MessageReceivedEvent event) {
        try {
            String[] message = event.getMessage().getContent().split(" ", 2);
            return message[1].split(" ", REQUIRED + OPTIONAL);
        }catch (Exception e){
            return null;
        }
    }

    //Returns the first word of message without the prefix
    public static String getCommandStr (MessageReceivedEvent event) {
        String commandStr = event.getMessage().getContent();
        commandStr = commandStr.split(" ", 2)[0];
        commandStr = commandStr.substring(Main.prefix.length());
        return commandStr;
    }

}

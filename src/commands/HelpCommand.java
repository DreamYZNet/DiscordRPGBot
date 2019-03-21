package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;

/**
 * Created by YamiZee on 6/1/2016.
 */
public class HelpCommand extends Command {

    public HelpCommand () {
        USAGE = "help <command>";
        DESCRIPTION = "Returns helpful information.";
        HELP =  "Usage: " + USAGE + "\nDescrption: " + DESCRIPTION;
        OPTIONAL = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        if (args == null) {
            printAll(event);
        }else if (Main.commands.containsKey(args[0])){
            printHelp(event, args[0]);
        }else{
            return false;
        }

        return true;
    }

    private void printAll (MessageReceivedEvent event) {
        String output = "";
        for (Map.Entry<String, Command> entry : Main.commands.entrySet()) {
            output += entry.getValue().getUsage() + " - " + entry.getValue().getDescription() + "\n";
        }
        Main.messageHandler.sendMessage(output);
    }
    private void printHelp (MessageReceivedEvent event, String commandName) {
        if (Main.commands.containsKey(commandName) ) {
            Main.messageHandler.sendMessage( Main.commands.get(commandName).getHelp() );
        }
    }


}

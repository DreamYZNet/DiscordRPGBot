package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Item;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 7/31/2016.
 */
public class WearCommand extends Command {

    public WearCommand() {
        USAGE = "wear [idname]";
        REQUIRED = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        if (player.getBearing().wear(args[0]) == true) {
            //Main.messageHandler.sendMessage(player.getName() + " now wearing " + args[0] + ".");
            Main.messageHandler.deleteMessage(event.getMessage());
        }else{
            Main.messageHandler.sendMessage("No such clothing found. ");
        }

        return true;
    }
}

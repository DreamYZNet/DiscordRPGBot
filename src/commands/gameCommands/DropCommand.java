package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Item;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 8/9/2016.
 */
public class DropCommand extends Command {

    public DropCommand () {
        USAGE = "drop [idname]";
        REQUIRED = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        Item item = player.getBearing().extract(args[0]);
        if (item == null) {
            Main.messageHandler.sendMessage("No such item. ");
            return false;
        }
        Main.messageHandler.sendMessage(player.getName() + " dropped " + item.getName() + ".");
        Main.messageHandler.deleteMessage(event.getMessage());
        return true;

    }
}

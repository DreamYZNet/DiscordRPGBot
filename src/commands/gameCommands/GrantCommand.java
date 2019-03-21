package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Item;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 7/31/2016.
 */
public class GrantCommand extends Command {

    public GrantCommand() {
        USAGE = "grant [player] [idname]";
        REQUIRED = 2;
        ADMIN = true;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(args[0]);
        Item item = Main.gameHandler.dataHandler.loadItem(args[1]);
        player.getBearing().addItem(item);
        Main.messageHandler.sendMessage(player.getName() + " received " + item.getName() + ".");
        Main.messageHandler.deleteMessage(event.getMessage());

        return true;
    }
}

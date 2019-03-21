package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Bearing;
import com.yamizee.discordbots.game.Clothing;
import com.yamizee.discordbots.game.Item;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;

/**
 * Created by YamiZee on 8/9/2016.
 */
public class GiveCommand extends Command {

    public GiveCommand () {
        USAGE = "give [player] [idname]";
        REQUIRED = 2;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {
        Player giving = Main.gameHandler.game().getPlayer(event.getAuthor());
        Player receiving = Main.gameHandler.game().getPlayer(args[0]);
        Item item = giving.getBearing().extract(args[1]);
        Main.messageHandler.deleteMessage(event.getMessage());
        Main.messageHandler.sendMessage(giving.getName() +" gave " +receiving.getName() +" " +item.getName() +". ");
        if (item != null) {
            receiving.getBearing().addItem(item);
        }else{
            Main.messageHandler.sendMessage("No such item. ");
            return false;
        }
        return true;
    }
}

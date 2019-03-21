package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Bearing;
import com.yamizee.discordbots.game.Clothing;
import com.yamizee.discordbots.game.Item;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YamiZee on 7/31/2016.
 */
public class DisplayInventoryCommand extends Command {

    public DisplayInventoryCommand() {
        USAGE = "inventory";
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        HashMap<Bearing.Slot, Clothing> wearing = player.getBearing().getWearing();
        HashMap<Bearing.Slot, Item> holding = player.getBearing().getHolding();
        ArrayList<Item> carrying = player.getBearing().getCarrying();
        String output = "";
        output += player.getName();

        if (holding.size() > 0) {
            output += "\n";
            if (holding.get(Bearing.Slot.GRIPL) != null) {
                output += "Left-hand: " + holding.get(Bearing.Slot.GRIPL).getName() + ", ";
            }
            if (holding.get(Bearing.Slot.GRIPR) != null) {
                output += "Right-Hand: " + holding.get(Bearing.Slot.GRIPR).getName() + ", ";
            }
        }

        output += "\nWearing: ";
        for (Map.Entry<Bearing.Slot, Clothing> entry : wearing.entrySet()) {
            output += (entry.getValue().getName()) + ", ";
        }

        output += "\nCarrying: ";
        for (Item item : carrying) {
            output += (item.getName()) + ", ";
        }
        Main.messageHandler.sendMessage(output);
        return true;
    }
}

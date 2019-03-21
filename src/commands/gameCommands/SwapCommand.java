package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Bearing;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 8/9/2016.
 */
public class SwapCommand extends Command {

    public SwapCommand() {
        USAGE = "swap [hands]";
        REQUIRED = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        switch (args[0]) {
            case "swap":
                player.getBearing().swapHands();
            break;
            default:
                Main.messageHandler.sendMessage("That can't be swapped. ");
        }

        return true;
    }
}

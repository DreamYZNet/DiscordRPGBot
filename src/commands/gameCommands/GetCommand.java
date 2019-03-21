package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 6/3/2016.
 */
public class GetCommand extends Command {

    public GetCommand() {
        USAGE = "get [stat] <user>";
        DESCRIPTION = "Returns the stat value.";
        REQUIRED = 1;
        OPTIONAL = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        if (args.length == 1) {
            //player = Main.gameHandler.getPlayer(event.getAuthor());
        }else if (args.length == 2) {
            player = Main.gameHandler.game().getPlayer(args[1].toLowerCase());
        }

        switch (args[0]) {
            case "hp":
            case "health":
                event.getTextChannel().sendMessage( Integer.toString(player.getHealth()) );
            break;
            case "maxHeath":
                event.getTextChannel().sendMessage( Integer.toString(player.getMaxHealth()) );
            break;
            case "attack":
                event.getTextChannel().sendMessage( Integer.toString(player.getAttack()) );
            break;
            case "defense":
                event.getTextChannel().sendMessage( Integer.toString(player.getDefense()) );
            break;
        }

        return true;
    }

}

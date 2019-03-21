package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 6/8/2016.
 */
public class IncCommand extends Command {

    public IncCommand() {
        USAGE = "inc [stat] [amount] <player>";
        REQUIRED = 2;
        OPTIONAL = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        if (args.length == 2) {
            //player = Main.gameHandler.getPlayer(event.getAuthor());
        }else if (args.length == 3) {
            player = Main.gameHandler.game().getPlayer(args[1].toLowerCase());
        }
        String name = player.getName();

        switch (args[0]) {
            case "hp":
            case "health":
                //event.getTextChannel().sendMessage( "Received "+args[1]" exp!" );
                player.incHealth( Integer.parseInt(args[1]) );
                Main.messageHandler.sendMessage( name +" received "+args[1]+" health!\nHealth: "+Integer.toString(player.getHealth()) );
                break;
            case "exp":
                Main.messageHandler.sendMessage( name +" received "+args[1]+" exp!" );
                player.gainExp( Integer.parseInt(args[1]) );
                break;
        }
        return true;
    }
}

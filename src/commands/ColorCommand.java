package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

/**
 * Created by YamiZee on 8/14/2016.
 */
public class ColorCommand extends Command {

    public ColorCommand () {
        USAGE = "color [rrggbb]/([256] [256] [256])";
        DESCRIPTION = "Player/user will be colored. ";
        REQUIRED = 1;
        OPTIONAL = 2;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        //Get color
        Color color = null;
        if (args.length == 1) {
            if (args[0].length() == 6)
                args[0] = "#"+args[0];
            color = Color.decode(args[0]);
        }else if (args.length == 3) {
            color = new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
        //Get the needed role name
        String roleName = event.getAuthor().getId();
        if (Main.gameHandler.game() != null && Main.gameHandler.game().getPlayer(event.getAuthor()) != null) {
            roleName += "game";
            //Give color to player
            Main.gameHandler.game().getPlayer(event.getAuthor()).setColor(color);
        }
        //Give color to user
        Main.discordManager.colorUser(event.getAuthor(), event.getGuild(), color, roleName);

        if (event.getAuthorNick() != null)
            Main.messageHandler.sendMessage(event.getAuthorNick() + " has been recolored. ");
        else
            Main.messageHandler.sendMessage(event.getAuthorName() + " has been recolored. ");

        return true;
    }
}
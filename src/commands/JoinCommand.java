package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 8/13/2016.
 */
public class JoinCommand extends Command {

    public JoinCommand () {
        USAGE = "join";
        DESCRIPTION = "Player will join the currently active game. ";
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        //if player isn't in the game
        if (Main.gameHandler.game().getPlayer(event.getAuthor()) == null) {
            Player player = Main.gameHandler.dataHandler.loadPlayer( Main.gameHandler.game().getNewPlayer(), event.getAuthor().getId());
            //if player doesnt exist in the database
            if (player == null) {
                Main.gameHandler.game().addPlayer(event.getAuthor());
                Main.messageHandler.sendMessage("New player " + event.getAuthor().getUsername() + " created. ");
            }else{
                Main.gameHandler.game().addPlayer(player);
                Main.discordManager.changeNickname(event.getAuthor(), event.getGuild(), player.getName());
                Main.discordManager.colorUser(event.getAuthor(), event.getGuild(), player.getColor(), event.getAuthor().getId()+"game");
                Main.messageHandler.sendMessage(player.getName() + " joined the game. ");
            }
        }else{
            Main.messageHandler.sendMessage("Player already in game. ");
        }
        return true;

    }
}
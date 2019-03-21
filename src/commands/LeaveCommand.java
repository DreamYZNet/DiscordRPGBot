package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 8/13/2016.
 */
public class LeaveCommand extends Command {

    public LeaveCommand () {
        USAGE = "leave";
        DESCRIPTION = "Player will leave the currently active game. ";
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        if (player != null) {
            Main.gameHandler.game().removePlayer(player);
            Main.discordManager.changeNickname(event.getAuthor(), event.getGuild(), "");
            Main.discordManager.deleteRole(event.getAuthor().getId()+"game", event.getGuild(), event.getAuthor());
        }else{
            Main.messageHandler.sendMessage("wtf? this wasn't supposed to hapen??!?");
        }

        return true;

    }
}
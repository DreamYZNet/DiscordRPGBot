package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;

import static com.yamizee.discordbots.Main.gameHandler;

/**
 * Created by YamiZee on 8/13/2016.
 */
public class UnloadCommand extends Command {

    public UnloadCommand() {
        USAGE = "unload";
        DESCRIPTION = "Unload game.";
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Map<String, Player> players = Main.gameHandler.game().getUserPlayers();
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            Main.discordManager.deleteRole(entry.getValue().getDiscordId()+"game", event.getGuild());
            Main.discordManager.changeNickname(event.getAuthor(), event.getGuild(), "");
        }

        Main.gameHandler.unload();

        return true;
    }
}
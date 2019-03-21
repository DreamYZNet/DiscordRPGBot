package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 8/13/2016.
 */
public class RenameCommand extends Command {

    public RenameCommand () {
        USAGE = "rename [new name...]";
        DESCRIPTION = "Player/user will be renamed. ";
        REQUIRED = 1;
        TRAIL = true;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        String prevName = event.getAuthorNick();
        if (Main.gameHandler.game() != null && Main.gameHandler.game().getPlayer(event.getAuthor()) != null) {
            Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
            player.rename(args[0]);
        }
        event.getGuild().getManager().setNickname(event.getAuthor(), args[0]);
        Main.messageHandler.sendMessage(prevName + " is now called " + event.getAuthorNick() + ". ");

        return true;
    }
}
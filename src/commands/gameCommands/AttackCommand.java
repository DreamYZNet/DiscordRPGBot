package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Monster;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 6/9/2016.
 */
public class AttackCommand extends Command {

    public AttackCommand() {
        REQUIRED = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {
        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        Monster monster = Main.gameHandler.game().getMonster(Integer.parseInt(args[0])-1);
        if (monster != null) {
            player.attack(monster);
        }

        Main.gameHandler.game().update();

        return true;
    }
}

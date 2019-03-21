package com.yamizee.discordbots.commands.gameCommands;

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Monster;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by YamiZee on 6/9/2016.
 */
public class SummonCommand extends Command {

    public SummonCommand() {
        REQUIRED = 1;
        ADMIN = true;
        OPTIONAL = 3;
        USAGE = "summon [monster] <hp> <atk> <def>";
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Monster monster = Main.gameHandler.game().addMonster(args[0]);
        switch (args.length) {
            case 4:
                monster.setDefense(Integer.parseInt(args[3]));
            case 3:
                monster.setAttack(Integer.parseInt(args[2]));
            case 2:
                monster.setMaxHealth(Integer.parseInt(args[1]));
                monster.setHealth(monster.getMaxHealth());
        }
        Main.gameHandler.game().displayBattleState();
        return true;
    }
}

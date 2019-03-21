package com.yamizee.discordbots.commands;

import com.yamizee.discordbots.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Objects;
import java.util.Random;

/**
 * Created by YamiZee on 6/3/2016.
 */
public class RollCommand extends Command {

    public RollCommand() {
        USAGE = "roll <number><'d'><sides>";
        DESCRIPTION = "Rolls dice.";
        OPTIONAL = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {


        int int1 = 1;
        int int2 = 6;
        try {
            args = args[0].split("d", 2);
            int1 = Integer.parseInt(args[0]);
        }catch(Exception e){}
        try {
            int2 = Integer.parseInt(args[1]);
        }catch(Exception e){}

        roll(int1, int2);

        return true;
    }

    public void roll (int dice, int sides) {
        int[] rolls = new int[dice];
        int total = 0;
        Random rand = new Random();
        String output = "";
        for (int i = 0; i < rolls.length; i++) {
            rolls[i] = rand.nextInt(sides) +1;
            total += rolls[i];
            output += rolls[i]+"/"+sides;
            if (rolls.length-1 != i) {
                output += " + ";
            }else if (rolls.length != 1){
                output += " = " + total;
            }
        }
        Main.messageHandler.sendMessage(output);
    }
}

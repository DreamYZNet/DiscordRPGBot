package com.yamizee.discordbots.commands.gameCommands;

/**
 * Created by YamiZee on 8/9/2016.
 */

import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Bearing;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class UnholdCommand extends Command {

    public UnholdCommand() {
        USAGE = "unhold [idname/right/left/all]";
        REQUIRED = 1;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        switch (args[0]) {
            case "right":
                player.getBearing().unhold(Bearing.Slot.GRIPR);
            break;
            case "left":
                player.getBearing().unhold(Bearing.Slot.GRIPL);
            break;
            case "both":
            case "all":
                player.getBearing().unhold(Bearing.Slot.GRIPL);
                player.getBearing().unhold(Bearing.Slot.GRIPR);
            break;
            default:
                player.getBearing().unhold(args[0]);
        }
        Main.messageHandler.deleteMessage(event.getMessage());

        return true;
    }
}

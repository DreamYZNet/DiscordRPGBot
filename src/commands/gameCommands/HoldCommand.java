package com.yamizee.discordbots.commands.gameCommands;

/**
 * Created by YamiZee on 8/9/2016.
 */
import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.Command;
import com.yamizee.discordbots.game.Bearing;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class HoldCommand extends Command {

    public HoldCommand() {
        USAGE = "hold [idname] <hand>";
        REQUIRED = 1;
        OPTIONAL = 1;
        TRAIL = true;
    }

    @Override
    public boolean execute(String[] args, MessageReceivedEvent event) {

        Player player = Main.gameHandler.game().getPlayer(event.getAuthor());
        if (args.length == 1) {
            player.getBearing().hold(args[0]);
            event.getMessage().deleteMessage();
        }else{
            switch (args[1]) {
                case "right":
                case "right hand":
                case "with right hand":
                case "in right hand":
                    player.getBearing().hold(args[0], Bearing.Slot.GRIPR);
                    Main.messageHandler.deleteMessage(event.getMessage());
                    break;
                case "left":
                case "left hand":
                case "with left hand":
                case "in left hand":
                    player.getBearing().hold(args[0], Bearing.Slot.GRIPL);
                    Main.messageHandler.deleteMessage(event.getMessage());
                    break;
            }
        }


        return true;
    }
}

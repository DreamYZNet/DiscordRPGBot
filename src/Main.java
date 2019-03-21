
package com.yamizee.discordbots;

import com.yamizee.discordbots.commands.*;
import com.yamizee.discordbots.commands.gameCommands.*;
import com.yamizee.discordbots.game.GameHandler;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Main {

    public static String prefix = ",";

    //name of command & command class
    public static LinkedHashMap<String, Command> commands = new LinkedHashMap <String, Command>();

    public static final GameHandler gameHandler = new GameHandler();
    public static final MessageHandler messageHandler = new MessageHandler();
    public static final DiscordManager discordManager = new DiscordManager();

    private static final String token = "MTg3MDAwODAyNTUwODc0MTE1.Ci5tkQ.i05bJNEbPO9goF7NPD1mV5dWkbc";
    private static JDA jda;


    private static HashMap<String, CommandRequest> previousRequest = new HashMap<>();

    public static void main(String[] args) {

        try {
            jda = new JDABuilder().addListener(new BotListener()).setBotToken(token).buildBlocking();
            jda.setAutoReconnect(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        commands.put("ping", new PingCommand());
        commands.put("help", new HelpCommand());
        commands.put("echo", new EchoCommand());
        commands.put("roll", new RollCommand());
        commands.put("lock", new LockChannel());
        commands.put("unlock", new UnlockChannel());
        commands.put("nuke", new NukeCommand());
        commands.put("rename", new RenameCommand());
        commands.put("color", new ColorCommand());

    }

    public static void handleEvent (MessageReceivedEvent event) {
        //If the prefix is right, //and message isn't from self
        if (event.getMessage().getContent().startsWith(Main.prefix)) { //&& event.getMessage().getAuthor().getId() != event.getJDA().getSelfInfo().getId()){

            //Set the channel to send messages to
            messageHandler.setChannel(event.getTextChannel());

            //If the content contains only the prefix (aka the message is ,)
            if (event.getMessage().getContent().equals(Main.prefix)) {
                CommandRequest request = previousRequest.get(event.getAuthor().getId());
                handleCommand(request.commandStr, request.args, event);
            }else{
                String commandStr = Command.getCommandStr(event);
                if (commands.containsKey(commandStr)) {
                    Command command = commands.get(commandStr);
                    handleCommand(commandStr, command.getArgs(event), event);
                }else{
                    Main.messageHandler.sendMessage("No such command...");
                }
            }
            if (messageHandler.getDeleteAllMessages()) {
                event.getMessage().deleteMessage();
            }
            messageHandler.flush();
            //gameHandler.saveGame();
            //gameHandler.loadGame();
        }
    }

    public static void handleCommand (String commandStr, String[] args, MessageReceivedEvent event) {

        Command command = commands.get(commandStr);
        //If user has proper privileges to use the command
        if (!command.requireAdmin() || event.getAuthor() == event.getGuild().getOwner()) {
            //If the parameters have correct amount of arguments
            if (!(args == null && command.getRequired() != 0)
                    && (args == null || (args.length >= command.getRequired()
                    && (command.hasTrail() || args.length <= command.getRequired() + command.getOptional())))) {
                if (command.execute(args, event)) {
                }

                //record the request in case needed later
                previousRequest.put(event.getAuthor().getId(), new CommandRequest(commandStr, args));

            } else {
                Main.messageHandler.sendMessage("Incorrect Parameters!\n" + command.getHelp());
            }
        }else{
            Main.messageHandler.sendMessage("Insufficient permissions. ");
        }

    }


}

class CommandRequest {

    String commandStr;
    String[] args;

    public CommandRequest (String commandStr, String[] args) {
        this.commandStr = commandStr;
        this.args = args;
    }

}

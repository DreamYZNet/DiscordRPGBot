package com.yamizee.discordbots.game.games;


import com.yamizee.discordbots.Main;
import com.yamizee.discordbots.commands.*;
import com.yamizee.discordbots.commands.gameCommands.*;
import com.yamizee.discordbots.game.DataHandler;
import com.yamizee.discordbots.game.Monster;
import com.yamizee.discordbots.game.Player;
import net.dv8tion.jda.entities.User;

import java.util.*;

/**
 * Created by YamiZee on 8/13/2016.
 */
public class Game {

    //Players
    private HashMap <String, Player> namePlayers = new HashMap<>(); //name id
    private HashMap <String, Player> userPlayers = new HashMap<>(); //user id because ids can be stored

    private LinkedHashMap<String, Command> commands = new LinkedHashMap <String, Command>();

    //Current monsters
    private Monster[] monsters = new Monster[5];

    //Items

    //Battle state
    private boolean battling = false;
    private ArrayList<Monster> notMovedYet = new ArrayList<>();
    private ArrayList<Monster> alreadyMoved = new ArrayList<>();

    public Game() {
        loadCommands();
        loadGame();
    }

    private void loadCommands() {
        commands.put("join", new JoinCommand());
        commands.put("leave", new LeaveCommand());
        commands.put("get", new GetCommand());
        commands.put("gain", new IncCommand());
        commands.put("scale", new ScaleCommand());
        commands.put("summon", new SummonCommand());
        commands.put("attack", new AttackCommand());

        commands.put("inventory", new DisplayInventoryCommand());
        commands.put("grant", new GrantCommand());
        commands.put("wear", new WearCommand());
        commands.put("strip", new StripCommand());
        commands.put("hold", new HoldCommand());
        commands.put("unhold", new UnholdCommand());
        commands.put("swap", new SwapCommand());
        commands.put("give", new GiveCommand());
        commands.put("drop", new DropCommand());
    }

    public void loadGame () {

        /*Player[] players = new Player[];
        for (Player p : players) {
            namePlayers.put(p.getId(), p);
            userPlayers.put(p.getDiscordId(), p);
        }*/

    }

    public void saveGame () {
        for (Map.Entry <String, Player> entry : namePlayers.entrySet()) {
            new DataHandler().savePlayer(entry.getValue());
            System.out.println("saving "+entry.getKey());
        }
    }
    public void terminate () {
        saveGame();
    }


    //Construct: fill player hashmaps
    public void addPlayer (User user) {
        String name = user.getUsername();
        Player player = getNewPlayer();
        player.setName(name);
        player.setDiscordId(user.getId());
        player.setId(name.replaceAll("\\s","").toLowerCase());
        addPlayer(player);
    }
    public void addPlayer (Player player) {
        namePlayers.put(player.getId(), player);
        userPlayers.put(player.getDiscordId(), player);
    }
    //wrapper
    public Player getNewPlayer () { //exists solely so that it can be overwritten for sub classes of Player
        return new Player();
    }
    public void removePlayer (Player player) {
        notMovedYet.remove(player);
        alreadyMoved.remove(player);
        userPlayers.remove(player.getDiscordId());
        namePlayers.remove(player.getId());

        Main.messageHandler.sendMessage(player.getName() + " left the game. ");
    }

    //BATTLE METHODS
    public void initBattle () {
        if (!battling) {
            for (Map.Entry<String, Player> entry : namePlayers.entrySet()) {
                notMovedYet.add(entry.getValue());
            }
            battling = true;
        }
    }

    public void update () {
        if (notMovedYet.size() == 0) {
            //Swap arraylists
            ArrayList<Monster> tempList = notMovedYet;
            notMovedYet = alreadyMoved;
            alreadyMoved = tempList;

            displayBattleState();
            update();
        }else{
            //If the next turn is a monsters:
            if (!(notMovedYet.get(0) instanceof Player)) {
                notMovedYet.get(0).attack(getRandomPlayer());
                update();
            }
        }
    }
    private Player getRandomPlayer () {
        int number = new Random().nextInt(namePlayers.size());
        Object[] values = namePlayers.values().toArray();
        return (Player) values[number];
    }

    //MONSTER METHODS
    public Monster addMonster (String name) {
        int slot = findFirstEmpty(monsters);
        if (slot != -1) {
            Monster monster = getNewMonster();
            monster.setName(name);
            monsters[slot] = monster;
            Main.messageHandler.sendMessage( name +" summoned!" );
            initBattle();
            notMovedYet.add(monsters[slot]);
            return monster;
        }
        return null;
    }
    public Monster getMonster (int n) {
        if (n >= 0 && n < monsters.length) {
            return monsters[n];
        }
        return null;
    }
    public Monster getNewMonster () {
        return new Monster();
    }
    public void removeMonster (Monster monster) {
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] == monster) {
                monsters[i] = null;
                notMovedYet.remove(monster);
                alreadyMoved.remove(monster);
            }
        }
    }
    private int findFirstEmpty(Object[] objects){
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                return i;
            }
        }
        return -1;
    }

    //GENERAL BATTLE METHODS
    public void displayBattleState () {
        Main.messageHandler.flush();

        //Display Monsters
        String output = "Monsters:\n";
        int monsterCount = 0;
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] != null) {
                if (monsterCount > 0) {
                    output += " - ";
                }
                output += (i+1) +":"+ monsters[i].getName();
                monsterCount++;
            }
        }

        //Display Players
        output += "\n\nPlayers:\n";
        for (Map.Entry <String, Player> entry : namePlayers.entrySet()) {
            output += entry.getValue().getName() +": HP:"+ entry.getValue().getHealth() +"/"+entry.getValue().getMaxHealth()+" - ";
        }
        Main.messageHandler.sendMessage(output);
        Main.messageHandler.flush();
    }
    public void distributeExp (int exp) {
        for (Map.Entry <String, Player> entry : namePlayers.entrySet()) {
            Main.messageHandler.sendMessage(entry.getValue().getName() + " gained "+exp+" exp. ");
            entry.getValue().gainExp(exp);
        }
    }

    //GETTERS AND SETTERS
    public Player getPlayer (String idname) {
        return namePlayers.get(idname);
    }
    public Player getPlayer (User user) { return userPlayers.get(user.getId()); }

    public HashMap<String, Player> getUserPlayers() { return userPlayers; }

    public ArrayList<Monster> getNotMovedYet() { return notMovedYet; }
    public ArrayList<Monster> getAlreadyMoved() { return alreadyMoved; }

    public LinkedHashMap<String, Command> getCommands() { return commands; }

}


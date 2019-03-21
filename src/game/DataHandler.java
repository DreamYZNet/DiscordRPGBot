package com.yamizee.discordbots.game;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;

import org.jdom2.*;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 * Created by YamiZee on 7/27/2016.
 */
public class DataHandler {

    String playerStatsPath = "./resources/playerstats.xml";
    String itemCatalogPath = "./resources/items.xml";

    public DataHandler(){}

    public Player[] loadAllPlayers() {

        try{
            //Create a DocumentBuilder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a Document from a file or stream
            File inputFile = new File(playerStatsPath);
            Document document = saxBuilder.build(inputFile);

            List<Element> children = document.getRootElement().getChildren("player");
            Player[] players = new Player[children.size()];

            for (int i = 0; i < children.size(); i++) {
                players[i] = loadPlayer( new Player(), children.get(i).getAttribute("id").getValue() );
            }
            return players;
        }catch(FileNotFoundException fe){
            System.out.println("File not found.");
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    public Player loadPlayer (Player player, String discordid) {

        try{
            //Create a DocumentBuilder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a Document from a file or stream
            File inputFile = new File(playerStatsPath);
            Document document = saxBuilder.build(inputFile);

            XPathFactory xpf = XPathFactory.instance();
            XPathExpression<Element> xpath = xpf.compile("//player[@discord_id='" + discordid + "']", Filters.element());
            Element playerElement = xpath.evaluateFirst(document);

            if (playerElement != null) {
                //Create player & stats
                player.setId(playerElement.getAttributeValue("id"));
                player.setDiscordId(playerElement.getAttributeValue("discord_id"));
                player.setName(playerElement.getChild("name").getValue());
                player.setHealth(Integer.parseInt(playerElement.getChild("health").getValue()));
                player.setExp(Integer.parseInt(playerElement.getChild("exp").getValue()));
                player.setLevel(Integer.parseInt(playerElement.getChild("level").getValue()));
                String color = playerElement.getChild("color").getValue();
                if (color.length() == 6)
                    color = "#" + color;
                player.setColor(Color.decode(color));

                return player;
            }else{
                return null;
            }

        }catch(FileNotFoundException fe){
            System.out.println("File not found.");
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    public void savePlayer (Player player) {

        try{
            //Create a DocumentBuilder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a Document from a file or stream
            File inputFile = new File(playerStatsPath);
            Document document = saxBuilder.build(inputFile);

            XPathFactory xpf = XPathFactory.instance();
            XPathExpression<Element> xpath = xpf.compile("//player[@id='" + player.getId() + "']", Filters.element());
            Element playerElement = xpath.evaluateFirst(document);
            if (playerElement == null) {
                playerElement = new Element ("player");
                document.getRootElement().addContent(playerElement);
            }
            getAttributeOrCreate(playerElement, "id").setValue(player.getId());
            getAttributeOrCreate(playerElement, "discord_id").setValue(player.getDiscordId());
            getElementOrCreate(playerElement, "name").setText(player.getName());
            getElementOrCreate(playerElement, "health").setText(String.valueOf(player.getHealth()));
            getElementOrCreate(playerElement, "exp").setText(String.valueOf(player.getExp()));
            getElementOrCreate(playerElement, "level").setText(String.valueOf(player.getLevel()));

            //XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
            //xout.output(document, );fuck fix this saving shit later

        }catch(FileNotFoundException fe){
            System.out.println("File not found.");
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }
    public Item loadItem (String id) {

        try{
            //Create a DocumentBuilder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a Document from a file or stream
            File inputFile = new File(itemCatalogPath);
            Document document = saxBuilder.build(inputFile);

            XPathFactory xpf = XPathFactory.instance();
            XPathExpression<Element> xpath = xpf.compile("/catalog//*[@id='" + id + "']", Filters.element()); // /catalog//*[@id='id']
            Element itemElement = xpath.evaluateFirst(document);

            //Create item
            Item item;
            if (itemElement.getName().equals("garnment")) {
                List<Element> elementList = itemElement.getChild("wearable").getChildren();
                Bearing.Slot[] slots = elementsToSlots(elementList.toArray(new Element[elementList.size()]));
                item = new Clothing(slots);
            }else if (itemElement.getName().equals("weapon")) {
                boolean twoHanded = false;
                if (itemElement.getChild("twohanded") != null) {
                    twoHanded = true;
                }
                int damage = Integer.parseInt( getValueNoException(itemElement.getChild("damage")) );
                item = new Weapon(damage, twoHanded);
            }else{
                item = new Item();
            }
            item.setId(itemElement.getAttributeValue("id"));
            item.setName( getValueNoException(itemElement.getChild("name")) );
            if (item.getName() == null) item.setName(item.getId());
            item.setDescriptor( getValueNoException(itemElement.getChild("descriptor")) );
            item.setDescription( getValueNoException(itemElement.getChild("description")) );
            item.setWeight( Integer.parseInt( getValueNoException(itemElement.getChild("weight")) ) );
            item.setSpace( Integer.parseInt( getValueNoException(itemElement.getChild("space")) ) );
            item.setValue( Integer.parseInt( getValueNoException(itemElement.getChild("value")) ) );

            return item;

        }catch(FileNotFoundException fe){
            System.out.println("File not found.");
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    public Bearing.Slot[] elementsToSlots (Element[] elements) {
        Bearing.Slot[] slots = new Bearing.Slot[elements.length];
        for (int i = 0; i < elements.length; i++) {
            slots[i] = Bearing.Slot.valueOf(elements[i].getName().toUpperCase());
        }
        return slots;
    }

    public Element getElementByXPath (String expression, Document document) {
        XPathFactory xpf = XPathFactory.instance();
        XPathExpression<Element> xpath = xpf.compile(expression, Filters.element());
        return xpath.evaluateFirst(document);
    }
    //Get element from element. If it doesnt exist, create it
    public Element getElementOrCreate (Element parent, String child) {
        Element element = parent.getChild(child);
        if (element == null) {
            element = parent.addContent(new Element(child));
        }
        return element;
    }
    //Get attribute from element. If it doesnt exist, create it
    public Attribute getAttributeOrCreate (Element parent, String child) {
        Attribute attribute = parent.getAttribute(child);
        if (attribute == null) {
            parent.setAttribute(new Attribute(child, ""));
        }
        return attribute;
    }
    //no exception
    public String getValueNoException (Element e) {
        if (e != null) {
            return e.getValue();
        }else{
            return null;
        }
    }

}











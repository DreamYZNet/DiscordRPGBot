package com.yamizee.discordbots;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.User;

import java.awt.*;

/**
 * Created by YamiZee on 8/16/2016.
 */
public class DiscordManager {

    public void colorUser (String userid, Guild guild, Color color, String roleName) {
        colorUser(findUserById(userid, guild), guild, color, roleName);
    }
    public void colorUser (User user, Guild guild, Color color, String roleName) {
        if (user == null && guild == null && color == null && roleName == null) return;
        //Get role
        Role role = null;
        for (Role tempRole : guild.getRolesForUser(user)) {
            if (tempRole.getName().equals(roleName)) {
                role = tempRole;
                break;
            }
        }
        //Or create it
        if (role == null) {
            role = guild.createRole().getRole();
            role.getManager().setName(roleName);
        }
        //Move role second to top (very top doesn't seem possible)
        role.getManager().move(guild.getRoles().get(0).getPosition() - 1);
        //Set color
        role.getManager().setColor(color).update();
        //Give user the role
        guild.getManager().addRoleToUser(user, role).update();
    }

    public void deleteRole (String roleName, Guild guild, User user) {
        for (Role tempRole : guild.getRolesForUser(user)) {
            if (tempRole.getName().equals(roleName)) {
                tempRole.getManager().delete();
                break;
            }
        }
    }//less efficient version
    public void deleteRole (String roleName, Guild guild) {
        for (Role tempRole : guild.getRoles()) {
            if (tempRole.getName().equals(roleName)) {
                tempRole.getManager().delete();
                break;
            }
        }
    }

    public User findUserById (String userid, Guild guild) {
        for (User user : guild.getUsers()) {
            if (user.getId().equals(userid)) {
                return user;
            }
        }
        return null;
    }

    public void changeNickname (User user, Guild guild, String nickname) {
        try {
            guild.getManager().setNickname(user, nickname);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}

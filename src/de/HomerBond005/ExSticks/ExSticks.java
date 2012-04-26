/*
 * Copyright HomerBond005
 * 
 *  Published under CC BY-NC-ND 3.0
 *  http://creativecommons.org/licenses/by-nc-nd/3.0/
 */
package de.HomerBond005.ExSticks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.HomerBond005.Permissions.PermissionsChecker;

public class ExSticks extends JavaPlugin{
	PluginManager pm;
	private final ESPL playerlistener = new ESPL(this);
	private boolean permissionsenabled = true;
	Map<Player, Boolean> playerbools = new HashMap<Player, Boolean>();
	PermissionsChecker pc;
	public void onEnable(){
		pm = getServer().getPluginManager();
		pm.registerEvents(playerlistener, this);
		pc = new PermissionsChecker(this, permissionsenabled);
		for(Player player : getServer().getOnlinePlayers()){
			playerbools.put(player, false);
			System.out.println("lol");
		}
		System.out.println("[ExplosiveSticks] is enabled.");
	}
	public void onDisable(){
		System.out.println("[ExplosiveSticks] is disabled.");
	}
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		if(command.getName().equalsIgnoreCase("ex")){
			try{
			}catch(ArrayIndexOutOfBoundsException e){
				sender.sendMessage("[ExSticks]: to less arguments.");
				return true;
			}
			if(sender.hasPermission("ExSticks.player.explode")){
				
			}
		}else if(command.getName().equalsIgnoreCase("bs")){
			Player player = (Player) sender;
			if(hasPermission(player, "exsticks.boomstick.enable")){
				if(switchBS(player)){
					player.sendMessage(ChatColor.GREEN + "BoomStick enabled! Try it ;-)");
				}else{
					player.sendMessage(ChatColor.GREEN + "BoomStick disabled. Now you're save.");
				}
				return true;
			}else{
				player.sendMessage(ChatColor.RED + "Sorry, but I can't let you do this. :(");
				return true;
			}
		}
		sender.sendMessage("[ExSticks]: type /ex help for help.");
		return true;
	}
	public boolean hasPermission(Player player, String perm){
		if(permissionsenabled){
			if(player.hasPermission(perm)){
				return true;
			}
		}else{
			if(player.isOp()){
				return true;
			}
		}
		return false;
	}
	public boolean getEnabledBS(Player player){
		return playerbools.get(player);
	}
	public boolean switchBS(Player player){
		playerbools.put(player, !playerbools.get(player));
		return playerbools.get(player);
	}
}
package com.bukkit.homerbond005.ExSticks;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExSticks extends JavaPlugin{
	PluginManager pm;
	private final ESPL playerlistener = new ESPL(this);
	private boolean permissionsenabled = false;
    @SuppressWarnings({ "unchecked", "rawtypes" })
	Vector<PlayerBool> playerbools = new Vector();
	public void onEnable(){
		pm = getServer().getPluginManager();
		pm.registerEvents(playerlistener, this);
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
				if(!switchBS(player)){
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
		int playerfound = 999999999;
		for(int i = 0; i < playerbools.size(); i++){
			if(playerbools.get(i).who() == player.getDisplayName()){
				playerfound = i;
			}
		}
		if(playerfound == 999999999){
			playerbools.add(new PlayerBool(player.getDisplayName(), false));
			return false;
		}else{
			return playerbools.get(playerfound).get();
		}
	}
	public boolean switchBS(Player player){
		int playerfound = 999999999;
		for(int i = 0; i < playerbools.size(); i++){
			if(playerbools.get(i).who() == player.getDisplayName()){
				playerfound = i;
			}
		}
		if(playerfound == 999999999){
			playerbools.add(new PlayerBool(player.getDisplayName(), true));
			return true;
		}else{
			playerbools.get(playerfound).change(!playerbools.get(playerfound).get());
			return !playerbools.get(playerfound).get();
		}
	}
}

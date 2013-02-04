/*
 * Copyright HomerBond005
 * 
 *  Published under CC BY-NC-ND 3.0
 *  http://creativecommons.org/licenses/by-nc-nd/3.0/
 */
package de.HomerBond005.ExSticks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExSticks extends JavaPlugin{
	private PluginManager pm;
	private final ESPL playerlistener = new ESPL(this);
	private boolean permissionsenabled = true;
	Map<Player, Boolean> playerbools = new HashMap<Player, Boolean>();
	PermissionsChecker pc;
	private Metrics metrics;
	private Updater updater;
	private Logger log;
	
	@Override
	public void onEnable(){
		getConfig().addDefault("updateReminderEnabled", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		log = getLogger();
		pm = getServer().getPluginManager();
		pm.registerEvents(playerlistener, this);
		pc = new PermissionsChecker(this, permissionsenabled);
		for(Player player : getServer().getOnlinePlayers()){
			playerbools.put(player, false);
		}
		try{
			metrics = new Metrics(this);
			metrics.start();
		}catch(IOException e){
			log.log(Level.WARNING, "Error while enabling Metrics.");
		}
		updater = new Updater(this, getConfig().getBoolean("updateReminderEnabled", true));
		getServer().getPluginManager().registerEvents(updater, this);
		log.log(Level.INFO, "is enabled.");
	}
	
	@Override
	public void onDisable(){
		log.log(Level.INFO, "is disabled.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		if(command.getName().equalsIgnoreCase("bs")){
			Player player = (Player) sender;
			if(pc.has(player, "exsticks.boomstick")){
				if(switchBS(player)){
					player.sendMessage(ChatColor.GREEN + "BoomStick enabled! Try it ;-)");
				}else{
					player.sendMessage(ChatColor.GREEN + "BoomStick disabled. Now you're safe.");
				}
				return true;
			}else{
				pc.sendNoPermMsg(player);
				return true;
			}
		}
		return true;
	}
	
	public boolean getEnabledBS(Player player){
		return playerbools.get(player);
	}
	
	private boolean switchBS(Player player){
		playerbools.put(player, !playerbools.get(player));
		return playerbools.get(player);
	}
}

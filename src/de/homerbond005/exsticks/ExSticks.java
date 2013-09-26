/*
 * Copyright HomerBond005
 * 
 *  Published under CC BY-NC-ND 3.0
 *  http://creativecommons.org/licenses/by-nc-nd/3.0/
 */
package de.homerbond005.exsticks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ExSticks extends JavaPlugin {
	private PluginManager pm;
	private final ESPL playerlistener = new ESPL(this);
	Map<Player, Boolean> playerbools = new HashMap<Player, Boolean>();
	private Metrics metrics;
	private Logger log;

	@Override
	public void onEnable() {
		getConfig().addDefault("updateReminderEnabled", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		log = getLogger();
		pm = getServer().getPluginManager();
		pm.registerEvents(playerlistener, this);
		for (Player player : getServer().getOnlinePlayers()) {
			playerbools.put(player, false);
		}
		try {
			metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			log.log(Level.WARNING, "Error while enabling Metrics.");
		}
		log.log(Level.INFO, "is enabled.");
	}

	@Override
	public void onDisable() {
		log.log(Level.INFO, "is disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		if (command.getName().equalsIgnoreCase("bs")) {
			Player player = (Player) sender;
			if (player.hasPermission("exsticks.boomstick")) {
				if (switchBS(player)) {
					player.sendMessage(ChatColor.GREEN + "BoomStick enabled! Try it ;-)");
				} else {
					player.sendMessage(ChatColor.GREEN + "BoomStick disabled. Now you're safe.");
				}
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the required permission!");
				return true;
			}
		}
		return true;
	}

	public boolean getEnabledBS(Player player) {
		return playerbools.get(player);
	}

	private boolean switchBS(Player player) {
		playerbools.put(player, !playerbools.get(player));
		return playerbools.get(player);
	}
}

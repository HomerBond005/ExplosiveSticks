/*
 * Copyright HomerBond005
 * 
 *  Published under CC BY-NC-ND 3.0
 *  http://creativecommons.org/licenses/by-nc-nd/3.0/
 */
package de.HomerBond005.ExSticks;
import net.minecraft.server.EntityTNTPrimed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ESPL implements Listener{
	private static ExSticks plugin;
	public ESPL(ExSticks es){
		plugin = es;
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event){
		plugin.playerbools.put(event.getPlayer(), false);
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event){
		plugin.playerbools.remove(event.getPlayer());
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && plugin.getEnabledBS(player)){
			try{
				if(event.getItem().getType() != Material.STICK){
					return;
				}
			}catch(NullPointerException e){
				return;
			}
			Location playerloc;
			if(!plugin.pc.has(player, "exsticks.boomstick")){
				playerloc = player.getLocation();
			}else{
				playerloc = player.getTargetBlock (null, 100).getLocation();
				if(playerloc.getBlock().isEmpty()){
					playerloc = player.getLocation();
					playerloc.setY(100);
				}
			}
			spawnTNT(playerloc);
		}else if(event.getAction() == Action.RIGHT_CLICK_AIR && plugin.getEnabledBS(player)){
			try{
				if(event.getItem().getType() != Material.STICK){
					return;
				}
			}catch(NullPointerException e){
				return;
			}
			Location playerloc;
			if(!plugin.pc.has(player, "exsticks.boomstick")){
				playerloc = player.getLocation();
			}else{
				playerloc = player.getTargetBlock (null, 100).getLocation();
				playerloc.setY(90.0);
			}
			spawnTNT(playerloc);
			
		}
	}
	private void spawnTNT(Location loc){
		EntityTNTPrimed e = null;
		CraftWorld cWorld = (CraftWorld)loc.getWorld();
		net.minecraft.server.World world = (net.minecraft.server.World)(cWorld.getHandle());
		e = new EntityTNTPrimed(world);
		org.bukkit.entity.Entity TNT = e.getBukkitEntity();
		world.addEntity(e);
		Double X = loc.getX();
		Double Y = loc.getY();
		Double Z = loc.getZ();
		Location location = new Location( loc.getWorld(), X, Y, Z );
		TNT.teleport( location );
	}
}

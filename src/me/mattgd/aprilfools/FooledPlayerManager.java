package me.mattgd.aprilfools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * FooledPlayer container for managing the April Fools joke.
 * 
 * @author mattgd
 */
public class FooledPlayerManager {

	/** ArrayList of players being fooled */
	private ArrayList<String> fooledPlayers;
	/** The AprilFools plugin instance */
	private AprilFools plugin;
	
	/**
	 * Constructs a new FooledPlayed for the specified Player.
	 * @param p The Player being fooled.
	 */
	public FooledPlayerManager(AprilFools plugin) {
		this.plugin = plugin;
		fooledPlayers = new ArrayList<String>();
	}
	
	/**
	 * Returns the ArrayList of fooled players.
	 * @return the ArrayList of fooled players.
	 */
	public ArrayList<String> getFooledPlayers() {
		return fooledPlayers;
	}

	/**
	 * Toggles fooling for the player.
	 * @param name The name of the player to toggle fooling for.
	 */
	public boolean toggleFooling(String name) {
		if (fooledPlayers.contains(name)) {
			fooledPlayers.remove(name);
			return false;
		} else {
			fooledPlayers.add(name);
			return true;
		}
	}
	
	public void foolPlayer(final String name) {
		fooledPlayers.add(name);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				Player p = Bukkit.getPlayer(name);
				
				if (p != null) {
					MessageManager.getInstance().good(p, "Happy April Fools Day! You've been fooled. Also, here's a gift.");
					p.getInventory().addItem(new ItemStack(Material.EMERALD, 3));
				}
				
				FileConfiguration config = plugin.getConfig();
				config.getStringList("fooled-players").add(name);
				
				try {
					config.save(plugin.getDataFolder() + File.separator + "config.yml");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				fooledPlayers.remove(name);
			}
		}, 900L); // 45 seconds
	}
	
}

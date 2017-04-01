package me.mattgd.aprilfools;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * AprilFools plugin main class.
 * 
 * @author mattgd
 */
public class AprilFools extends JavaPlugin implements Listener {
	
	private FooledPlayerManager manager;
	
    /**
     * Enable the AprilFools plugin.
     */
	@Override
	public void onEnable() {
		saveDefaultConfig(); // Create default the configuration if config.yml doesn't exist
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("aprilfools").setExecutor(this); // Setup commands
		manager = new FooledPlayerManager(this);
	}
	
	/**
     * Disable the Foundation plugin.
     */
	@Override
	public void onDisable() {       
        Bukkit.getScheduler().cancelAllTasks(); // Cancel scheduled tasks
        getConfig().options().copyDefaults(true);
		getLogger().info("Disabled!");
	}
	
	/**
	 * Allows the player to toggle the April Fools surprise!
	 * @return true always
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		MessageManager msg = MessageManager.getInstance();
		
		if (sender instanceof Player) {
			String fooled = manager.toggleFooling(sender.getName()) ? "enabled" : "disabled";
			msg.good(sender, "You have " + fooled + " fooling.");
		} else {
			msg.severe(sender, "Only player can use this command.");
		}

		return true;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if (manager.getFooledPlayers().contains(p.getName())) {
			p.getVelocity().multiply(-2);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		String name = e.getPlayer().getName();
		
		if (!getConfig().getStringList("fooled-players").contains(name)) {
			manager.foolPlayer(name);
		}
	}
	
	public FooledPlayerManager getManager() {
		return manager;
	}
	
	public static AprilFools getPlugin() {
		return getPlugin();
	}
	
}

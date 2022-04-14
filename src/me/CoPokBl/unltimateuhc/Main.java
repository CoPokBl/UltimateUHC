package me.CoPokBl.unltimateuhc;

import me.CoPokBl.unltimateuhc.Commands.NonTabCommands;
import me.CoPokBl.unltimateuhc.Commands.Scenarios;
import me.CoPokBl.unltimateuhc.EventListeners.CustomDrops;
import me.CoPokBl.unltimateuhc.EventListeners.GameListeners;
import me.CoPokBl.unltimateuhc.EventListeners.GoldenHeads;
import me.CoPokBl.unltimateuhc.EventListeners.WorldProtections;
import me.CoPokBl.unltimateuhc.Scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public static int SpigotVersion;
	public static Main plugin;
	public static GameManager gameManager;
	public static ScoreboardManager scoreboardManager;

	@Override
	public void onEnable() {
		// Instances
		plugin = this;
		gameManager = new GameManager();
		scoreboardManager = new ScoreboardManager();

		// Get spigot version
		SpigotVersion = Utils.GetVersion();

		WorldManager worldManager = new WorldManager();

		// register commands
		this.getCommand("uhcscenario").setExecutor(new Scenarios());
		this.getCommand("uhcscenario").setTabCompleter(new Scenarios());

		this.getCommand("uhcstart").setExecutor(new NonTabCommands());
		this.getCommand("uhcend").setExecutor(new NonTabCommands());
		this.getCommand("uhcserverend").setExecutor(new NonTabCommands());
		this.getCommand("uhcinfo").setExecutor(new NonTabCommands());
		this.getCommand("uhcscatter").setExecutor(new NonTabCommands());
		this.getCommand("uhchealth").setExecutor(new NonTabCommands());
		this.getCommand("uhcpvp").setExecutor(new NonTabCommands());
		this.getCommand("uhcsethealth").setExecutor(new NonTabCommands());
		this.getCommand("givehead").setExecutor(new NonTabCommands());
		this.getCommand("uhcstatus").setExecutor(new NonTabCommands());
		this.getCommand("uhcdebug").setExecutor(new NonTabCommands());
		this.getCommand("uhcrespawn").setExecutor(new NonTabCommands());
		this.getCommand("uhcjoin").setExecutor(new NonTabCommands());
		this.getCommand("uhcleave").setExecutor(new NonTabCommands());
		this.getCommand("uhcname").setExecutor(new NonTabCommands());

		// register events
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new CustomDrops(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GameListeners(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GoldenHeads(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new WorldProtections(), this);

		// Register the recipes
		new Recipes().registerRecipes();

		// Delete any existing UHC world
		worldManager.IfUhcWorldExistsDelete();

		// Create UHC world
		WorldCreator wc = new WorldCreator("uhc");
		wc.environment(World.Environment.NORMAL);
		wc.type(WorldType.NORMAL);
		wc.createWorld();

		World uhc = Bukkit.getWorld("uhc");
		if (uhc == null) {
			Bukkit.getLogger().severe("World creation failed!");
			Bukkit.getLogger().severe("Disabling");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		// Init world for UHC
		uhc.getWorldBorder().setCenter(0, 0);
		uhc.getWorldBorder().setSize(500);
		uhc.setSpawnLocation(0,100, 0);
		if (!(Bukkit.getOnlinePlayers().isEmpty())) 
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (online.isOp()) {
					online.sendMessage(ChatColor.RED + "Reloading the server breaks UltimateUHC, restart server now!");
					online.sendMessage(ChatColor.RED + "UltimateUHC is now disabling...");
					Bukkit.getPluginManager().disablePlugin(this);
				}
			}
	}
	
	@Override
	public void onDisable() {
		// when plugin stops
		if (!(Bukkit.getOnlinePlayers().isEmpty())) {
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (online.isOp()) {
					online.sendMessage(ChatColor.RED + "UltimateUHC has been disabled.");
				}
			}
		} else {
			Bukkit.getLogger().info("UltimateUHC has been disabled.");
		}
	}
	


	
	public String t(String mes) {
		return ChatColor.translateAlternateColorCodes('&', mes);
	}
	

	
	
	
	
}
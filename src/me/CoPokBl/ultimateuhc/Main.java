package me.CoPokBl.ultimateuhc;

import me.CoPokBl.ultimateuhc.Commands.NonTabCommands;
import me.CoPokBl.ultimateuhc.Commands.Scenarios;
import me.CoPokBl.ultimateuhc.EventListeners.CustomDrops;
import me.CoPokBl.ultimateuhc.EventListeners.GameListeners;
import me.CoPokBl.ultimateuhc.EventListeners.GoldenHeads;
import me.CoPokBl.ultimateuhc.EventListeners.WorldProtections;
import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin {
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

		// thing
		saveDefaultConfig();

		// set config values
		gameManager.TimeToPvp = getConfig().getInt("secondsToPvp");
		gameManager.TimeToMeetup = getConfig().getInt("secondsToMeetup");
		gameManager.WorldName = getConfig().getString("worldName");
		List<String> scenarios = getConfig().getStringList("enabledScenarios");
		for (String scenario : scenarios) {
			if (Scenarios.scenarios.containsKey(scenario.toLowerCase())) {
				Scenario scen = Scenarios.scenarios.get(scenario.toLowerCase());
				scen.Enable();
				gameManager.Scenarios.add(scen);
				getLogger().info("Enabled scenario: " + scenario);
			}
		}

		// Get spigot version
		SpigotVersion = Utils.GetVersion();

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
		this.getCommand("uhclistscenarios").setExecutor(new NonTabCommands());
		this.getCommand("uhcaddtime").setExecutor(new NonTabCommands());
		this.getCommand("uhccancelrestart").setExecutor(new NonTabCommands());

		// register events
		Bukkit.getServer().getPluginManager().registerEvents(new CustomDrops(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GameListeners(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GoldenHeads(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new WorldProtections(), this);

		// Register the recipes
		new Recipes().registerRecipes();

		// Create UHC world
		if (Bukkit.getWorld(gameManager.WorldName) != null) {
			getLogger().info("A UHC world exists");
		}
		String seedConfigValue = getConfig().getString("worldSeed");
		WorldCreator wc = new WorldCreator(gameManager.WorldName);
		if (seedConfigValue != null && !Objects.equals(seedConfigValue, "null")) {
			wc.seed(Long.parseLong(seedConfigValue));
		}
		wc.environment(World.Environment.valueOf(getConfig().getString("worldEnvironment")));
		wc.type(WorldType.valueOf(getConfig().getString("worldType")));
		wc.createWorld();

		World uhc = Bukkit.getWorld(gameManager.WorldName);
		if (uhc == null) {
			Bukkit.getLogger().severe("World creation failed!");
			Bukkit.getLogger().severe("Disabling");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		// Init world for UHC
		uhc.getWorldBorder().setCenter(0, 0);
		uhc.getWorldBorder().setSize(getConfig().getInt("worldBorderSize"));
		uhc.setSpawnLocation(0,100, 0);
		if (!(Bukkit.getOnlinePlayers().isEmpty())) 
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (online.isOp()) {
					online.sendMessage(ChatColor.RED + "Reloading the server breaks UltimateUHC, restart server now!");
					online.sendMessage(ChatColor.RED + "UltimateUHC is now disabling...");
					getLogger().severe("Reloading the server breaks UltimateUHC, restart server now!");
					Bukkit.getPluginManager().disablePlugin(this);
				}
			}

		getLogger().info("Enabled UltimateUHC");
	}
	
	@Override
	public void onDisable() {

		WorldManager worldManager = new WorldManager();
		worldManager.WorldPath = Bukkit.getWorld(getConfig().getString("worldName")).getWorldFolder();

		// Kick all players
		for (Player online : Bukkit.getOnlinePlayers()) {
			online.kickPlayer(ChatColor.RED + "The server is restarting!");
		}

		// If deleteWorldUponCompletion is true, delete the world
		if (getConfig().getBoolean("deleteWorldUponCompletion")) {
			worldManager.IfUhcWorldExistsDelete();
			Bukkit.getLogger().info("UHC world deleted");
		}

		getLogger().info("UltimateUHC has been disabled.");
	}
	


	
	public String t(String mes) { return ChatColor.translateAlternateColorCodes('&', mes); }
	

	
	
	
	
}
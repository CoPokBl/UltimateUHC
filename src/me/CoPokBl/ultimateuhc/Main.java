// This is a version of the plugin designed for 1.8.x in hope that it will work with 1.9+ as well

package me.CoPokBl.ultimateuhc;

import me.CoPokBl.ultimateuhc.Commands.NonTabCommands;
import me.CoPokBl.ultimateuhc.Commands.Scenarios;
import me.CoPokBl.ultimateuhc.EventListeners.CustomDrops;
import me.CoPokBl.ultimateuhc.EventListeners.GameListeners;
import me.CoPokBl.ultimateuhc.EventListeners.GoldenHeads;
import me.CoPokBl.ultimateuhc.EventListeners.WorldProtections;
import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Interfaces.UhcEvent;
import me.CoPokBl.ultimateuhc.NMS.NMSHandler;
import me.CoPokBl.ultimateuhc.Scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
		gameManager.OfflinePlayersManager = new OfflinePlayersManager();

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

		RewardManager.LoadRewards(getConfig());

		// Load events from config
		List<UhcEvent> events = new ArrayList<>();
		ConfigurationSection eventsSection = getConfig().getConfigurationSection("customEvents");
		if (eventsSection == null) {
			getLogger().warning("No custom events section found in config!");
			gameManager.Events = new UhcEvent[0];
		} else {
			Set<String> keys = eventsSection.getKeys(false);
			for (String key : keys) {
				if (!eventsSection.isConfigurationSection(key)) {
					continue;
				}
				ConfigurationSection eventSection = eventsSection.getConfigurationSection(key);
				if (eventSection == null) continue;
				if (!eventSection.getBoolean("enabled", true)) continue;
				UhcEvent event = new UhcEvent();
				event.name = key;
				if (eventSection.contains("time")) {
					event.time = eventSection.getInt("time");
				} else {
					throw new IllegalArgumentException("Event " + key + " does not have a time value set! Please set it in the config.");
				}
				if (eventSection.contains("command")) {
					event.command = eventSection.getString("command");
				}
				if (eventSection.contains("message")) {
					event.message = eventSection.getString("message");
				}
				if (eventSection.contains("title")) {
					event.title = eventSection.getString("title");
				}
				if (eventSection.contains("subtitle")) {
					event.subtitle = eventSection.getString("subtitle");
				}
				if (eventSection.contains("pvp")) {
					event.pvp = eventSection.getBoolean("pvp");
				}
				if (eventSection.contains("meetup")) {
					event.meetup = eventSection.getBoolean("meetup");
				}
				if (eventSection.contains("border")) {
					String border = eventSection.getString("border");
					// split it by '/' and get the first and second value
					String[] split = border.split("/");
					if (split.length != 2) {
						throw new IllegalArgumentException("Border value for event " + key + " is not in the correct format! Please set it in the config.");
					}
					event.borderSize = Integer.parseInt(split[0]);
					event.borderTime = Integer.parseInt(split[1]);
				}
				events.add(event);
				getLogger().info("Loaded custom event: " + key);
			}
			gameManager.Events = events.toArray(new UhcEvent[0]);
		}

		// Get spigot version
		SpigotVersion = Utils.GetVersion();
		Bukkit.getLogger().info("Spigot version: " + SpigotVersion);
		try {
			NMSHandler.getInstance().Init(SpigotVersion);  // Init NMS
		} catch (IllegalArgumentException e) {
			getLogger().severe("Failed to load NMS. Unsupported version: 1." + SpigotVersion);
		}

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
		Bukkit.getServer().getPluginManager().registerEvents(new OfflinePlayersManager(), this);

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

		// If deleteWorldUponCompletion is true, delete the world
		if (!getConfig().getBoolean("deleteWorldUponCompletion")) {
			return;
		}

		// Kick all players
		for (Player online : Bukkit.getOnlinePlayers()) {
			online.kickPlayer(ChatColor.RED + "The server is restarting!");
		}

		worldManager.IfUhcWorldExistsDelete();
		Bukkit.getLogger().info("UHC world deleted");

		getLogger().info("UltimateUHC has been disabled.");
	}
	


	
	public String t(String mes) { return ChatColor.translateAlternateColorCodes('&', mes); }
	

	
	
	
	
}
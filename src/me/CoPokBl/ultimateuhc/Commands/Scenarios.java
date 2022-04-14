package me.CoPokBl.ultimateuhc.Commands;

import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Interfaces.UhcEventType;
import me.CoPokBl.ultimateuhc.Main;
import me.CoPokBl.ultimateuhc.ScenarioClasses.*;
import me.CoPokBl.ultimateuhc.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Scenarios implements CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// perm check
		if (!(sender.hasPermission("uhc.scenarios"))) {
			sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
			return false;
		}
		// usage check
		if (!(args.length == 2)) {
			sender.sendMessage(ChatColor.RED + "Usage: /scenario <scenario> [on/off]");
			return true;
		}
		if (!(args[1].equals("on") || args[1].equals("off"))) {
			sender.sendMessage(ChatColor.RED + "Usage: /scenario <scenario> [on/off]");
			return true;
		}

		Scenario scenario = null;

		switch (args[0].toLowerCase()) {
			case "skyhigh" -> scenario = new SkyHigh();
			case "zombies" -> scenario = new Zombies();
			case "halfores" -> scenario = new HalfOres();
			case "goldenplayers" -> scenario = new GoldenPlayers();
			case "chicken" -> scenario = new Chicken();
			case "enderman" -> scenario = new Endermen();
			case "stacked" -> scenario = new Stacked();
			case "fallout" -> scenario = new Fallout();
			case "doubledamage" -> scenario = new DoubleDamage();
			case "onetable" -> scenario = new OneTable();
			default -> {
				sender.sendMessage(ChatColor.RED + "Usage: /scenario <scenario> [on/off]. Invalid Scenario!");
				return true;
			}
		}

		if (args[1].equals("on")) {
			if (Utils.ContainsScenario(Main.gameManager.Scenarios, scenario)) {
				sender.sendMessage(ChatColor.RED + "This Scenario Is Already Enabled!");
				return true;
			}
			scenario.Enable();
			Main.gameManager.Scenarios.add(scenario);
			sender.sendMessage(ChatColor.GREEN + "Scenario Enabled!");
			// check events
			if (Main.gameManager.PvpEnabled) scenario.UhcEvent(UhcEventType.Pvp);
			if (Main.gameManager.MeetupEnabled) scenario.UhcEvent(UhcEventType.Meetup);
			return false;
		}

		if (args[1].equals("off")) {
			if (!(Utils.ContainsScenario(Main.gameManager.Scenarios, scenario))) {
				sender.sendMessage(ChatColor.RED + "This Scenario Is Already Disabled!");
				return true;
			}
			int index = Utils.GetIndexOfScenario(Main.gameManager.Scenarios, scenario);
			Main.gameManager.Scenarios.get(index).Disable();
			Main.gameManager.Scenarios.remove(index);
			sender.sendMessage(ChatColor.GREEN + "Scenario Disabled!");
			return true;
		}

		sender.sendMessage(ChatColor.RED + "Usage: /scenario <scenario> [on/off]");
		return true;
	}


	static String[] arguments;
	static String[] onOff;

	static {
		arguments = new String[] {
				"SkyHigh",
				"Zombies",
				"HalfOres",
				"GoldenPlayers",
				"Chicken",
				"Enderman",
				"Stacked",
				"Fallout",
				"DoubleDamage",
				"OneTable"
		};

		onOff = new String[] {
				"on",
				"off"
		};
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> result = new ArrayList<String>();
		if (args.length == 1) {
			for (String a : arguments) {
				if (a.toLowerCase().startsWith(args[0].toLowerCase()))
					result.add(a);
			}
			return result;
		}

		// on off argument
		if (args.length == 2) {
			for (String a : onOff) {
				if (a.toLowerCase().startsWith(args[1].toLowerCase()))
					result.add(a);
			}
			return result;
		}

		return null;
	}

}

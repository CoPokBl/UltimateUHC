package me.CoPokBl.unltimateuhc.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

// import static me.CoPokBl.unltimateuhc.Main.gameManager;

public class Scenarios implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Bukkit.broadcastMessage("Command hit");
		return true;

//		// perm check
//		if (!(sender.hasPermission("uhc.scenarios"))) {
//			sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
//			return false;
//		}
//		// usage check
//		if (!(args.length == 2)) {
//			sender.sendMessage(ChatColor.RED + "Usage: /scenario <scenario> [on/off]");
//			return true;
//		}
//		if (!(args[1].equals("on") || args[1].equals("off"))) {
//			sender.sendMessage(ChatColor.RED + "Usage: /scenario <scenario> [on/off]");
//			return true;
//		}
//
//		Scenario scenario = null;
//
//		switch (args[0].toLowerCase()) {
//			case "skyhigh" -> scenario = new SkyHigh();
//			case "zombies" -> scenario = new Zombies();
//			case "halfores" -> scenario = new HalfOres();
//			case "goldenplayers" -> scenario = new GoldenPlayers();
//			case "chicken" -> scenario = new Chicken();
//			case "enderman" -> scenario = new Endermen();
//			case "stacked" -> scenario = new Stacked();
//			case "fallout" -> scenario = new Fallout();
//			case "doubledamage" -> scenario = new DoubleDamage();
//			case "onetable" -> scenario = new OneTable();
//			default -> {
//				sender.sendMessage(ChatColor.RED + "Usage: /scenario <scenario> [on/off]. Invalid Scenario!");
//				return true;
//			}
//		}
//
//		if (args[1].equals("on")) {
//			if (Main.gameManager.Scenarios.contains(scenario)) {
//				sender.sendMessage(ChatColor.RED + "This Scenario Is Already Enabled!");
//				return true;
//			}
//			scenario.Enable();
//			Main.gameManager.Scenarios.add(scenario);
//			sender.sendMessage(ChatColor.GREEN + "Scenario Enabled!");
//			return false;
//		}
//
//		if (args[1].equals("off")) {
//			if (!(Main.gameManager.Scenarios.contains(scenario))) {
//				sender.sendMessage(ChatColor.RED + "This Scenario Is Already Disabled!");
//				return true;
//			}
//			scenario.Disable();
//			Main.gameManager.Scenarios.remove(scenario);
//			sender.sendMessage(ChatColor.GREEN + "Scenario Disabled!");
//			return false;
//		}
//
//		sender.sendMessage(ChatColor.RED + "Usage: /scenario <scenario> [on/off]");
//		return true;
	}


//	static String[] arguments;
//	static String[] onOff;
//
//	static {
//		arguments = new String[] {
//				"SkyHigh",
//				"Zombies",
//				"HalfOres",
//				"GoldenPlayers",
//				"Chicken",
//				"Endermen",
//				"Stacked",
//				"Fallout",
//				"DoubleDamage",
//				"OneTable"
//		};
//
//		onOff = new String[] {
//				"on",
//				"off"
//		};
//	}

//	@Override
//	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
//		List<String> result = new ArrayList<String>();
//		if (args.length == 1) {
//			for (String a : arguments) {
//				if (a.toLowerCase().startsWith(args[0].toLowerCase()))
//					result.add(a);
//			}
//			return result;
//		}
//
//		// on off argument
//		if (args.length == 2) {
//			for (String a : onOff) {
//				if (a.toLowerCase().startsWith(args[1].toLowerCase()))
//					result.add(a);
//			}
//			return result;
//		}
//
//		return null;
//	}

}

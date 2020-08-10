package me.CoPokBl.unltimateuhc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class scenarios implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("uhcscenario")) {
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
			// has perms
			if (args[0].equalsIgnoreCase("skyhigh")) {
				// skyhigh
				if (args[1].equals("on")) {
					Main.skyhigh = true;
					sender.sendMessage(ChatColor.GREEN + "Skyhigh Has Been Enabled!");
					return true;
				} else {
					Main.skyhigh = false;
					sender.sendMessage(ChatColor.GREEN + "Skyhigh Has Been Disabled!");
					return true;
				}
			}
			// end skyhigh
			// zombies
			if (args[0].equalsIgnoreCase("zombies")) {
				if (args[1].equals("on")) {
					Main.zombies = true;
					sender.sendMessage(ChatColor.GREEN + "Zombies Has Been Enabled!");
					return true;
				} else {
					Main.zombies = false;
					sender.sendMessage(ChatColor.GREEN + "Zombies Has Been Disabled!");
					return true;
				}
			}
			
			//half ores
			if (args[0].equalsIgnoreCase("halfores")) {
				if (args[1].equals("on")) {
					Main.halfores = true;
					sender.sendMessage(ChatColor.GREEN + "HalfOres Has Been Enabled!");
					return true;
				} else {
					Main.halfores = false;
					sender.sendMessage(ChatColor.GREEN + "HalfOres Has Been Disabled!");
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("goldenplayers")) {
				if (args[1].equals("on")) {
					Main.goldp = true;
					sender.sendMessage(ChatColor.GREEN + "Golden PLayers Has Been Enabled!");
					return true;
				} else {
					Main.goldp = false;
					sender.sendMessage(ChatColor.GREEN + "Golden Players Has Been Disabled!");
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("chicken")) {
				if (args[1].equals("on")) {
					Main.chicken = true;
					sender.sendMessage(ChatColor.GREEN + "Chicken Has Been Enabled!");
					return true;
				} else {
					Main.chicken = false;
					sender.sendMessage(ChatColor.GREEN + "Chicken Has Been Disabled!");
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("endermen")) {
				if (args[1].equals("on")) {
					Main.ender = true;
					sender.sendMessage(ChatColor.GREEN + "Endermen Has Been Enabled!");
					return true;
				} else {
					Main.ender = false;
					sender.sendMessage(ChatColor.GREEN + "Endermen Has Been Disabled!");
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("stacked")) {
				if (args[1].equals("on")) {
					Main.stacked = true;
					sender.sendMessage(ChatColor.GREEN + "Stacked Has Been Enabled!");
					return true;
				} else {
					Main.stacked = false;
					sender.sendMessage(ChatColor.GREEN + "Stacked Has Been Disabled!");
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("fallout")) {
				if (args[1].equals("on")) {
					Main.fallout = true;
					sender.sendMessage(ChatColor.GREEN + "Fallout Has Been Enabled!");
					return true;
				} else {
					Main.fallout = false;
					sender.sendMessage(ChatColor.GREEN + "Fallout Has Been Disabled!");
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("doubledamage")) {
				if (args[1].equals("on")) {
					Main.doubledamage = true;
					sender.sendMessage(ChatColor.GREEN + "DoubleDamage Has Been Enabled!");
					return true;
				} else {
					Main.doubledamage = false;
					sender.sendMessage(ChatColor.GREEN + "DoubleDamage Has Been Disabled!");
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("onetable")) {
				if (args[1].equals("on")) {
					Main.onetable = true;
					sender.sendMessage(ChatColor.GREEN + "OneTable Has Been Enabled!");
					return true;
				} else {
					Main.onetable = false;
					sender.sendMessage(ChatColor.GREEN + "OneTable Has Been Disabled!");
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + "Invalid Scenario! Usage: /scenario <scenario> [on/off]");
			return true;
			// command "uhcscenario"
		}
		return true;
		// command
	}

}

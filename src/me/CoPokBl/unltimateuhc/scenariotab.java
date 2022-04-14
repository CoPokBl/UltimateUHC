package me.CoPokBl.unltimateuhc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class scenariotab implements TabCompleter {
	List<String> arguments = new ArrayList<String>();
	List<String> onoff = new ArrayList<String>();

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (arguments.isEmpty()) {
			onoff.add("on");
			onoff.add("off");
			arguments.add("skyhigh");
			arguments.add("zombies");
			arguments.add("halfores");
			arguments.add("goldenplayers");
			arguments.add("chicken");
			arguments.add("endermen");
			arguments.add("stacked");
			arguments.add("fallout");
			arguments.add("doubledamage");
			arguments.add("onetable");
		}
		List<String> result = new ArrayList<String>();
		if (args.length == 1) {
			for (String a : arguments) {
				if (a.toLowerCase().startsWith(args[0].toLowerCase()))
					result.add(a);
			}
			return result;
		}
		if (args.length == 2) {
			for (String a : onoff) {
				if (a.toLowerCase().startsWith(args[1].toLowerCase()))
					result.add(a);
			}
			return result;
		}
		
		return null;
	}

}

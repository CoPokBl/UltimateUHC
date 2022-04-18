package me.CoPokBl.ultimateuhc.Commands;

import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Main;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import me.CoPokBl.ultimateuhc.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static me.CoPokBl.ultimateuhc.Main.gameManager;
import static me.CoPokBl.ultimateuhc.Main.scoreboardManager;
import static me.CoPokBl.ultimateuhc.Utils.GetRandomNum;

public class NonTabCommands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("uhcstart")) {
            Player player = (Player) sender;
            if (!(player.hasPermission("uhc.start"))) {
                player.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return false;
            }
            gameManager.StartUhc();
        }

        if (label.equalsIgnoreCase("uhcjoin")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player p = (Player) sender;
            if (gameManager.AlivePlayers.contains(new UhcPlayer(p))) {
                p.sendMessage(ChatColor.RED + "You Are Already In The Game!");
                return true;
            }
            gameManager.SendPlayer(p);
            return true;
        }

        if (label.equalsIgnoreCase("uhcleave")) {
            sender.sendMessage(ChatColor.RED + "This command is depreciated and no longer works");
            return true;
//            if (!(sender instanceof Player)) {
//                return true;
//            }
//            Player p = (Player) sender;
//            gameManager.AlivePlayers.remove(p);
//            p.teleport(Bukkit.getWorld("world").getSpawnLocation());
//            return true;
        }

        //debug
        if (label.equalsIgnoreCase("uhcdebug")) {
            if (!(sender.hasPermission("uhc.debug")))
                return true;
            if (args[0].equalsIgnoreCase("addalive")) {
                gameManager.AlivePlayers.add(new UhcPlayer(Bukkit.getPlayer(args[1])));
            } else if (args[0].equalsIgnoreCase("randomtp")) {
                if (!(sender instanceof Player)) {
                    return true;
                }
                Player p = (Player) sender;
                Location loc = Utils.GetRandomSpawn(Bukkit.getWorld(gameManager.WorldName));
                if (loc == null) {
                    p.sendMessage(ChatColor.RED + "No Spawns Found!");
                    return true;
                }
                p.teleport(loc);
            }
            sender.sendMessage(ChatColor.GREEN + "Debug Command Executed Successfully");
        }

        if (label.equalsIgnoreCase("uhclistscenarios")) {
            sender.sendMessage(ChatColor.GOLD + "Enabled Scenarios: ");
            for (Scenario scenario : gameManager.Scenarios) {
                sender.sendMessage("- " + ChatColor.GREEN + scenario.GetName());
            }
        }

        if (label.equalsIgnoreCase("uhcaddtime")) {
            if (!(sender.hasPermission("uhc.addtime"))) {
                sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return false;
            }
            try {
                gameManager.gameLoopTimer += Integer.parseInt(args[0]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Invalid Number!");
                return false;
            }
        }

        if (label.equalsIgnoreCase("uhcname")) {
            if (sender.hasPermission("uhc.name")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "Usage: /uhcname [NEW NAME]");
                    return true;
                }
                StringBuilder name = new StringBuilder();
                for (String arg : args) {
                    name.append(arg).append(" ");
                }
                scoreboardManager.UhcName = name.toString();
                sender.sendMessage(ChatColor.GREEN + "Set the name of the UHC to " + scoreboardManager.UhcName);
                return true;
            }
            sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
            return true;
        }

        //head command
        if (label.equalsIgnoreCase("givehead")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You Don't Have Hands!");
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("uhc.givehead")) {
                if (!(args.length == 1)) {
                    sender.sendMessage(ChatColor.RED + "Usage: /givehead <player>");
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + sender.getName() + " minecraft:player_head{SkullOwner:\"" + args[0] + "\"}");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
        }

        if (label.equalsIgnoreCase("uhcstatus")) {
            sender.sendMessage(ChatColor.GREEN + "Current UHC game status (You could just look at the scoreboard):");
            sender.sendMessage(ChatColor.RED + "In Game: " + gameManager.InGame);
            sender.sendMessage(ChatColor.AQUA + "Is Meetup: " + gameManager.MeetupEnabled);
            sender.sendMessage(ChatColor.GOLD + "PVP allowed: " + gameManager.PvpEnabled);
            sender.sendMessage(ChatColor.BLACK + "People Alive: " + gameManager.AlivePlayers.size());
            if (!(sender instanceof Player))
                return true;
            Player p = (Player) sender;
            sender.sendMessage(ChatColor.BLUE + "WorldBorder: " + (int)p.getWorld().getWorldBorder().getSize());
            if (gameManager.AlivePlayers.contains(new UhcPlayer(p))) {
                sender.sendMessage(ChatColor.GREEN + "You Are Alive!!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "You Are Dead");
            return true;
        }

        if (label.equalsIgnoreCase("uhcend")) {
            sender.sendMessage(ChatColor.RED + "This command is deprecated and no longer works");
            return true;
        }

        if (label.equalsIgnoreCase("uhcsethealth")) {
            if (!(sender.hasPermission("uhc.sethealth"))) {
                sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return true;
            }
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                int health;
                try {
                    health = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Usage: /uhcsethealth <player> (health)");
                    return true;
                }
                target.setHealth(health);
                sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s health to " + args[1]);
            }
            else {
                sender.sendMessage(ChatColor.RED + "Usage: /uhcsethealth <player> (health)");
            }
        }

        if (label.equalsIgnoreCase("uhcpvp")) {
            if (!(sender.hasPermission("uhc.pvp"))) {
                sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "This command is deprecated and no longer works");
            sender.sendMessage(ChatColor.RED + "Use /uhcaddtime " + (gameManager.TimeToPvp - gameManager.gameLoopTimer) +
                    " to increase the game time and trigger PvP");
            return true;
        }

        if (label.equalsIgnoreCase("uhcserverend")) {
            if (!(sender.hasPermission("uhc.serverend"))) {
                sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "This command is deprecated. All this command did was /restart after 1 minute.");
            return true;
        }

        if (label.equalsIgnoreCase("uhcinfo")) {
            sender.sendMessage(
                    ChatColor.GOLD + "" + ChatColor.BOLD + "UHC info:");
            sender.sendMessage(
                    ChatColor.GOLD + "You will join and get teleported to a random location with the effects slowness, blindness, mining fatigue and resistance.");
            sender.sendMessage(
                    ChatColor.GOLD + "Until the UHC starts you will be invincible. When it starts all the effects will be removed and a title will show.");
            sender.sendMessage(
                    ChatColor.GOLD + "PVP will be disabled until it tells you it has been enabled. During the time before meetup you have time to mine and get resources.");
            sender.sendMessage(
                    ChatColor.GOLD + "During meetup you are not allowed to be underground or sky basing. You must head to 0, 0. If you don't you will be kicked from the UHC.");
            sender.sendMessage(
                    ChatColor.GOLD + "Have Fun!! Plugin By CoPokBl.");
            return true;
        }

        //scatter
        if (label.equalsIgnoreCase("uhcscatter")) {
            if (!sender.hasPermission("uhc.scatter")) {
                sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player Not Found!");
                return true;
            }
            World uhc = Bukkit.getWorld("uhc");
            if (uhc == null) {
                sender.sendMessage(ChatColor.RED + "Failed: UHC World Not Found!");
                return true;
            }
            Location loc = Utils.GetRandomSpawn(uhc);
            if (loc == null) {
                sender.sendMessage(ChatColor.RED + "Failed: Couldn't find a safe location to teleport the player");
                return true;
            }
            target.teleport(loc);
            target.sendMessage(ChatColor.GREEN + "You Have Been Teleported To A Random Location!");
            // set the block below the target to stone
            uhc.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).setType(Material.valueOf(Main.plugin.getConfig().getString("teleportBlock")));
            sender.sendMessage(ChatColor.GREEN + "Scattered " + args[0]);
            return true;
        }

        // respawn
        if (label.equalsIgnoreCase("uhcrespawn")) {
            if (sender.hasPermission("uhc.respawn")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "That player does not exist!");
                    return true;
                }
                Bukkit.dispatchCommand(sender, "uhcscatter " + target.getName());
                gameManager.AlivePlayers.remove(new UhcPlayer(target));
                gameManager.AlivePlayers.add(new UhcPlayer(target));
                gameManager.SetupPlayer(target);
                target.setGameMode(GameMode.SURVIVAL);
                sender.sendMessage(ChatColor.GREEN + "You Have Respawned " + target.getName() + "!");
                target.sendMessage(ChatColor.GREEN + "You Have Been Respawned!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
            return false;
        }

        // /uhchealth command
        if (label.equalsIgnoreCase("uhchealth")) {
            if (args.length == 0) {
                sender.sendMessage("Usage: /uhchealth <player>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "That player does not exist!");
                return true;
            }
            sender.sendMessage(target.getName() + "'s health is: " + target.getHealth());
            return true;
        }

        if (label.equalsIgnoreCase("uhccancelrestart")) {
            if (!sender.hasPermission("uhc.cancelrestart")) {
                sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return false;
            }
            Bukkit.getScheduler().cancelTask(gameManager.ShutdownOnGameEndTaskId);
            Bukkit.broadcastMessage(ChatColor.GREEN + "Restart Cancelled!");
            return true;
        }

        return false;
    }

}

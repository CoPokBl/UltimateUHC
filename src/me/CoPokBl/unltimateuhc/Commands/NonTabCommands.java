package me.CoPokBl.unltimateuhc.Commands;

import me.CoPokBl.unltimateuhc.Main;
import me.CoPokBl.unltimateuhc.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static me.CoPokBl.unltimateuhc.Main.gameManager;
import static me.CoPokBl.unltimateuhc.Main.scoreboardManager;
import static me.CoPokBl.unltimateuhc.Utils.GetRandomNum;

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
            if (gameManager.AlivePlayers.contains(p)) {
                p.sendMessage(ChatColor.RED + "You Are Already In The Game!");
                return true;
            }
            gameManager.SendPlayer(p);
            return true;
        }

        if (label.equalsIgnoreCase("uhcleave")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player p = (Player) sender;
            gameManager.AlivePlayers.remove(p);
            p.teleport(Bukkit.getWorld("world").getSpawnLocation());
            return true;

        }

        //debug
        if (label.equalsIgnoreCase("uhcdebug")) {
            if (!(sender.hasPermission("uhc.debug")))
                return true;
            sender.sendMessage("This command is for CoPokBl only.");
            if (!(sender.getName().equals("CoPokBl")))
                return true;
            if (args[0].equalsIgnoreCase("addalive")) {
                gameManager.AlivePlayers.add(Bukkit.getPlayer(args[1]));
            }
        }

        if (label.equalsIgnoreCase("uhcname")) {
            if (sender.hasPermission("uhc.name")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "Usage: /uhcname [NEW NAME]");
                    return true;
                }
                StringBuilder name = new StringBuilder();
                for (int i = 0;i < args.length;i++) {
                    name.append(args[i]).append(" ");
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
            sender.sendMessage(ChatColor.GREEN + "Current UHC game status:");
            sender.sendMessage(ChatColor.RED + "In Game: " + gameManager.InGame);
            sender.sendMessage(ChatColor.AQUA + "Is Meetup: " + gameManager.MeetupEnabled);
            sender.sendMessage(ChatColor.GOLD + "PVP allowed: " + gameManager.PvpEnabled);
            sender.sendMessage(ChatColor.BLACK + "People Alive: " + gameManager.AlivePlayers.size());
            if (!(sender instanceof Player))
                return true;
            Player p = (Player) sender;
            sender.sendMessage(ChatColor.BLUE + "WorldBorder: " + (int)p.getWorld().getWorldBorder().getSize());
            if (gameManager.AlivePlayers.contains(p)) {
                sender.sendMessage(ChatColor.GREEN + "You Are Alive!!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "You Are Dead");
            return true;
        }

        if (label.equalsIgnoreCase("uhcend")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /uhcend <winner>");
                return true;
            }
            if (!(args[1].equals("confirm"))) {
                sender.sendMessage(ChatColor.RED +
                        "This command is not needed because when there is one player left it will do it by itself, " +
                        "if you are sure you want to do this type /uhcend <winner> confirm");
                return true;
            }
            Player target;
            try {
                target = Bukkit.getPlayer(args[0]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "That player does not exist! /uhcend <winner> confirm");
                return true;
            }
            if (!(sender.hasPermission("uhc.end"))) {
                sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return false;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a {\"color\":\"green\",\"text\":\"" + args[0] + " has won the UHC!!!!\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + target.getName() + " title {\"color\":\"blue\", \"text\":\"You Have Won The UHC!\"}");
            sender.sendMessage("Ended UHC.");
            sender.sendMessage("Thanks For Using UltimateUHC by CoPokBl!");
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
                } catch(Exception e) {
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
            if (sender.hasPermission("uhc.pvp")) {
                if (args[0].equals("true")) {
                    gameManager.PvpEnabled = true;
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"color\":\"red\", \"text\":\"PVP is Now Enabled!\"}");
                    return true;
                }
                else if (args[0].equals("false")) {
                    gameManager.PvpEnabled = false;
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Usage: /uhcpvp (true/false");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
            return true;
        }

        if (label.equalsIgnoreCase("uhcserverend")) {
            Player player = (Player) sender;
            if (!(player.hasPermission("uhc.stopserver"))) {
                player.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return false;
            }
            player.sendMessage("Closing Server...");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a {\"color\":\"green\",\"text\":\"The Server Will Shutdown In 1 Minute\"}");
            BukkitRunnable closeserver = new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.shutdown();
                }
            };
            closeserver.runTaskLater(Main.plugin, 20 * 60);
        }

        if (label.equalsIgnoreCase("uhcinfo")) {
            sender.sendMessage(ChatColor.BLUE + "UHC info:");
            sender.sendMessage("You will join and get teleported to a random location with the effects slowness, blindness, mining fatugue and resistence.");
            sender.sendMessage("Until the UHC starts you will be invincible. When it starts all the effects will be removed and a title will show.");
            sender.sendMessage("PVP will be disabled until it tells you it has been enabled. During the time before meetup you have time to mine and get resourses.");
            sender.sendMessage("During meetup you are not allowed to be underground or skybasing. You must head to 0, 0. If you don't you will be kicked from the UHC.");
            sender.sendMessage("Have Fun!! Plugin By CoPokBl.");
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
            target.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 255)));
            World uhc = Bukkit.getWorld("uhc");
            double wbSize = uhc.getWorldBorder().getSize()/2;
            Location loc = Utils.GetTopLocation(uhc, (int) GetRandomNum(-wbSize, wbSize), (int) GetRandomNum(-wbSize, wbSize));
            target.teleport(loc);
            target.sendMessage(ChatColor.GREEN + "You Have Been Teleported To A Random Location!");
            // set the block below the target to stone
            uhc.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).setType(Material.STONE);
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
                Bukkit.dispatchCommand(sender, "scatter " + target.getName());
                gameManager.AlivePlayers.remove(target);
                gameManager.AlivePlayers.add(target);
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

        return false;
    }

}

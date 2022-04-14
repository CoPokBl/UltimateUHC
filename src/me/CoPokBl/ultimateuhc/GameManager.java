package me.CoPokBl.ultimateuhc;

import me.CoPokBl.ultimateuhc.EventListeners.WorldProtections;
import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Interfaces.UhcEventType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

import static me.CoPokBl.ultimateuhc.Main.scoreboardManager;
import static me.CoPokBl.ultimateuhc.Utils.GetRandomNum;

public class GameManager {
    public final List<Player> AlivePlayers = new ArrayList<>();
    public List<Scenario> Scenarios = new ArrayList<>();
    public Boolean PvpEnabled = false;
    public Boolean InGame = false;
    public Boolean MeetupEnabled = false;
    public int gameLoopTimer = 0;
    public int TimeToMeetup;
    public int TimeToPvp;

    public void SendPlayer(Player p) {
        scoreboardManager.CreateScoreboard(p);
        scoreboardManager.start(p);
        if (!InGame) {
            AlivePlayers.add(p);
            WorldProtections.NoInteract.add(p);

            // Get the UHC world
            World uhc = Bukkit.getWorld("uhc");
            if (uhc == null) {
                // um wtf
                Bukkit.getLogger().severe(
                        "Uhc world is null, failed to send player. Please restart the server. " +
                                "If this error persists, contact CoPokBl#9451 on Discord.");
                return;
            }

            // Teleport the player within the bounds of the world border
            double wbSize = uhc.getWorldBorder().getSize()/2;
            Location loc = Utils.GetTopLocation(uhc, (int) GetRandomNum(-wbSize, wbSize), (int) GetRandomNum(-wbSize, wbSize));
            p.teleport(loc);
            p.setGameMode(GameMode.SURVIVAL);

            // set the block below the player to stone
            uhc.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).setType(Material.STONE);

            p.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 255)));
            p.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255)));
            p.addPotionEffect((new PotionEffect(PotionEffectType.SLOW, 99999, 255)));
            p.addPotionEffect((new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 255)));
            p.addPotionEffect((new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, 255)));
        }
        else {
            // Game is running
            World uhc = Bukkit.getWorld("uhc");
            Location loc = Utils.GetTopLocation(uhc, 0, 0);
            p.teleport(loc);
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(ChatColor.RED + "You Joined In The Middle Of A Game! You Can't Play.");
        }
    }

    public void SetupPlayer(Player player) {
        for (Scenario scenario : Scenarios) {
            scenario.SetupPlayer(player);
        }
        ItemStack book = new ItemStack(Material.BOOK);
        player.getInventory().addItem(book);
    }


    public void StartUhc() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            SetupPlayer(online);
        }
        InGame = true;
        World uhc = Bukkit.getWorld("uhc");
        if (uhc == null) {
            // um wtf
            Bukkit.getLogger().severe(
                    "Uhc world is null, failed to start uhc. Please restart the server. " +
                         "If this error persists, contact CoPokBl#9451 on Discord.");
            return;
        }
        uhc.setGameRule(GameRule.NATURAL_REGENERATION, false);
        uhc.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        uhc.getWorldBorder().setCenter(new Location(uhc, 0, 100, 0));
        uhc.getWorldBorder().setSize(500);
        for (Player online : Bukkit.getOnlinePlayers()) {
            for (PotionEffect effect : online.getActivePotionEffects()) {
                online.removePotionEffect(effect.getType());
            }
            online.setMaxHealth(20);
            online.setHealth(20);
            online.setFoodLevel(20);

            // display title to all players
            online.sendTitle(ChatColor.GREEN + "The UHC Has Begun!", ChatColor.GREEN + "", 10, 20*3, 10);
        }
        WorldProtections.NoInteract.clear();
        StartGameLoop();

        for (Scenario scenario : Scenarios) {
            scenario.UhcStart();
        }
    }

    private void StartGameLoop() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {

            // runs every 1 seconds
            gameLoopTimer++;

            if (gameLoopTimer >= Main.plugin.getConfig().getInt("secondsToPvp") && !PvpEnabled) {
                // pvp
                PvpEnabled = true;
                World uhc = Bukkit.getWorld("uhc");
                uhc.getWorldBorder().setSize(20, 1800);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendTitle(ChatColor.RED + "PVP is Now Enabled!", ChatColor.GREEN + "", 10, 20*3, 10);
                }

                for (Scenario scenario : Scenarios) {
                    scenario.UhcEvent(UhcEventType.Pvp);
                }
            } else if (gameLoopTimer >= Main.plugin.getConfig().getInt("secondsToMeetup") && !MeetupEnabled) {
                // meetup
                MeetupEnabled = true;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendTitle(ChatColor.RED + "It Is Now Meetup!",  "Goto 0, 0! You Must Stay Above Ground!", 10, 20*3, 10);
                }

                for (Scenario scenario : Scenarios) {
                    scenario.UhcEvent(UhcEventType.Meetup);
                }
            }


        }, 0, 20);
    }

}

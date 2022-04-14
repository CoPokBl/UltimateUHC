package me.CoPokBl.unltimateuhc;

import me.CoPokBl.unltimateuhc.EventListeners.WorldProtections;
import me.CoPokBl.unltimateuhc.Interfaces.Scenario;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static me.CoPokBl.unltimateuhc.Main.scoreboardManager;
import static me.CoPokBl.unltimateuhc.Utils.GetRandomNum;

public class GameManager {
    public final List<Player> AlivePlayers = new ArrayList<>();
    public List<Scenario> Scenarios = new ArrayList<>();
    public Boolean PvpEnabled = false;
    public Boolean InGame = false;
    public Boolean MeetupEnabled = false;

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
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run gamerule naturalRegeneration false");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run worldborder center 0 0");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run worldborder set 500");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run effect clear @a");
        WorldProtections.NoInteract.clear();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"color\":\"blue\", \"text\":\"The UHC Has Begun!\"}");
        BukkitRunnable enablePvp = new BukkitRunnable() {
            //pvp
            @Override
            public void run() {
                PvpEnabled = true;
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"color\":\"red\", \"text\":\"PVP is Now Enabled!\"}");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run worldborder set 20 1800");

            }
        };
        // Run the task on this plugin in 3 seconds (60 ticks)
        enablePvp.runTaskLater(Main.plugin, 20 * 600);

        BukkitRunnable meetup = new BukkitRunnable() {
            //meetup
            @Override
            public void run() {
                MeetupEnabled = true;
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle {\"text\":\"Goto 0, 0! You Must Stay Above Ground!\"}");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"It Is Now Meetup!\",\"color\":\"dark_red\"}");

            }
        };
        meetup.runTaskLater(Main.plugin, 20 * 1900);

        for (Scenario scenario : Scenarios) {
            scenario.UhcStart();
        }
    }

}

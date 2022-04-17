package me.CoPokBl.ultimateuhc;

import me.CoPokBl.ultimateuhc.EventListeners.WorldProtections;
import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Interfaces.UhcEventType;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static me.CoPokBl.ultimateuhc.Main.scoreboardManager;

public class GameManager {
    public final List<UhcPlayer> AlivePlayers = new CopyOnWriteArrayList<>();
    public final List<UhcPlayer> DeadPlayers = new ArrayList<>();
    public final HashMap<UhcPlayer, ItemStack[]> OfflinePlayerInventories = new HashMap<>();
    public final HashMap<UhcPlayer, Location> OfflinePlayerLocations = new HashMap<>();
    public List<Scenario> Scenarios = new ArrayList<>();
    public Boolean PvpEnabled = false;
    public Boolean InGame = false;
    public Boolean MeetupEnabled = false;
    public int gameLoopTimer = 0;
    public int TimeToMeetup;
    public int TimeToPvp;
    public String WorldName;
    public Integer ShutdownOnGameEndTaskId = null;

    public void SendPlayer(Player p) {
        scoreboardManager.CreateScoreboard(p);
        scoreboardManager.start(p);
        if (!InGame) {
            JoinPlayerToGame(p);
        }
        else {
            // Game is running
            if (Utils.IsPlayerAlive(p) && (Main.plugin.getConfig().getBoolean("allowRejoin") ||
                    (!PvpEnabled && Main.plugin.getConfig().getBoolean("allowLateJoin")))) {
                // The player is in the game and is allowed to rejoin
                p.sendMessage(ChatColor.GREEN + "You have rejoined the game.");
                return;
            }
            if (!PvpEnabled && Main.plugin.getConfig().getBoolean("allowLateJoin") && !DeadPlayers.contains(new UhcPlayer(p))) {
                // let them join
                JoinPlayerToGame(p);
                p.sendMessage(ChatColor.GREEN + "You have joined the game late.");
                return;
            }
            // The player is not allowed to rejoin
            World uhc = Bukkit.getWorld(WorldName);
            Location loc = Utils.GetRandomSpawn(uhc);
            p.teleport(loc);
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(ChatColor.RED + "You Joined In The Middle Of A Game! You Can't Play.");
        }
    }

    private void JoinPlayerToGame(Player p) {
        AlivePlayers.add(new UhcPlayer(p.getUniqueId()));

        // Get the UHC world
        World uhc = Bukkit.getWorld(WorldName);
        if (uhc == null) {
            // um wtf
            Bukkit.getLogger().severe(
                    "Uhc world is null, failed to send player. Please restart the server. " +
                            "If this error persists, contact CoPokBl#9451 on Discord.");
            return;
        }

        // Teleport the player within the bounds of the world border
        Location loc = Utils.GetRandomSpawn(uhc);
        p.teleport(loc);

        // set the block below the player to stone
        uhc.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).setType(Material.valueOf(Main.plugin.getConfig().getString("teleportBlock")));

        new BukkitRunnable() {
            @Override
            public void run() {
                p.setGameMode(GameMode.SURVIVAL);
            }
        }.runTaskLater(Main.plugin, 20);

        if (!InGame) { WorldProtections.NoInteract.add(p); }
        // else Game is running


    }

    public void SetupPlayer(Player player) {
        // remove effects
        for (UhcPlayer up : AlivePlayers) {
            Player p = up.getPlayer();
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            p.getInventory().clear();
        }
        for (Scenario scenario : Scenarios) { scenario.SetupPlayer(player); }
        if (!Main.plugin.getConfig().getBoolean("giveBook")) return;
        ItemStack book = new ItemStack(Material.BOOK);
        player.getInventory().addItem(book);
    }


    public void StartUhc() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            SetupPlayer(online);
        }
        InGame = true;
        World uhc = Bukkit.getWorld(WorldName);
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
        uhc.getWorldBorder().setSize(Main.plugin.getConfig().getInt("worldBorderSize"));
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setHealth(20);
            online.setFoodLevel(20);

            // display title to all players
            online.sendTitle(ChatColor.GREEN + "The UHC Has Begun!", ChatColor.GREEN + "", 10, 20*3, 10);
        }
        WorldProtections.NoInteract.clear();  // Allow players to interact
        StartGameLoop();  // Start the game

        for (Scenario scenario : Scenarios) { scenario.UhcStart(); }  // Let the scenarios know the game has started
    }

    public void KillOffline() {
        World uhc = Bukkit.getWorld(WorldName);
        for (UhcPlayer p : AlivePlayers) {
            Bukkit.broadcastMessage(p.getName() + " isOffline: " + !p.isOnline());
            if (!p.isOnline()) {
                AlivePlayers.remove(p);
                DeadPlayers.add(p);

                // Drop their items
                for (ItemStack item : OfflinePlayerInventories.get(p)) {
                    if (item == null) continue;
                    uhc.dropItemNaturally(OfflinePlayerLocations.get(p), item);
                    Bukkit.broadcastMessage(ChatColor.RED + "Player " + p.getName() + " died and dropped " + item.getAmount() + " " + item.getType());
                }
                Bukkit.broadcastMessage(p.getName() + "died due to being offline.");
            }
        }
    }

    private void StartGameLoop() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        boolean borderHasShrunk = false;
        int timeToBorder = Main.plugin.getConfig().getInt("secondsToShrink");
        scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {

            // runs every 1 seconds
            gameLoopTimer++;

            if (gameLoopTimer >= timeToBorder && !borderHasShrunk) {
                // shrink the border
                World uhc = Bukkit.getWorld(WorldName);
                uhc.getWorldBorder().setSize(Main.plugin.getConfig().getInt("borderMinSize"), Main.plugin.getConfig().getInt("secondsToBorderMin"));
            }
            if (gameLoopTimer >= Main.plugin.getConfig().getInt("secondsToPvp") && !PvpEnabled) {
                // pvp

                // kill players who are not in the game if things
                if (!Main.plugin.getConfig().getBoolean("allowRejoin")) {
                    KillOffline();
                }

                PvpEnabled = true;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendTitle(ChatColor.RED + "PVP is Now Enabled!", ChatColor.GREEN + "", 10, 20*3, 10);
                }

                for (Scenario scenario : Scenarios) {
                    scenario.UhcEvent(UhcEventType.Pvp);
                }
            }
            if (gameLoopTimer >= Main.plugin.getConfig().getInt("secondsToMeetup") && !MeetupEnabled) {
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

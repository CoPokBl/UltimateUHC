package me.CoPokBl.ultimateuhc;

import me.CoPokBl.ultimateuhc.EventListeners.WorldProtections;
import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Interfaces.UhcEvent;
import me.CoPokBl.ultimateuhc.Interfaces.UhcEventType;
import me.CoPokBl.ultimateuhc.NMS.NMSHandler;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static me.CoPokBl.ultimateuhc.Main.gameManager;
import static me.CoPokBl.ultimateuhc.Main.scoreboardManager;

public class GameManager {
    public final List<UhcPlayer> AlivePlayers = new CopyOnWriteArrayList<>();
    public final List<UhcPlayer> DeadPlayers = new ArrayList<>();
    public final HashMap<String, ItemStack[]> OfflinePlayerInventories = new HashMap<>();  // UUID, items
    public final HashMap<String, Location> OfflinePlayerLocations = new HashMap<>();  // UUID, items
    public final HashMap<String, LivingEntity> OfflinePlayerEntities = new HashMap<>();  // UUID, entity
    public List<Scenario> Scenarios = new ArrayList<>();
    public Boolean PvpEnabled = false;
    public Boolean InGame = false;
    public Boolean MeetupEnabled = false;
    public int gameLoopTimer = 0;
    public String WorldName;
    public Integer ShutdownOnGameEndTaskId = null;
    public OfflinePlayersManager OfflinePlayersManager = new OfflinePlayersManager();
    public UhcEvent[] Events;
    public UhcEvent NextEvent;

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
            if (!PvpEnabled && Main.plugin.getConfig().getBoolean("allowLateJoin") && !Utils.IsPlayerDead(p)) {
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
        if (loc == null) {
            if (Main.plugin.getConfig().getBoolean("sendPlayersOnJoin")){
                p.kickPlayer(ChatColor.RED + "Unfortunately, we couldn't find a safe spawn location. You can either try again or contact the server staff.");
                return;
            }
            p.sendMessage(ChatColor.RED + "Unfortunately, we couldn't find a safe spawn location. You can either try again or contact the server staff.");
            return;
        }
        p.teleport(loc);

        // set the block below the player to stone
        try {
            Material mat = Material.valueOf(Main.plugin.getConfig().getString("teleportBlock"));
            uhc.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).setType(mat);
        } catch (IllegalArgumentException e) {
            // Don't place the block
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                p.setGameMode(GameMode.SURVIVAL);
                p.getInventory().clear();
            }
        }.runTaskLater(Main.plugin, 20);

        if (!InGame) { WorldProtections.NoInteract.add(p); }
        // else Game is running
    }

    public void SetupPlayer(Player p) {
        // remove effects
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        p.getInventory().clear();
        for (Scenario scenario : Scenarios) { scenario.SetupPlayer(p); }
        if (!Main.plugin.getConfig().getBoolean("giveBook")) return;
        ItemStack book = new ItemStack(Material.BOOK);
        p.getInventory().addItem(book);
    }


    public void StartUhc() {
        for (UhcPlayer online : AlivePlayers) {
            SetupPlayer(online.getPlayer());
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
        NMSHandler.getInstance().nms.setGameRule(uhc, me.CoPokBl.ultimateuhc.OverrideTypes.GameRule.NATURAL_REGENERATION, false);
        if (Main.SpigotVersion >= 15) {
            NMSHandler.getInstance().nms.setGameRule(uhc, me.CoPokBl.ultimateuhc.OverrideTypes.GameRule.IMMEDIATE_RESPAWN, true);
        }
        uhc.getWorldBorder().setCenter(new Location(uhc, 0, 100, 0));
        uhc.getWorldBorder().setSize(Main.plugin.getConfig().getInt("worldBorderSize"));
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setHealth(20);
            online.setFoodLevel(20);

            // display title to all players
            NMSHandler.getInstance().nms.sendTitle(online, ChatColor.GREEN + "The UHC Has Begun!", String.valueOf(ChatColor.GREEN), 10, 20*3, 10);
        }
        WorldProtections.NoInteract.clear();  // Allow players to interact
        StartGameLoop();  // Start the game

        for (Scenario scenario : Scenarios) { scenario.UhcStart(); }  // Let the scenarios know the game has started
        OfflinePlayersManager.StartOfflinePlayerBorderCheck();
    }

    public void WinCheck() {
        if (!(gameManager.AlivePlayers.size() == 1 && gameManager.InGame)) {
            return;
        }
        // run win
        final Player winner = gameManager.AlivePlayers.get(0).getPlayer();
        Bukkit.broadcastMessage(ChatColor.GREEN + winner.getDisplayName() + " has won the UHC!!!!");
        NMSHandler.getInstance().nms.sendTitle(winner, ChatColor.GREEN + "You Have Won The UHC!", "", 10, 20*3, 10);
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.playSound(online.getLocation(), Sound.valueOf(Main.SpigotVersion > 12 ? "ENTITY_ENDER_DRAGON_GROWL" : "ENDERDRAGON_GROWL"), 1, 1);
        }
        if (Main.plugin.getConfig().getBoolean("restartServerOnCompletion")) {
            int secondsToRestart = Main.plugin.getConfig().getInt("restartServerTime");
            gameManager.ShutdownOnGameEndTaskId = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () ->
                    Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            Main.plugin.getConfig().getString("restartCommand")), 20L * secondsToRestart);
            Bukkit.broadcastMessage(ChatColor.RED + "The server will restart in " + secondsToRestart + " seconds!");
            // tell all the operators to type /uhccancelrestart to cancel the restart
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.isOp()) {
                    player.sendMessage(ChatColor.RED + "Type /uhccancelrestart to cancel the restart");
                }
            });
        }
    }

    public String t(String mes) { return ChatColor.translateAlternateColorCodes('&', mes); }

    private void StartGameLoop() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {

            // runs every second
            gameLoopTimer++;

            // events
            World uhc = Bukkit.getWorld(WorldName);
            if (uhc == null) {
                // um wtf
                Bukkit.getLogger().severe(
                        "Uhc world is null, failed to start uhc. Please restart the server. " +
                                "If this error persists, contact CoPokBl#9451 on Discord.");
                return;
            }

            UhcEvent nextEvent = null;
            for (UhcEvent event : Events) {
                if (event.hasRun) continue;
                int currentNextEventTime;
                if (nextEvent == null) {
                    currentNextEventTime = 999999;
                } else {
                    currentNextEventTime = nextEvent.time;
                }
                if (event.time - gameLoopTimer < currentNextEventTime) {
                    nextEvent = event;
                }
                if (event.time > gameLoopTimer) continue;
                // run the event
                if (event.message != null) {
                    Bukkit.broadcastMessage(t(event.message));
                }
                boolean runTitle = false;
                String title = "";
                String subtitle = "";
                if (event.title != null) {
                    runTitle = true;
                    title = t(event.title);
                }
                if (event.subtitle != null) {
                    runTitle = true;
                    subtitle = t(event.subtitle);
                }
                if (runTitle) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        NMSHandler.getInstance().nms.sendTitle(player, title, subtitle, 10, 20*3, 10);
                    }
                }
                if (event.command != null) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), event.command);
                }
                boolean runBorder = false;
                Integer borderSize = null;
                int borderTime = 0;
                if (event.borderSize != null) {
                    runBorder = true;
                    borderSize = event.borderSize;
                }
                if (event.borderTime != null) {
                    runBorder = true;
                    borderTime = event.borderTime;
                }
                if (runBorder && (borderSize != null)) {
                    uhc.getWorldBorder().setSize(borderSize, borderTime);
                }
                if (event.pvp != null) {
                    PvpEnabled = event.pvp;
                    if (PvpEnabled) {
                        if (!Main.plugin.getConfig().getBoolean("allowRejoin")) OfflinePlayersManager.KillOffline();
                        for (Scenario scenario : Scenarios) {
                            scenario.UhcEvent(UhcEventType.Pvp);
                        }
                    }
                }
                if (event.meetup != null) {
                    MeetupEnabled = event.meetup;
                    if (MeetupEnabled) {
                        for (Scenario scenario : Scenarios) {
                            scenario.UhcEvent(UhcEventType.Meetup);
                        }
                    }
                }
                event.hasRun = true;
                Bukkit.getLogger().info("UhcEvent " + event.name + " has run");
            }
            NextEvent = nextEvent;

        }, 0, 20);
    }

}

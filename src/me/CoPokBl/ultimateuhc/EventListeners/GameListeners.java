package me.CoPokBl.ultimateuhc.EventListeners;

import me.CoPokBl.ultimateuhc.Main;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import me.CoPokBl.ultimateuhc.Scoreboard.MainBoard;
import me.CoPokBl.ultimateuhc.ScenarioClasses.Zombies;
import me.CoPokBl.ultimateuhc.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static me.CoPokBl.ultimateuhc.Main.gameManager;

public class GameListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!Main.plugin.getConfig().getBoolean("sendPlayersOnJoin")) {
            // Don't auto join them
            return;
        }
        gameManager.SendPlayer(e.getPlayer());  // It will deal with them
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        BukkitRunnable spec = new BukkitRunnable() {
            @Override
            public void run() {
                Player p = e.getPlayer();
                World uhc = Bukkit.getWorld(gameManager.WorldName);
                assert uhc != null;
                p.teleport(Utils.GetTopLocation(uhc, 0, 0));
                if (!gameManager.Scenarios.contains(new Zombies())) {
                    p.setGameMode(GameMode.SPECTATOR);
                }
            }
        };
        spec.runTaskLater(Main.plugin, 20);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        MainBoard board = new MainBoard(event.getPlayer().getUniqueId());
        if (board.hasID())
            board.stop();
        if (Main.plugin.getConfig().getBoolean("allowRejoin") ||
                (Main.plugin.getConfig().getBoolean("allowLateJoin") && !gameManager.PvpEnabled)) {
            gameManager.OfflinePlayerInventories.put(new UhcPlayer(event.getPlayer()), event.getPlayer().getInventory().getContents());
            gameManager.OfflinePlayerLocations.put(new UhcPlayer(event.getPlayer()), event.getPlayer().getLocation());
            for (ItemStack item : event.getPlayer().getInventory().getContents()) {
                if (item == null) { continue; }
                Bukkit.broadcastMessage(item.getType().toString());
            }
            return;
        }
        if (Utils.IsPlayerAlive(event.getPlayer())) {
            gameManager.AlivePlayers.remove(new UhcPlayer(event.getPlayer()));
            event.getPlayer().setHealth(0);  // Kill them
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        gameManager.AlivePlayers.remove(e.getEntity().getPlayer());
        if (gameManager.AlivePlayers.size() == 1 && gameManager.InGame) {
            // run win
            final Player winner = gameManager.AlivePlayers.get(0).getPlayer();
            Bukkit.broadcastMessage(ChatColor.GREEN + winner.getDisplayName() + " has won the UHC!!!!");
            winner.sendTitle(ChatColor.RED + "You Have Won The UHC!",  "", 10, 20*5, 10);
            gameManager.InGame = false;
            if (Main.plugin.getConfig().getBoolean("restartServerOnCompletion")) {
                int secondsToRestart = Main.plugin.getConfig().getInt("restartServerTime");
                gameManager.ShutdownOnGameEndTaskId = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Main.plugin.getConfig().getString("restartCommand"));
                }, 20L * secondsToRestart);
                Bukkit.broadcastMessage(ChatColor.RED + "The server will restart in " + secondsToRestart + " seconds!");
                // tell all the operators to type /uhccancelrestart to cancel the restart
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.isOp()) {
                        player.sendMessage(ChatColor.RED + "Type /uhccancelrestart to cancel the restart");
                    }
                });
            }
        }
        Player p = e.getEntity().getPlayer();
        p.getWorld().strikeLightningEffect(p.getLocation());

        // Create the head drop item
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
        head.setItemMeta(headMeta);

        // Drop the head
        World world = p.getWorld();
        world.dropItemNaturally(p.getLocation(), head);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (gameManager.PvpEnabled) { return; }
        if (!(event.getDamager() instanceof Player)) { return; }
        if (!(event.getEntity() instanceof Player)) { return; }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) { return; }

        Player player = event.getPlayer();
        if (event.getTo() == null) { return; }
        event.setCancelled(true);
        player.setVelocity(new Vector(0, 0, 0));
        player.teleport(event.getTo());
    }

}

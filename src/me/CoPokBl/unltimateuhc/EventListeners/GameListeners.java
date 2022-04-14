package me.CoPokBl.unltimateuhc.EventListeners;

import me.CoPokBl.unltimateuhc.Main;
import me.CoPokBl.unltimateuhc.Scoreboard.MainBoard;
import me.CoPokBl.unltimateuhc.ScenarioClasses.Zombies;
import me.CoPokBl.unltimateuhc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static me.CoPokBl.unltimateuhc.Main.gameManager;

public class GameListeners implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        BukkitRunnable spec = new BukkitRunnable() {
            @Override
            public void run() {
                Player p = e.getPlayer();
                World uhc = Bukkit.getWorld("uhc");
                assert uhc != null;
                p.teleport(Utils.GetTopLocation(uhc, 0, 0));
                // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run tp " + p.getName() + " 0 200 0");
                if (!gameManager.Scenarios.contains(new Zombies())) {
                    p.setGameMode(GameMode.SPECTATOR);
                }
            }
        };
        spec.runTaskLater(Main.plugin, 20);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        gameManager.AlivePlayers.remove(event.getPlayer());
        MainBoard board = new MainBoard(event.getPlayer().getUniqueId());
        if (board.hasID())
            board.stop();
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        gameManager.AlivePlayers.remove(e.getEntity().getPlayer());
        if (gameManager.AlivePlayers.size() == 1 && gameManager.InGame) {
            // run win
            final Player winner = gameManager.AlivePlayers.get(0);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a {\"color\":\"green\",\"text\":\"" + winner.getName() + " has won the UHC!!!!\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + winner.getName() + " title {\"color\":\"blue\", \"text\":\"You Have Won The UHC!\"}");
            gameManager.InGame = false;
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

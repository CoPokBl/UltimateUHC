package me.CoPokBl.ultimateuhc.EventListeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldProtections implements Listener {
    public static List<Player> NoInteract = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (NoInteract.contains(e.getPlayer())) {
            Location to = e.getTo();
            Location from = e.getFrom();

            to.setX(from.getX());
            to.setZ(from.getZ());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (NoInteract.contains(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (NoInteract.contains(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (NoInteract.contains(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (NoInteract.contains((Player) e.getEntity())) e.setCancelled(true);
    }

}

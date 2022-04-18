package me.CoPokBl.ultimateuhc.EventListeners;

import me.CoPokBl.ultimateuhc.Main;
import me.CoPokBl.ultimateuhc.ScenarioClasses.GoldenPlayers;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class CustomDrops implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e) {

        if (e.isCancelled()) return;

        if (Main.plugin.getConfig().getBoolean("enableAppleDrop")) {
            Material block = e.getBlock().getType();
            if (block.equals(Material.LEAVES) ||
                    block.equals(Material.LEAVES_2)) {
                Player player = e.getPlayer();
                World world = player.getWorld();
                ItemStack apple = new ItemStack(Material.APPLE);
                world.dropItemNaturally(e.getBlock().getLocation(), apple);
            }
        }

        if (!Main.plugin.getConfig().getBoolean("smeltOresAndFood")) return;

        // auto smelt
        if (e.getBlock().getType().equals(Material.IRON_ORE)) {
            // iron
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            World world = e.getPlayer().getWorld();
            ItemStack ironIngots = new ItemStack(Material.IRON_INGOT);
            world.dropItemNaturally(e.getBlock().getLocation(), ironIngots);
        }
        if (e.getBlock().getType().equals(Material.GOLD_ORE) && !Main.gameManager.Scenarios.contains(new GoldenPlayers())) {
            // gold
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            World world = e.getPlayer().getWorld();
            ItemStack goldIngots = new ItemStack(Material.GOLD_INGOT);
            world.dropItemNaturally(e.getBlock().getLocation(), goldIngots);
        }
    }

}

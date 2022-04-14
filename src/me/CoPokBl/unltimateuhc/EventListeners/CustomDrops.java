package me.CoPokBl.unltimateuhc.EventListeners;

import me.CoPokBl.unltimateuhc.Main;
import me.CoPokBl.unltimateuhc.ScenarioClasses.GoldenPlayers;
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

        if (!e.isDropItems()) return;

        Material block = e.getBlock().getType();
        if (block.equals(Material.OAK_LEAVES) || block.equals(Material.SPRUCE_LEAVES) || block.equals(Material.DARK_OAK_LEAVES) || block.equals(Material.BIRCH_LEAVES) || block.equals(Material.ACACIA_LEAVES) || block.equals(Material.JUNGLE_LEAVES)) {
            Player player = e.getPlayer();
            World world = player.getWorld();
            ItemStack apple = new ItemStack(Material.APPLE);
            world.dropItemNaturally(e.getBlock().getLocation(), apple);
        }

        // auto smelt
        if (e.getBlock().getType().equals(Material.IRON_ORE)) {
            // iron
            e.setDropItems(false);
            World world = e.getPlayer().getWorld();
            ItemStack ironIngots = new ItemStack(Material.IRON_INGOT);
            world.dropItemNaturally(e.getBlock().getLocation(), ironIngots);
        }
        if (e.getBlock().getType().equals(Material.GOLD_ORE) && !Main.gameManager.Scenarios.contains(new GoldenPlayers())) {
            // gold
            e.setDropItems(false);
            World world = e.getPlayer().getWorld();
            ItemStack goldIngots = new ItemStack(Material.GOLD_INGOT);
            world.dropItemNaturally(e.getBlock().getLocation(), goldIngots);
        }
    }

}

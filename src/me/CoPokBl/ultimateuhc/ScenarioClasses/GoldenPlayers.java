package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class GoldenPlayers extends ScenarioListener {

    @Override
    public String GetName() {
        return "GoldenPlayers";
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.DIAMOND_ORE) || event.getBlock().getType().equals(Material.GOLD_ORE)) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        World world = p.getWorld();
        ItemStack diamonds = new ItemStack(Material.DIAMOND, 2);
        ItemStack goldes = new ItemStack(Material.GOLD_INGOT, 5);
        world.dropItemNaturally(p.getLocation(), diamonds);
        world.dropItemNaturally(p.getLocation(), goldes);
    }

}

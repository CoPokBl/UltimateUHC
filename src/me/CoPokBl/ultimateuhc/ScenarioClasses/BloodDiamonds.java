package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodDiamonds extends ScenarioListener {

    @Override
    public String GetName() {
        return "BloodDiamonds";
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.getBlock().getType().equals(org.bukkit.Material.DIAMOND_ORE)) return;
        e.getPlayer().damage(2);
    }

}

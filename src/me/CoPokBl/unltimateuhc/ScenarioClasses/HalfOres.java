package me.CoPokBl.unltimateuhc.ScenarioClasses;

import me.CoPokBl.unltimateuhc.Interfaces.ScenarioListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import static me.CoPokBl.unltimateuhc.Utils.GetRandomNum;

public class HalfOres extends ScenarioListener {

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        int ran = 2;
        ran = (int) GetRandomNum(0, 2);
        if (ran == 1) {
            event.setDropItems(false);
        }
    }

}

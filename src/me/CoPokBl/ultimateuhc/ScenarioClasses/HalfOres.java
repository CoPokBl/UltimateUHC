package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import static me.CoPokBl.ultimateuhc.Utils.GetRandomNum;

public class HalfOres extends ScenarioListener {

    @Override
    public String GetName() {
        return "HalfOres";
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        int ran = 2;
        ran = (int) GetRandomNum(0, 2);
        if (ran == 1) {
            event.setDropItems(false);
        }
    }

}

package me.CoPokBl.unltimateuhc.ScenarioClasses;

import me.CoPokBl.unltimateuhc.Interfaces.ScenarioListener;
import me.CoPokBl.unltimateuhc.Interfaces.UhcEventType;
import me.CoPokBl.unltimateuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Fallout extends ScenarioListener {

    @Override
    public String GetName() {
        return "Fallout";
    }

    @Override
    public void UhcEvent(UhcEventType eventType) {
        if (eventType != UhcEventType.Meetup) {
            return;
        }
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {
            for (Player targetPlayer : Bukkit.getOnlinePlayers()) {
                if (targetPlayer.getLocation().getY() > 60) {
                    targetPlayer.damage(1);
                }
            }
        }, 0, 40L);
    }

}

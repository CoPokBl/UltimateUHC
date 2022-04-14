package me.CoPokBl.unltimateuhc.ScenarioClasses;

import me.CoPokBl.unltimateuhc.Interfaces.Scenario;
import me.CoPokBl.unltimateuhc.Interfaces.UhcEventType;
import me.CoPokBl.unltimateuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class SkyHigh extends Scenario {

    @Override
    public String GetName() {
        return "SkyHigh";
    }

    @Override
    public void UhcStart() {

    }

    @Override
    public void UhcEvent(UhcEventType eventType) {
        if (eventType != UhcEventType.Meetup) {
            return;
        }
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {

            // Damage players below Y = 100 every 2 seconds
            for (Player targetPlayer : Bukkit.getOnlinePlayers()) {
                if (targetPlayer.getLocation().getY() < 100) {
                    targetPlayer.damage(1);
                }
            }

        }, 0, 40L);
    }

}

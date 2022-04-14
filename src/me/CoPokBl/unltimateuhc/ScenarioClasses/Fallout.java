package me.CoPokBl.unltimateuhc.ScenarioClasses;

import me.CoPokBl.unltimateuhc.Interfaces.ScenarioListener;
import me.CoPokBl.unltimateuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Fallout extends ScenarioListener {

    @Override
    public void UhcStart() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {
            for (Player targetPlayer : Bukkit.getOnlinePlayers()) {
                if (targetPlayer.getLocation().getY() > 60) {
                    targetPlayer.damage(1);
                }
            }
        }, 38000L, 40L);
    }

}

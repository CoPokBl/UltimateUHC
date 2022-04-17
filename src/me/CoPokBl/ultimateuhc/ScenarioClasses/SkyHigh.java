package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Interfaces.UhcEventType;
import me.CoPokBl.ultimateuhc.Main;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class SkyHigh extends Scenario {

    int taskID = -1;

    @Override
    public String GetName() {
        return "SkyHigh";
    }

    @Override
    public void Disable() {
        super.Disable();
        if (taskID != -1) {
            Bukkit.getServer().getScheduler().cancelTask(taskID);
        }
    }

    @Override
    public void UhcEvent(UhcEventType eventType) {
        if (eventType != UhcEventType.Meetup) {
            return;
        }
        BukkitScheduler scheduler = Bukkit.getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {

            // Damage players below Y = 100 every 2 seconds
            for (UhcPlayer targetPlayer : Main.gameManager.AlivePlayers) {
                if (targetPlayer.getPlayer().getLocation().getY() < 100) {
                    targetPlayer.getPlayer().damage(1);
                }
            }

        }, 0, 40L);
    }

}

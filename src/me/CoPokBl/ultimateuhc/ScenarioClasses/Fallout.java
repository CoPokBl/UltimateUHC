package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import me.CoPokBl.ultimateuhc.Interfaces.UhcEventType;
import me.CoPokBl.ultimateuhc.Main;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Fallout extends ScenarioListener {

    int taskID = -1;

    @Override
    public String GetName() {
        return "Fallout";
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
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(Main.plugin, () -> {
            for (UhcPlayer targetPlayer : Main.gameManager.AlivePlayers) {
                if (targetPlayer.getPlayer().getLocation().getY() > 60) {
                    targetPlayer.getPlayer().damage(1);
                }
            }
        }, 0, 40L);
    }

}

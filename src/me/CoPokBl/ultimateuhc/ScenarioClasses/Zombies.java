package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import me.CoPokBl.ultimateuhc.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Zombies extends ScenarioListener {

    @Override
    public String GetName() {
        return "Zombies";
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        BukkitRunnable spec = new BukkitRunnable() {
            @Override
            public void run() {
                p.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 255)));
                p.setGameMode(GameMode.ADVENTURE);
            }
        };
        spec.runTaskLater(Main.plugin, 20);
    }

}

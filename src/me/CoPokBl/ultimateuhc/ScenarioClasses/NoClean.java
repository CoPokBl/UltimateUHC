package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class NoClean extends ScenarioListener {

    @Override
    public String GetName() {
        return "NoClean";
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // give killer resistance and weakness
        Player killer = event.getEntity().getKiller();
        if (killer == null) { return; }
        killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 255));
        killer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 255));
        killer.sendMessage(ChatColor.GREEN + "You cannot attack or be attacked for 20 seconds");
    }

}

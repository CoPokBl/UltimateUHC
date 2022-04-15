package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import org.bukkit.event.EventHandler;

public class NoFall extends ScenarioListener {

    @Override
    public String GetName() {
        return "NoFall";
    }

    @EventHandler
    public void onDamage(org.bukkit.event.entity.EntityDamageEvent e) {
        if (e.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }

}

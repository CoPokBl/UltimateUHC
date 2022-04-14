package me.CoPokBl.unltimateuhc.ScenarioClasses;

import me.CoPokBl.unltimateuhc.Interfaces.ScenarioListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DoubleDamage extends ScenarioListener {

    @Override
    public String GetName() {
        return "DoubleDamage";
    }
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) { return; }
        event.setDamage(event.getDamage()*2);
    }

}

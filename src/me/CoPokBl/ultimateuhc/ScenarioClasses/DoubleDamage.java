package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import me.CoPokBl.ultimateuhc.Main;
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
        if (!Main.gameManager.AlivePlayers.contains(((Player) event.getDamager()).getPlayer())) { return; }
        event.setDamage(event.getDamage()*2);
    }

}

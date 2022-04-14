package me.CoPokBl.ultimateuhc.Interfaces;

import me.CoPokBl.ultimateuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class ScenarioListener extends Scenario implements Listener {

    @Override
    public void Enable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    @Override
    public void Disable() {
        HandlerList.unregisterAll(this);
    }

}

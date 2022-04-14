package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class Endermen extends ScenarioListener {

    @Override
    public String GetName() {
        return "Endermen";
    }

    @Override
    public void SetupPlayer(Player player) {
        player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) { return; }
        event.getPlayer().getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
    }

}

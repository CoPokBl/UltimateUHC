package me.CoPokBl.unltimateuhc.ScenarioClasses;

import me.CoPokBl.unltimateuhc.Interfaces.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Chicken extends Scenario {

    @Override
    public void SetupPlayer(Player player) {
        player.setHealth(1);
        player.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
    }

}

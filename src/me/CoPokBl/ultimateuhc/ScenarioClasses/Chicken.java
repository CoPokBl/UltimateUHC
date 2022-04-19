package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Chicken extends Scenario {

    @Override
    public String GetName() {
        return "Chicken";
    }

    @Override
    public void SetupPlayer(Player player) {
        player.setHealth(1);
        player.getInventory().addItem(new ItemStack(Material.valueOf(Main.SpigotVersion <= 12 ? "GOLDEN_APPLE" : "ENCHANTED_GOLDEN_APPLE"), 1));
    }

}

package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OneTable extends ScenarioListener {

    @Override
    public String GetName() {
        return "OneTable";
    }

    private final List<Player> hasMadeTable = new ArrayList<>();

    @EventHandler
    public void playerCraftEvent(CraftItemEvent e) {
        ItemStack[] item = e.getInventory().getMatrix();
        Player p = (Player) e.getWhoClicked();
        Material itemType = e.getRecipe().getResult().getType();

        Material table = Material.CRAFTING_TABLE;
        if (!(hasMadeTable.contains(p)) && itemType.equals(table)) {
            hasMadeTable.add(p);
            p.sendMessage(ChatColor.GREEN + "You can only make one crafting table, that was your last one!");
            return;
        }
        if (itemType.equals(table)) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "You have already made a crafting table!");
        }
    }

}

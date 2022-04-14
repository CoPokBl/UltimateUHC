package me.CoPokBl.unltimateuhc.EventListeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GoldenHeads implements Listener {

    @EventHandler
    public void onPlayerEatFood(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        Player p = (Player) event.getPlayer();
        if (event.getItem().getType().equals(Material.GOLDEN_APPLE) && item.getItemMeta().getDisplayName().contains("Golden Head")) {
            // Player ate golden head
            p.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1)));
        }
    }

}

package me.CoPokBl.ultimateuhc;

import me.CoPokBl.ultimateuhc.Interfaces.RewardType;
import me.CoPokBl.ultimateuhc.NMS.NMSHandler;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.UUID;

import static me.CoPokBl.ultimateuhc.Main.gameManager;

public class OfflinePlayersManager implements Listener {

    public void StartOfflinePlayerBorderCheck() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, () -> {
            for (int i = 0; i < gameManager.OfflinePlayerLocations.values().size(); i++) {
                Location loc = gameManager.OfflinePlayerLocations.values().toArray(new Location[0])[i];
                if (!Utils.IsLocationInsideBorder(loc)) {
                    // Kill them
                    UhcPlayer p = new UhcPlayer(UUID.fromString(gameManager.OfflinePlayerLocations.keySet().toArray(new String[0])[i]));
                    Entity ent = gameManager.OfflinePlayerEntities.get(p.getUUID().toString());
                    if (ent == null) {
                        // No entity, no problem
                        return;
                    }
                    ent.remove();
                    Kill(p, p.getName() + " was killed by the border while offline");
                    gameManager.OfflinePlayerLocations.remove(gameManager.OfflinePlayerLocations.keySet().toArray(new String[0])[i]);
                }
            }
        }, 0, 20*5);  // Every 5 seconds
    }

    public void KillOffline() {
        World uhc = Bukkit.getWorld(Main.gameManager.WorldName);
        for (UhcPlayer p : Main.gameManager.AlivePlayers) {
            if (!p.isOnline()) {
                Main.gameManager.AlivePlayers.remove(p);
                Main.gameManager.DeadPlayers.add(p);

                // Drop their items
                Location loc = gameManager.OfflinePlayerLocations.get(p.getUUID().toString());
                if (loc == null) { Bukkit.getLogger().severe("Location doesn't exist, what the hell happened?"); continue; }
                for (ItemStack item : gameManager.OfflinePlayerInventories.get(p.getUUID().toString())) {
                    if (item == null) continue;
                    assert uhc != null;
                    uhc.dropItemNaturally(loc, item);
                }
                Bukkit.broadcastMessage(p.getName() + "died due to being offline");
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (!Main.plugin.getConfig().getBoolean("allowRejoin") &&
                !(Main.plugin.getConfig().getBoolean("allowLateJoin") && !gameManager.PvpEnabled)) {
            return;
        }

        if (!Utils.IsPlayerAlive(e.getPlayer())) {
            return;
        }

        gameManager.OfflinePlayerLocations.remove(e.getPlayer().getUniqueId().toString());
        gameManager.OfflinePlayerInventories.remove(e.getPlayer().getUniqueId().toString());

        // Remove their entity
        Entity ent = gameManager.OfflinePlayerEntities.get(e.getPlayer().getUniqueId().toString());
        if (ent == null) {
            // No entity, no problem
            return;
        }
        ent.remove();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (!Main.plugin.getConfig().getBoolean("allowRejoin") &&
                !(Main.plugin.getConfig().getBoolean("allowLateJoin") && !gameManager.PvpEnabled)) {
            return;
        }

        gameManager.OfflinePlayerInventories.put(event.getPlayer().getUniqueId().toString(), event.getPlayer().getInventory().getContents());
        gameManager.OfflinePlayerLocations.put(event.getPlayer().getUniqueId().toString(), event.getPlayer().getLocation());

        // Add mob in their position that acts as them
        World uhc = Bukkit.getWorld(Main.gameManager.WorldName);
        Entity entity = uhc.spawnEntity(event.getPlayer().getLocation(), EntityType.VILLAGER);
        entity.setCustomNameVisible(true);
        entity.setCustomName(ChatColor.BOLD + event.getPlayer().getName());

        // make entity livingentity
        LivingEntity entityLiving = (LivingEntity) entity;
        entityLiving.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 999999));
        entityLiving.setRemoveWhenFarAway(false);
        entityLiving.setHealth(event.getPlayer().getHealth());
        entityLiving.setCanPickupItems(false);

        gameManager.OfflinePlayerEntities.put(event.getPlayer().getUniqueId().toString(), entityLiving);

    }

    @EventHandler
    public void onVillagerGetJob(VillagerAcquireTradeEvent e) {
        for (UhcPlayer p : Main.gameManager.AlivePlayers) {
            if (Objects.equals(e.getEntity().getCustomName(), ChatColor.BOLD + p.getName())) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        if (!(e.getEntity() instanceof LivingEntity)) {
            return;
        }

        boolean found = false;
        for (UhcPlayer p : Main.gameManager.AlivePlayers) {
            if (Objects.equals(e.getEntity().getCustomName(), ChatColor.BOLD + p.getName())) {
                found = true;
                break;
            }
        }
        if (!found) return;

        if (gameManager.PvpEnabled) return;

        e.setCancelled(true);

        e.getDamager().sendMessage(ChatColor.RED + "You can't attack offline players until PvP is enabled");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if (!Main.plugin.getConfig().getBoolean("allowRejoin") &&
                !(Main.plugin.getConfig().getBoolean("allowLateJoin") && !gameManager.PvpEnabled)) {
            return;
        }

        if (e.getEntity() instanceof Player) {
            return;
        }

        // check if entity has the tag
        boolean found = false;
        UhcPlayer p = null;
        for (UhcPlayer pl : Main.gameManager.AlivePlayers) {
            if (Objects.equals(e.getEntity().getCustomName(), ChatColor.BOLD + pl.getName())) {
                found = true;
                p = pl;
                break;
            }
        }
        if (!found) return;

        if (p.isOnline()) {
            Bukkit.getLogger().severe("Player is online but their offline replacement died, what the hell happened?\n" +
                    "You can probably ignore this message.");
            return;
        }

        // RIP
        String deathMessage;
        if (e.getEntity().getKiller() == null) {
            deathMessage = p.getName() + " died.";
        } else {
            deathMessage = p.getName() + " was slain by " + e.getEntity().getKiller().getName();
        }

        Kill(p, deathMessage);
        if (e.getEntity().getKiller() != null) {
            RewardManager.RewardPlayer(RewardType.PlayerKill, new UhcPlayer(e.getEntity().getKiller()));
        }

        e.setDroppedExp(0);
        e.getDrops().clear();
    }

    public void Kill(UhcPlayer p, String deathMessage) {
        // the player died
        World uhc = Bukkit.getWorld(Main.gameManager.WorldName);
        Utils.RemovePlayerFromAlive(p);
        gameManager.DeadPlayers.add(p);

        Location loc = gameManager.OfflinePlayerLocations.get(p.getUUID().toString());
        if (loc == null) { Bukkit.getLogger().severe("Location doesn't exist, what the hell happened?"); return; }
        assert uhc != null;
        uhc.strikeLightningEffect(loc);
        RewardManager.RewardPlayer(RewardType.PlayerDeath, p);

        // Drop their items
        for (ItemStack item : gameManager.OfflinePlayerInventories.get(p.getUUID().toString())) {
            if (item == null) continue;
            uhc.dropItemNaturally(loc, item);
        }

        Bukkit.broadcastMessage(deathMessage);
        gameManager.WinCheck();
    }



}

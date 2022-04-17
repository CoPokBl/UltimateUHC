package me.CoPokBl.ultimateuhc.OverrideTypes;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UhcPlayer {
    private final String name;
    private final UUID uuid;

    public UhcPlayer(String name) {
        this.name = name;
        Player player = org.bukkit.Bukkit.getPlayer(name);
        if (player == null) {
            throw new IllegalArgumentException("Only online players can be converted to UhcPlayer using the name constructor");
        }
        this.uuid = player.getUniqueId();
    }

    public UhcPlayer(UUID id) {
        name = org.bukkit.Bukkit.getOfflinePlayer(id).getName();
        uuid = id;
    }

    public UhcPlayer(Player player) {
        name = player.getName();
        uuid = player.getUniqueId();
    }

    public String getName() {
        return this.name;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public org.bukkit.entity.Player getPlayer() {
        return org.bukkit.Bukkit.getPlayer(this.uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return org.bukkit.Bukkit.getOfflinePlayer(this.uuid);
    }

    public boolean isOnline() {
        return getOfflinePlayer().isOnline();
    }

}

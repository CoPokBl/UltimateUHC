package me.CoPokBl.ultimateuhc.OverrideTypes;

import org.bukkit.Bukkit;

import java.util.UUID;

public class World {
    private String name;
    private UUID uuid;

    public World(String name) {
        org.bukkit.World world = Bukkit.getWorld(name);
        if (world == null) {
            return;
        }
        this.name = world.getName();
        this.uuid = world.getUID();
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isValid() {
        return Bukkit.getWorld(uuid) != null;
    }
}

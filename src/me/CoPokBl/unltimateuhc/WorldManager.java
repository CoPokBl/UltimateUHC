package me.CoPokBl.unltimateuhc;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

public class WorldManager {

    public WorldManager() { }

    public void unloadWorld(World world) {
        if (world != null) { Bukkit.getServer().unloadWorld(world, true); }
    }

    public void DeleteWorld(String worldName) {
        World delete = Bukkit.getWorld(worldName);
        if (delete == null) { return; }
        File deleteFolder = delete.getWorldFolder();
        try {
            DeleteWorld2(deleteFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteWorld2(File path) throws Exception {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) return;  // failed
            for (File file : files) {
                if (file.isDirectory()) {
                    DeleteWorld2(file);
                } else {
                    if (!file.delete()) return;  // failed
                }
            }
        }
        boolean result = path.delete();  // Indicate success or failure
        if (!result) throw new Exception("Failed to delete folder");  // failed
    }

    public void IfUhcWorldExistsDelete() {
        if (Bukkit.getWorld("uhc") == null)
            return;
        unloadWorld(Bukkit.getWorld("uhc"));
        DeleteWorld("uhc");
    }

}

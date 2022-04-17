package me.CoPokBl.ultimateuhc;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class WorldManager {

    public File WorldPath;

    public WorldManager() { }

    public void unloadWorld(World world) {
        if (world != null) { Bukkit.getServer().unloadWorld(world, true); }
    }

    public void DeleteWorld(String worldName) {
//        World delete = Bukkit.getWorld(worldName);
//        if (delete == null) { Bukkit.getLogger().info("World is null, not deleting."); return; }
//        File deleteFolder = delete.getWorldFolder();
        boolean success = DeleteWorld2(WorldPath);
        if (!success) {
            Bukkit.getLogger().severe("Failed to delete world folder");
        }
    }

    public boolean DeleteWorld2(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                Bukkit.getLogger().severe("Failed to list files in " + path.getAbsolutePath());
                return false;  // failed
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    DeleteWorld2(file);
                } else {
                    if (!file.delete()) {
                        Bukkit.getLogger().severe("Failed to delete file: " + file.getAbsolutePath());
                        return false;  // failed
                    }
                }
            }
        } else {
            Bukkit.getLogger().severe("Failed to delete folder: " + path.getAbsolutePath());
            Bukkit.getLogger().severe("Folder does not exist.");
            return false;  // failed
        }
        return path.delete();  // Indicate success or failure
    }

    public void IfUhcWorldExistsDelete() {
        if (Bukkit.getWorld(Main.gameManager.WorldName) == null) {
            Bukkit.getLogger().info("Uhc world does not exist, cannot delete.");
            return;
        }
        unloadWorld(Bukkit.getWorld(Main.gameManager.WorldName));
        DeleteWorld(Main.gameManager.WorldName);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                  // Run later as to make sure the world is unloaded
//            }
//        }.runTaskLater(Main.plugin, 20L);
    }

}

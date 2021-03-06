package me.CoPokBl.ultimateuhc;

import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class Utils {

    public static Location GetTopLocation(World world, int x, int z) {

        int posy = 255;

        while (world.getBlockAt(x, posy, z).getType() == Material.AIR) {
            posy--;
            if (posy < -300) return null;
        }
        posy++;
        return new Location(world, x+0.5, posy, z+0.5);
    }

    public static Location GetValidTopLocation(World world, int x, int z) {
        for (int i = 255; i > -200; i--) {
            Block block = world.getBlockAt(x, i, z);
            if (block.getType() == Material.AIR) {
                continue;
            }
            if (block.getType() != Material.BEDROCK && block.getType() != Material.LAVA) {  // don't spawn on bedrock or lava
                if (world.getBlockAt(x, i+2, z).getType() != Material.AIR || world.getBlockAt(x, i+1, z).getType() != Material.AIR) {
                    // don't spawn player in blocks
                    continue;
                }
                return new Location(world, x+0.5, i+1, z+0.5);
            }
            // continue until block is air again
            int loops = 0;
            while (block.getType() != Material.AIR) {
                i--;
                loops++;
                if (loops > 1000) return null;
                block = world.getBlockAt(x, i, z);
            }
        }
        return null;
    }

    public static Location GetRandomSpawn(World world) {
        double wbSize = world.getWorldBorder().getSize()/2;
        double x;
        double z;
        Location loc = null;

        int loops = 0;
        while (loc == null) {
            x = GetRandomNum(-wbSize, wbSize);
            z = GetRandomNum(-wbSize, wbSize);
            loc = GetValidTopLocation(world, (int)x, (int)z);
            loops++;
            if (loops > 100) return null;  // no spawn found somehow
        }
        return loc;
    }

    public static int GetVersion() {
        String versionS = Bukkit.getVersion();
        Bukkit.getLogger().info("Spigot's version string: " + versionS);

        String ver = versionS.split(" ")[2];
        ver = ver.replace(')', ' ');
        ver = ver.split("\\.")[1];
        return Integer.parseInt(ver);
    }

    public static double GetRandomNum(double min, double max) {
        return (int)((Math.random()*((max-min)+1))+min);
    }

    public static boolean ContainsScenario(List<Scenario> scenarios, Scenario scenario) {
        for (Scenario s : scenarios) {
            if (Objects.equals(s.GetName(), scenario.GetName())) return true;
        }
        return false;
    }

    public static int GetIndexOfScenario(List<Scenario> scenarios, Scenario scenario) {
        for (int i = 0; i < scenarios.size(); i++) {
            Scenario s = scenarios.get(i);
            if (Objects.equals(s.GetName(), scenario.GetName())) return i;
        }
        return -1;
    }

    // seconds to hours minutes seconds
    public static String GetTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        if (hours > 0) return hours + "h " + minutes + "m " + seconds + "s";
        else if (minutes > 0) return minutes + "m " + seconds + "s";
        else return seconds + "s";
    }

    public static boolean IsPlayerAlive(Player player) {
        for (UhcPlayer p : Main.gameManager.AlivePlayers) {
            if (p.getUUID().equals(player.getUniqueId())) return true;
        }
        return false;
    }

    public static boolean IsPlayerDead(Player player) {
        for (UhcPlayer p : Main.gameManager.DeadPlayers) {
            if (p.getUUID().equals(player.getUniqueId())) return true;
        }
        return false;
    }

    public static void RemovePlayerFromAlive(Player player) {
        for (UhcPlayer p : Main.gameManager.AlivePlayers) {
            if (p.getUUID().equals(player.getUniqueId())) {
                Main.gameManager.AlivePlayers.remove(p);
                return;
            }
        }
    }

    public static void RemovePlayerFromAlive(UhcPlayer player) {
        for (UhcPlayer p : Main.gameManager.AlivePlayers) {
            if (p.getUUID().equals(player.getUUID())) {
                Main.gameManager.AlivePlayers.remove(p);
                return;
            }
        }
    }

    public static boolean IsLocationInsideBorder(Location location) {
        World world = location.getWorld();
        double wbSize = world.getWorldBorder().getSize()/2;
        double x = location.getX();
        double z = location.getZ();
        return x > -wbSize && x < wbSize && z > -wbSize && z < wbSize;
    }

}

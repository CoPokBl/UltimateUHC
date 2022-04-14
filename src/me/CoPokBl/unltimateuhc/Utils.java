package me.CoPokBl.unltimateuhc;

import me.CoPokBl.unltimateuhc.Interfaces.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static Location GetTopLocation(World world, int x, int z) {

        int posy = 255;

        while (world.getBlockAt(x, posy, z).getType() == Material.AIR) {
            posy--;
        }
        posy++;
        return new Location(world, x, posy, z);
    }

    public static int GetVersion() {
        String versionS = Bukkit.getVersion();

        for (int i = 0; i < 99; i++) {
            if (versionS.contains("1." + i))  return i;
        }
        return 100;
    }

    public static double GetRandomNum(double min, double max) {
        return (int)((Math.random()*((max-min)+1))+min);
    }

    public static boolean ContainsScenario(List<Scenario> scenarios, Scenario scenario) {
        for (Scenario s : scenarios) {
            if (Objects.equals(s.name, scenario.name)) return true;
        }
        return false;
    }

    public static int GetIndexOfScenario(List<Scenario> scenarios, Scenario scenario) {
        for (int i = 0; i < scenarios.size(); i++) {
            Scenario s = scenarios.get(i);
            if (Objects.equals(s.name, scenario.name)) return i;
        }
        return -1;
    }

}

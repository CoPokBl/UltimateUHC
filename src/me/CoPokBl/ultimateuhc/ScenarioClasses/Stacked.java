package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.ScenarioListener;
import me.CoPokBl.ultimateuhc.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static me.CoPokBl.ultimateuhc.Utils.GetRandomNum;

public class Stacked extends ScenarioListener {

    @Override
    public String GetName() {
        return "Stacked";
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Location loc = event.getBlock().getLocation();

        World world = p.getWorld();
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        int dia = (int) GetRandomNum(1, 2);
        int iro = (int) GetRandomNum(1, 10);
        int gol = (int) GetRandomNum(1, 5);
        int woo = (int) GetRandomNum(1, 20);
        int apl = (int) GetRandomNum(1, 10);
        int lap = (int) GetRandomNum(1, 3);
        int obs = (int) GetRandomNum(1, 2);
        ItemStack diam = new ItemStack(Material.DIAMOND, dia);
        ItemStack iron = new ItemStack(Material.IRON_INGOT, iro);
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, gol);
        ItemStack wood = new ItemStack(Material.valueOf(Main.SpigotVersion > 12 ? "OAK_LOG" : "LOG"), woo);
        ItemStack appl = new ItemStack(Material.APPLE, apl);
        ItemStack lapi = new ItemStack(Material.LAPIS_ORE, lap);
        ItemStack obsi = new ItemStack(Material.OBSIDIAN, obs);
        world.dropItemNaturally(loc, diam);
        world.dropItemNaturally(loc, iron);
        world.dropItemNaturally(loc, gold);
        world.dropItemNaturally(loc, wood);
        world.dropItemNaturally(loc, appl);
        world.dropItemNaturally(loc, lapi);
        world.dropItemNaturally(loc, obsi);
    }

}

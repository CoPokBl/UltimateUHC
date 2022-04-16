package me.CoPokBl.ultimateuhc.ScenarioClasses;

import me.CoPokBl.ultimateuhc.Interfaces.Scenario;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class SuperHeroes extends Scenario {

    @Override
    public String GetName() {
        return "SuperHeroes";
    }

    @Override
    public void SetupPlayer(Player p) {
        // generate random number between 0 and 10
        Random r = new Random();

        switch (r.nextInt(5)) {
            case 0 -> {
                p.sendMessage(ChatColor.GREEN + "You were given super strength!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 1, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 3, false, false));
            }
            case 1 -> {
                p.sendMessage(ChatColor.GREEN + "You were given super jump!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 5, false, false));
            }
            case 2 -> {
                p.sendMessage(ChatColor.GREEN + "You were given super speed!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 5, false, false));
            }
            case 3 -> {
                p.sendMessage(ChatColor.GREEN + "You were given super toughness!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 5, false, false));
            }
            case 4 -> {
                p.sendMessage(ChatColor.GREEN + "You were given super stealth!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 1, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1, false, false));
            }
            case 5 -> {
                p.sendMessage(ChatColor.GREEN + "You were given obsidian skin!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 1, false, false));
            }
        }
    }

}
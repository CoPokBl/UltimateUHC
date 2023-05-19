package me.CoPokBl.ultimateuhc.NMS;

import me.CoPokBl.ultimateuhc.OverrideTypes.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface NMSVersion {
    void setMobAi(org.bukkit.entity.LivingEntity entity, boolean ai);
    void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut);
    <T> void setGameRule(World world, GameRule rule, T value);
}

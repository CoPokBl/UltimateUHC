package me.CoPokBl.ultimateuhc.NMS.Versions;

import me.CoPokBl.ultimateuhc.NMS.NMSVersion;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Version17 implements NMSVersion {

    @Override
    public void setMobAi(LivingEntity entity, boolean ai) {
        entity.setCanPickupItems(false);
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        craftEntity.setAI(ai);
    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer player = (CraftPlayer) p;
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void setGameRule(World world, me.CoPokBl.ultimateuhc.OverrideTypes.GameRule rule, T value) {
        CraftWorld craftWorld = (CraftWorld) world;
        GameRule<?> gameRule;
        switch (rule) {
            case IMMEDIATE_RESPAWN -> gameRule = GameRule.DO_IMMEDIATE_RESPAWN;
            case NATURAL_REGENERATION -> gameRule = GameRule.NATURAL_REGENERATION;
            default -> throw new IllegalStateException("Unexpected value: " + rule);
        }
        craftWorld.setGameRule((GameRule<T>) gameRule, value);
    }

}

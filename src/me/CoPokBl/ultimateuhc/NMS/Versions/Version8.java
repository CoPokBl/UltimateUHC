package me.CoPokBl.ultimateuhc.NMS.Versions;

import me.CoPokBl.ultimateuhc.NMS.NMSVersion;
import me.CoPokBl.ultimateuhc.OverrideTypes.GameRule;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Version8 implements NMSVersion {

    public void setMobAi(LivingEntity entity, boolean ai) {
        EntityLiving handle = ((CraftLivingEntity) entity).getHandle();
        byte flag = (byte) (ai ? 0 : 1);
        handle.getDataWatcher().watch(15, flag);
    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer player = (CraftPlayer) p;
        player.sendTitle(title, subtitle);
    }

    @Override
    public <T> void setGameRule(World world, GameRule rule, T value) {
        String ruleName;
        switch (rule) {
            case IMMEDIATE_RESPAWN -> ruleName = "doImmediateRespawn";
            case NATURAL_REGENERATION -> ruleName = "naturalRegeneration";
            default -> throw new IllegalArgumentException("Unexpected value: " + rule);
        }
        world.setGameRuleValue(ruleName, value.toString().toLowerCase());
    }

}

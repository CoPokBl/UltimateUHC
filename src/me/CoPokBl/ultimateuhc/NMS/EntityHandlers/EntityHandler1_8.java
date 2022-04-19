package me.CoPokBl.ultimateuhc.NMS.EntityHandlers;

import me.CoPokBl.ultimateuhc.Interfaces.EntityHandler;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;

public class EntityHandler1_8 implements EntityHandler {

    public void setCanPickupArrow(Arrow arrow, boolean canPickup) {
        EntityArrow entityArrow = (EntityArrow) arrow;
        if (canPickup) {
            entityArrow.fromPlayer = 1;
        } else {
            entityArrow.fromPlayer = 0;
        }

    }

    public void setMobAi(LivingEntity entity, boolean ai) {
        EntityLiving handle = ((CraftLivingEntity) entity).getHandle();
        byte flag = (byte) (ai ? 0 : 1);
        handle.getDataWatcher().watch(15, flag);
    }

}

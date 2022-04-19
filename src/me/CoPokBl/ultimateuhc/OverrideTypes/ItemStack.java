package me.CoPokBl.ultimateuhc.OverrideTypes;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStack {
    private Material type;
    private int amount;
    private String customName;
    private boolean hideFlags;

    public Material getType() {
        return type;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public boolean isHideFlags() {
        return hideFlags;
    }

    public void setHideFlags(boolean hideFlags) {
        this.hideFlags = hideFlags;
    }

    public org.bukkit.inventory.ItemStack toBukkitItemStack() {
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(type.toBukkitMaterial(), amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());
        if (customName != null) {
            itemMeta.setDisplayName(customName);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

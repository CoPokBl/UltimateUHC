package me.CoPokBl.ultimateuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes {

    public Recipes() { }

    public void registerRecipes() {
        if (Main.plugin.getConfig().getBoolean("enableGravelToArrow")) { Bukkit.addRecipe(getArrowRecipe()); }
        if (Main.plugin.getConfig().getBoolean("enable4WoolToString")) {
            Bukkit.addRecipe(getStringRecipeWhite());
            Bukkit.addRecipe(getStringRecipeBlack());
            Bukkit.addRecipe(getStringRecipeBrown());
        }
        Bukkit.addRecipe(getHeadRecipe());
    }

    private ShapedRecipe getHeadRecipe() {

        ItemStack item = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Golden Head");
        item.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(Main.plugin, "golden_apple");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" g ", "ghg", " g ");
        recipe.setIngredient('g', Material.GOLD_INGOT);
        recipe.setIngredient('h', Material.PLAYER_HEAD);

        return recipe;
    }

    private ShapedRecipe getArrowRecipe() {
        ItemStack item = new ItemStack(Material.ARROW);
        NamespacedKey key = new NamespacedKey(Main.plugin, "arrow");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("g");
        recipe.setIngredient('g', Material.GRAVEL);
        return recipe;
    }

    private ShapedRecipe getStringRecipeWhite() {
        ItemStack item = new ItemStack(Material.STRING);
        NamespacedKey key = new NamespacedKey(Main.plugin, "stringw");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ww", "ww");
        recipe.setIngredient('w', Material.WHITE_WOOL);
        return recipe;
    }
    private ShapedRecipe getStringRecipeBlack() {
        ItemStack item = new ItemStack(Material.STRING);
        NamespacedKey key = new NamespacedKey(Main.plugin, "stringb");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ww", "ww");
        recipe.setIngredient('w', Material.BLACK_WOOL);
        return recipe;
    }
    private ShapedRecipe getStringRecipeBrown() {
        ItemStack item = new ItemStack(Material.STRING);
        NamespacedKey key = new NamespacedKey(Main.plugin, "stringbr");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ww", "ww");
        recipe.setIngredient('w', Material.BROWN_WOOL);
        return recipe;
    }
}

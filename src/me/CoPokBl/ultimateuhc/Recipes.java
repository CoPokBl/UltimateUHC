package me.CoPokBl.ultimateuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes {

    public Recipes() { }

    public void registerRecipes() {
        if (Main.plugin.getConfig().getBoolean("enableGravelToArrow")) { Bukkit.addRecipe(getArrowRecipe()); }
        if (Main.plugin.getConfig().getBoolean("enable4WoolToString")) {
            Bukkit.addRecipe(getStringRecipeWhite());
        }
        Bukkit.addRecipe(getHeadRecipe());
    }

    private ShapedRecipe getHeadRecipe() {

        ItemStack item = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Golden Head");
        item.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(item);

        recipe.shape(" g ", "ghg", " g ");
        recipe.setIngredient('g', Material.GOLD_INGOT);
        recipe.setIngredient('h', Material.valueOf(Main.SpigotVersion <= 12 ? "SKULL" : "PLAYER_HEAD"));

        return recipe;
    }

    private ShapedRecipe getArrowRecipe() {
        ItemStack item = new ItemStack(Material.ARROW);
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("g");
        recipe.setIngredient('g', Material.GRAVEL);
        return recipe;
    }

    private ShapedRecipe getStringRecipeWhite() {
        ItemStack item = new ItemStack(Material.STRING);
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ww", "ww");
        recipe.setIngredient('w', Material.valueOf(Main.SpigotVersion <= 12 ? "WOOL" : "WHITE_WOOL"));
        return recipe;
    }

}

package me.CoPokBl.ultimateuhc;

import me.CoPokBl.ultimateuhc.Interfaces.Reward;
import me.CoPokBl.ultimateuhc.Interfaces.RewardType;
import me.CoPokBl.ultimateuhc.OverrideTypes.UhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RewardManager {
    private static HashMap<RewardType, Reward> Rewards;

    public static void LoadRewards(FileConfiguration config) {
        ConfigurationSection rewardsSection = config.getConfigurationSection("rewards");
        Rewards = new HashMap<>();
        if (rewardsSection == null) {
            Bukkit.getLogger().warning("No rewards configuration section was found. No rewards are loaded.");
            return;
        }
        List<String> validRewardTypes = Arrays.stream(RewardType.values()).map(Enum::toString).collect(Collectors.toList());
        for (String key : rewardsSection.getKeys(false)) {
            if (!validRewardTypes.contains(key)) {
                Bukkit.getLogger().warning("Invalid rewards type: " + key + ". Ignoring...");
                continue;
            }
            Reward reward = new Reward();
            reward.Commands = rewardsSection.getStringList(key).toArray(new String[0]);
            Rewards.put(RewardType.valueOf(key), reward);
        }
    }

    public static void RewardPlayer(RewardType type, UhcPlayer player) {
        if (!Rewards.containsKey(type)) {
            Bukkit.getLogger().warning("Could not reward player for " + type.toString() + ". No reward data in config.");
            return;
        }
        for (String cmd : Rewards.get(type).Commands) {
            String modifiedCmd = cmd.replace("{player}", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), modifiedCmd);
        }
    }

}

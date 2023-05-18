package me.CoPokBl.ultimateuhc.OverrideTypes;

import java.util.HashMap;
import java.util.Map;

public class Material {
    private String type;

    private static final Map<String, String> toPost1_12Items = Map.of(
            "WOOL", "WHITE_WOOL",
            "LEAVES", "OAK_LEAVES"
    );
    private static final Map<String, String> fromPost1_12Items = new HashMap<>();

    static {
        // Make from map
        for (Map.Entry<String, String> entry : toPost1_12Items.entrySet()) {
            fromPost1_12Items.put(entry.getValue(), entry.getKey());
        }
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(org.bukkit.Material type) {
        String typeName = type.name();
        if (Server.getInstance().getVersion() <= 13 && toPost1_12Items.containsKey(typeName)) {
            typeName = toPost1_12Items.get(typeName);
        }
        this.type = typeName;
    }

    public Material(org.bukkit.Material type) {
        this.setType(type);
    }

    public Material(String type) {
        this.type = type;
    }

    public boolean equals(Material material) {
        return this.type.equals(material.getType());
    }

    public boolean isLeaves() {
        return this.type.equals("LEAVES") ||
                this.type.equals("LEAVES_2") ||
                this.type.equals("OAK_LEAVES") ||
                this.type.equals("BIRCH_LEAVES") ||
                this.type.equals("SPRUCE_LEAVES") ||
                this.type.equals("JUNGLE_LEAVES") ||
                this.type.equals("ACACIA_LEAVES") ||
                this.type.equals("DARK_OAK_LEAVES") ||
                this.type.equals("AZALEA_LEAVES") ||
                this.type.equals("FLOWERING_AZALEA_LEAVES");
    }

    public org.bukkit.Material toBukkitMaterial() {
        if (Server.getInstance().getVersion() >= 13) {
            // latest version (post id flattening)
            try {
                return org.bukkit.Material.valueOf(this.type);
            } catch (IllegalArgumentException e) {
                // Item doesn't exist in current version
                return null;
            }
        } else {
            // Pre 1.13
            if (fromPost1_12Items.containsKey(this.type)) {
                return org.bukkit.Material.valueOf(fromPost1_12Items.get(this.type));
            } else {
                try {
                    return org.bukkit.Material.valueOf(this.type);
                } catch (IllegalArgumentException e) {
                    // Item doesn't exist in current version
                    return null;
                }
            }
        }
    }
}

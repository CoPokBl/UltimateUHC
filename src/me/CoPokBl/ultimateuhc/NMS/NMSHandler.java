package me.CoPokBl.ultimateuhc.NMS;

import me.CoPokBl.ultimateuhc.NMS.Versions.Version16;
import me.CoPokBl.ultimateuhc.NMS.Versions.Version17;
import me.CoPokBl.ultimateuhc.NMS.Versions.Version8;
import org.bukkit.Bukkit;

public class NMSHandler {

    private static NMSHandler instance;
    public NMSVersion nms;

    public static NMSHandler getInstance() {
        if (instance == null) {
            instance = new NMSHandler();
        }
        return instance;
    }

    public void Init(int version) {
        switch (version) {
            case 8 -> nms = new Version8();
            case 16 -> nms = new Version16();
            case 17 -> nms = new Version17();
            default -> {
                Bukkit.getLogger().severe("Version not supported!");
                Bukkit.getLogger().severe("Using closest version...");
                if (version > 17) {
                    nms = new Version17();
                    Bukkit.getLogger().severe("Using 1.17 NMS");
                } else if (version < 13) {
                    nms = new Version8();
                    Bukkit.getLogger().severe("Using 1.8 NMS");
                } else {
                    nms = new Version16();
                    Bukkit.getLogger().severe("Using 1.16 NMS");
                }
            }
        }
    }

}
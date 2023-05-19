package me.CoPokBl.ultimateuhc.NMS;

import me.CoPokBl.ultimateuhc.NMS.Versions.Version16;
import me.CoPokBl.ultimateuhc.NMS.Versions.Version17;
import me.CoPokBl.ultimateuhc.NMS.Versions.Version8;

public class NMSHandler {

    private static NMSHandler instance;
    public NMSVersion nms;

    public static NMSHandler getInstance() {
        if (instance == null) {
            instance = new NMSHandler();
        }
        return instance;
    }

    public void Init(int version) throws IllegalArgumentException {
        switch (version) {
            case 8 -> nms = new Version8();
            case 16 -> nms = new Version16();
            case 17 -> nms = new Version17();
            default -> throw new IllegalArgumentException("Version not supported");
        }
    }

}
package me.CoPokBl.ultimateuhc.NMS;

import me.CoPokBl.ultimateuhc.Interfaces.EntityHandler;
import me.CoPokBl.ultimateuhc.NMS.EntityHandlers.EntityHandler1_17;
import me.CoPokBl.ultimateuhc.NMS.EntityHandlers.EntityHandler1_8;

public class NMSHandler {

    private static NMSHandler instance;
    public EntityHandler entityHandler;

    public static NMSHandler getInstance() {
        if (instance == null) {
            instance = new NMSHandler();
        }
        return instance;
    }

    public void Init(int version) throws IllegalArgumentException {
        switch (version) {
            case 8 -> entityHandler = new EntityHandler1_8();
            case 17 -> entityHandler = new EntityHandler1_17();
            default -> throw new IllegalArgumentException("Version not supported");
        }
    }

}
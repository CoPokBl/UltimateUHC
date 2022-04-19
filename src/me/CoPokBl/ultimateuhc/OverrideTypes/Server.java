package me.CoPokBl.ultimateuhc.OverrideTypes;

import me.CoPokBl.ultimateuhc.Main;
import me.CoPokBl.ultimateuhc.Utils;

public class Server {

    private static Server instance;
    private int version;
    private Configuration config;

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
            instance.Init();
        }
        return instance;
    }

    private void Init() {
        setVersion(Utils.GetVersion());
        config = new Configuration(Main.plugin.getConfig());
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public World getWorld(String name) {
        World world = new World(name);
        if (!world.isValid()) return null;
        return world;
    }

    public Configuration getConfig() {
        return config;
    }
}

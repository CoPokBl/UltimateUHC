package me.CoPokBl.ultimateuhc.OverrideTypes;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class Configuration {

    private final Map<String, Object> values;

    public Configuration(FileConfiguration config) {
        values = config.getValues(true);
    }

    public Configuration(Map<String, Object> config) {
        values = config;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public Object get(String path) {
        return values.get(path);
    }

    public Configuration getConfigurationSection(String path) {
        return new Configuration(((ConfigurationSection) values.get(path)).getValues(true));
    }

    public String getString(String path) {
        if (values.get(path) == null) return null;
        return values.get(path).toString();
    }

    public Integer getInt(String path) {
        if (values.get(path) == null) return null;
        return Integer.parseInt(values.get(path).toString());
    }

    public Boolean getBoolean(String path) {
        if (values.get(path) == null) return null;
        return Boolean.parseBoolean(values.get(path).toString());
    }

    public Double getDouble(String path) {
        return Double.parseDouble(values.get(path).toString());
    }

    public Float getFloat(String path) {
        if (values.get(path) == null) return null;
        return Float.parseFloat(values.get(path).toString());
    }

    public Long getLong(String path) {
        if (values.get(path) == null) return null;
        return Long.parseLong(values.get(path).toString());
    }

    public Short getShort(String path) {
        if (values.get(path) == null) return null;
        return Short.parseShort(values.get(path).toString());
    }

    public Byte getByte(String path) {
        if (values.get(path) == null) return null;
        return Byte.parseByte(values.get(path).toString());
    }

    public Character getChar(String path) {
        if (values.get(path) == null) return null;
        return values.get(path).toString().charAt(0);
    }

}

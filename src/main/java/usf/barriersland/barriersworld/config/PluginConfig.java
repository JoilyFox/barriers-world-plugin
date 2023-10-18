package usf.barriersland.barriersworld.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import usf.barriersland.barriersworld.BarriersWorld;

import java.util.List;

public class PluginConfig {

    private final BarriersWorld plugin;

    public PluginConfig(BarriersWorld plugin) {
        this.plugin = plugin;
    }

    public int getBarrierSize() {
        return plugin.getPluginConfig().getInt("size");
    }

    public int getOpenDuration() {
        return plugin.getPluginConfig().getInt("open_duration");
    }

    public List<String> getWeeklyOpenings() {
        return plugin.getPluginConfig().getStringList("weekly_openings");
    }

    public List<String> getSpecificOpenings() {
        return plugin.getPluginConfig().getStringList("specific_openings");
    }

    public void setBarrierSize(int newSize) {
        FileConfiguration config = plugin.getConfig();
        config.set("size", newSize);
        plugin.saveConfig();
    }

    public void setOpenDuration(int newDuration) {
        FileConfiguration config = plugin.getConfig();
        config.set("open_duration", newDuration);
        plugin.saveConfig();
    }

    public List<String> getDateSpecificOpenings() {
        FileConfiguration config = plugin.getConfig();
        return config.getStringList("barrier.dateSpecificOpenings");
    }
}

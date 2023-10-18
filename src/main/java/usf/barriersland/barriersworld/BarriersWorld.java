package usf.barriersland.barriersworld;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import usf.barriersland.barriersworld.commands.NextOpenCommand;
import usf.barriersland.barriersworld.commands.NextCloseCommand;
import usf.barriersland.barriersworld.scheduler.BarrierScheduler;
import usf.barriersland.barriersworld.util.BarrierManager;

public final class BarriersWorld extends JavaPlugin {

    private BarrierManager barrierManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        getCommand("nextopen").setExecutor(new NextOpenCommand(barrierManager));
        getCommand("nextclose").setExecutor(new NextCloseCommand(barrierManager));

        barrierManager = new BarrierManager(this);

        barrierManager.initializeBarrier();
        barrierManager.restoreBarrierState();

        // Schedule a repeating task to check the barrier schedule every minute
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            barrierManager.checkSchedule();
        }, 0L, 1200L);  // 0L is the initial delay, 1200L is the repeat interval (1 minute in ticks)

        // Schedule the BarrierScheduler to run periodically
        new BarrierScheduler(this, barrierManager).runTaskTimer(this, 0L, 20L);  // Checking every second (20 ticks = 1 second)
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        barrierManager.disableBarrier();
    }

    public BarrierManager getBarrierManager() {
        return barrierManager;
    }

    public FileConfiguration getPluginConfig() {
        return getConfig();
    }
}

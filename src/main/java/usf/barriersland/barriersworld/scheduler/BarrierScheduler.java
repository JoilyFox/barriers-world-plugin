package usf.barriersland.barriersworld.scheduler;

import org.bukkit.scheduler.BukkitRunnable;
import usf.barriersland.barriersworld.BarriersWorld;
import usf.barriersland.barriersworld.util.BarrierManager;

public class BarrierScheduler extends BukkitRunnable {

    private final BarriersWorld plugin;
    private final BarrierManager barrierManager;

    public BarrierScheduler(BarriersWorld plugin, BarrierManager barrierManager) {
        this.plugin = plugin;
        this.barrierManager = barrierManager;
    }

    @Override
    public void run() {
        // Logic to check the schedule and manage barrier openings and closings
        barrierManager.checkSchedule();
    }
}

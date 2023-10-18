package usf.barriersland.barriersworld.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import usf.barriersland.barriersworld.BarriersWorld;
import usf.barriersland.barriersworld.config.PluginConfig;
import org.bukkit.ChatColor;

public class BarrierManager {

    private final BarriersWorld plugin;
    private final PluginConfig config;

    public BarrierManager(BarriersWorld plugin) {
        this.plugin = plugin;
        this.config = new PluginConfig(plugin);
    }

    public void initializeBarrier() {
        // Retrieve the world
        World world = Bukkit.getWorlds().get(0);  // assuming the world you want is the first one

        // Set the world border as per the configuration
        int size = config.getBarrierSize();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder center " + world.getSpawnLocation().getX() + " " + world.getSpawnLocation().getZ());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set " + size);
        // Assume 20 seconds is the time to die at the barrier with full health
        double damageAmount = (20 * 20) / size;  // damage per block per second
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder damage amount " + damageAmount);
    }

    public void disableBarrier() {
        // Reset the world border to a larger size or disable damage, as per your needs
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set 60000000");  // Set to maximum size
    }

    public String getNextOpeningTime() {
        // Assuming TimeUtil is a utility class with necessary methods for time calculations
        return TimeUtil.getNextOpeningTime(config.getWeeklyOpenings(), config.getDateSpecificOpenings());
    }

    public String getNextClosingTime() {
        // Assuming TimeUtil is a utility class with necessary methods for time calculations
        return TimeUtil.getNextClosingTime(config.getWeeklyOpenings(), config.getDateSpecificOpenings(), config.getOpenDuration());
    }

    public void checkSchedule() {
        String nextOpeningTime = getNextOpeningTime();
        String nextClosingTime = getNextClosingTime();

        // Logic to check if it's time to open or close the barrier
        if (TimeUtil.isTimeToOpen(nextOpeningTime)) {
            initializeBarrier();
            // Notify players about the barrier opening
            Bukkit.broadcastMessage(ChatColor.GREEN + "The barrier is now open until " + nextClosingTime + ".");
        } else if (TimeUtil.isTimeToClose(nextClosingTime)) {
            disableBarrier();
            // Notify players about the barrier closing
            Bukkit.broadcastMessage(ChatColor.RED + "The barrier is now closed until " + nextOpeningTime + ".");
        }

        // Logic to send reminder messages to players based on the time to next opening or closing
        sendReminderMessages(nextOpeningTime, nextClosingTime);
    }

    public void restoreBarrierState() {
        // Logic to restore the barrier state based on the saved state or the time elapsed
        // Assuming StateUtil is a utility class with necessary methods for state restoration
//        StateUtil.restoreBarrierState(plugin, this);
    }

    private void sendReminderMessages(String nextOpeningTime, String nextClosingTime) {
        long currentTime = System.currentTimeMillis();
        long timeToNextOpening = TimeUtil.getTimeDifference(currentTime, nextOpeningTime);
        long timeToNextClosing = TimeUtil.getTimeDifference(currentTime, nextClosingTime);

        // Define the reminder intervals in milliseconds
        long[] reminderIntervals = {
                2 * 60 * 60 * 1000,  // 2 hours
                1 * 60 * 60 * 1000,  // 1 hour
                30 * 60 * 1000,  // 30 minutes
                10 * 60 * 1000,  // 10 minutes
                5 * 60 * 1000,  // 5 minutes
                1 * 60 * 1000,  // 1 minute
                30 * 1000,  // 30 seconds
                10 * 1000   // 10 seconds
        };

        for (long interval : reminderIntervals) {
            if (timeToNextOpening >= interval && timeToNextOpening < interval + 1000) {  // Add a 1-second buffer to ensure the message is sent
                String timeRemaining = TimeUtil.formatTimeDifference(timeToNextOpening);
                Bukkit.broadcastMessage(ChatColor.GREEN + "Until the barrier opens, there are " + timeRemaining + " left at " + nextOpeningTime + ".");
            }
            if (timeToNextClosing >= interval && timeToNextClosing < interval + 1000) {  // Add a 1-second buffer to ensure the message is sent
                String timeRemaining = TimeUtil.formatTimeDifference(timeToNextClosing);
                Bukkit.broadcastMessage(ChatColor.RED + "Until the barrier closes, there are " + timeRemaining + " left at " + nextClosingTime + ".");
            }
        }
    }
}

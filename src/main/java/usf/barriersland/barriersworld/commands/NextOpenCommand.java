package usf.barriersland.barriersworld.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import usf.barriersland.barriersworld.util.BarrierManager;
import org.bukkit.ChatColor;

public class NextOpenCommand implements CommandExecutor {

    private final BarrierManager barrierManager;

    public NextOpenCommand(BarrierManager barrierManager) {
        this.barrierManager = barrierManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Logic to calculate and display the next opening time
        String nextOpeningTime = barrierManager.getNextOpeningTime();
        if (nextOpeningTime != null) {
            sender.sendMessage("The next barrier opening will be at " + nextOpeningTime);
        } else {
            sender.sendMessage(ChatColor.RED + "No planned barrier openings.");
        }
        return true;
    }
}

package usf.barriersland.barriersworld.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import usf.barriersland.barriersworld.util.BarrierManager;
import org.bukkit.ChatColor;

public class NextCloseCommand implements CommandExecutor {

    private final BarrierManager barrierManager;

    public NextCloseCommand(BarrierManager barrierManager) {
        this.barrierManager = barrierManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Logic to calculate and display the next closing time
        String nextClosingTime = barrierManager.getNextClosingTime();
        if (nextClosingTime != null) {
            sender.sendMessage("The barriers will close at " + nextClosingTime);
        } else {
            sender.sendMessage(ChatColor.RED + "All barriers are already closed!");
        }
        return true;
    }
}

package net.daechler.sudo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Sudo extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("sudo").setExecutor(this);
        getLogger().info(ChatColor.GREEN + getName() + " has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.RED + getName() + " has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sudo")) {
            // Make sure the sender has the right permissions
            if (!sender.hasPermission("sudo.use")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }

            // Ensure the right number of arguments are used
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /sudo <player> <command or message>");
                return true;
            }

            // Try to get the targeted player
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Could not find player: " + args[0]);
                return true;
            }

            // Execute the command or send a message as the targeted player
            StringBuilder sudoCommand = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sudoCommand.append(args[i] + " ");
            }

            // Trim the trailing space
            String finalCommand = sudoCommand.toString().trim();

            if (finalCommand.startsWith("/")) {
                Bukkit.dispatchCommand(target, finalCommand.substring(1));
            } else {
                target.chat(finalCommand);
            }
            return true;
        }
        return false;
    }
}

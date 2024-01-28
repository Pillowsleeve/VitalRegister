package me.pixelperfect.vitalregister.commands;

import me.pixelperfect.vitalregister.VitalRegister;
import me.pixelperfect.vitalregister.files.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {

    public DataManager data;
    public VitalRegister plugin;
    public RegisterCommand(VitalRegister plugin, DataManager data) {this.data = data; this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //Command: /Register [Password]
        if (label.equalsIgnoreCase("register")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You have to be a player to execute this command!");
                return true;
            }
            Player player = (Player) sender;
            if (!(plugin.dataFileIsEnabled())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&eData.yml &cis disabled!"));
                return true;
            }
            if (!(player.hasPermission("vital.register.register"))) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lNo Permission! &7You are not allowed to register!"));
                return true;
            }
            if (args.length == 0) {
                if (plugin.isRegistered(player)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&aYou're already registered on this server!"));
                    return true;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l! &7Please provide a secure &epassword&7!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lUsage: &8/register &8<&epassword&8>"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l> &7Your password gets securely scrambled and saved!"));
            }
            if (args.length > 0) {
                if (plugin.isRegistered(player)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&aYou're already registered on this server!"));
                    return true;
                }
                if (args[0].length() < 6) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l! &7Password must contain more than 6 Characters!"));
                    return true;
                } else {
                    //If all guard clauses have been passed it means that player executed /register [password]
                    data.getConfig().set("players." + player.getUniqueId() + ".is-registered", true);
                    data.getConfig().set("players." + player.getUniqueId() + ".password", args[0].hashCode());
                    data.getConfig().set("players." + player.getUniqueId() + ".is-signed-in", false);
                    data.saveConfig();
                    player.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&', plugin.prefix + "&aYou have successfully been registered!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&', "&e&l! &7Please sign in with &b/signin [password]&7!"));
                }
            }
            return true;
        }
        return false;
    }

}



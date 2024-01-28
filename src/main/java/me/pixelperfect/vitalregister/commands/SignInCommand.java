package me.pixelperfect.vitalregister.commands;

import me.pixelperfect.vitalregister.VitalRegister;
import me.pixelperfect.vitalregister.files.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SignInCommand implements CommandExecutor {

    public DataManager data;
    public VitalRegister plugin;
    public SignInCommand(VitalRegister plugin, DataManager data) {this.plugin = plugin; this.data = data;}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("signin")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You have to be a player to execute this command!");
                return true;
            }
            Player player = (Player) sender;
            if (!(plugin.dataFileIsEnabled())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&eData.yml &cis disabled!"));
                return true;
            }
            if (args.length == 0) {
                if (data.getConfig().getBoolean("players." + player.getUniqueId() + ".is-signed-in")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&aYou are already signed in!"));
                    return true;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l! &7Please provide your &epassword&7!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lUsage: &8/signin &8<&epassword&8>"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l> &7&nForgot your password&7?"));
            }
            if (args.length > 0) {
                if (data.getConfig().getBoolean("players." + player.getUniqueId() + ".is-signed-in")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&aYou are already signed in!"));
                    return true;
                }

                if (data.getConfig().get("players." + player.getUniqueId() + ".password").equals(args[0].hashCode())) {
                    data.getConfig().set("players." + player.getUniqueId() + ".is-signed-in", true);
                    data.saveConfig();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&aSigned in!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l> &7Have fun playing!"));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&cPassword is incorrect! &7Please try again."));
                    return true;
                }
            }
            return true;
        }
        return false;
    }
}

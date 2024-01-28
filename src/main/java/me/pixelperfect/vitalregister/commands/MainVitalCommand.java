package me.pixelperfect.vitalregister.commands;

import me.pixelperfect.vitalregister.VitalRegister;
import me.pixelperfect.vitalregister.files.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainVitalCommand implements CommandExecutor {

    public DataManager data;
    public VitalRegister plugin;
    public MainVitalCommand(VitalRegister plugin, DataManager data) {this.plugin = plugin; this.data = data;}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //Command: /vitalregister [file] <enable/disable>
        if (label.equalsIgnoreCase("vitalregister") || label.equalsIgnoreCase("vr") || label.equalsIgnoreCase("vregister")) {
            Player player = (Player) sender;
            if (!(player.hasPermission("vital.register.file"))) {
                player.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&', "&c&lVital&8:&c&oRegister &8> &7You don't have permission to execute this command!"));
                return true;
            }
            if (args.length == 0) {
                String registered;
                if (plugin.isRegistered(player)) {
                    registered = ChatColor.translateAlternateColorCodes('&', "&a&lYes");
                } else registered = ChatColor.translateAlternateColorCodes('&', "&c&lNo");
                String signedin;
                if (plugin.isSignedIn(player)) {
                    signedin = ChatColor.translateAlternateColorCodes('&', "&a&lYes");
                } else signedin = ChatColor.translateAlternateColorCodes('&', "&c&lNo");

                player.sendMessage("");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&lVital&8:&bRegister&8]-------------------"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Version: &f" + plugin.version));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Author: &fPillowsleeve"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Registered: &r" + registered));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Signed In: &r" + signedin));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8]-------------------------------"));
                player.sendMessage("");
                return true;
            }
            if (args[0].equalsIgnoreCase("data.yml")) {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l? &7What do you want to do with &eData.yml&7?"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&', "&cUsage:&r &8/Vitalregister &eData.yml &8<&aenable&8/&cdisable&8/&ereload&8>"));
                    if (data.getConfig().getBoolean("enabled")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7This file is currently &aenabled&7."));
                    } else {player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7This file is currently &cdisabled&7."));}
                    return true;
                }
                if (args[1].equalsIgnoreCase("enable")) {
                    //Check if data.yml is enabled
                    if (data.getConfig().getBoolean("enabled")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l! &7The file &eData.yml &7is already &aenabled&7!"));
                    } else {
                        data.getConfig().set("enabled", true);
                        data.saveConfig();
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l! &eData.yml &7has been &aenabled&7!"));
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("disable")) {
                    //Check if data.yml is disabled
                    if (!(data.getConfig().getBoolean("enabled"))) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l! &7The file &eData.yml &7is already &cdisabled&7!"));
                    } else {
                        data.getConfig().set("enabled", false);
                        data.saveConfig();
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l! &eData.yml &7has been &cdisabled&7!"));
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("reload")) {
                    data.reloadConfig();
                    data.saveConfig();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Config file &eData.yml &7reloaded &aSuccessfully&7!"));
                    return true;
                }
            }
        }

        return false;
    }
}

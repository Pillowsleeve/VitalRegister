package me.pixelperfect.vitalregister.listeners;

import me.pixelperfect.vitalregister.VitalRegister;
import me.pixelperfect.vitalregister.files.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinListener implements Listener {
    public DataManager data;
    public VitalRegister plugin;
    public FirstJoinListener(DataManager data, VitalRegister plugin) {this.data = data; this.plugin = plugin;}

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!(plugin.dataFileIsEnabled())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&eData.yml &cis disabled!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l> &7Enable this file to allow registration and signing in!"));
            return;
        }
        if (!(plugin.checkPlayerData(player))) {
            data.getConfig().set("players." + player.getUniqueId() + ".name", player.getName());
            data.getConfig().set("players." + player.getUniqueId() + ".is-registered", false);
            data.saveConfig();
            player.sendMessage("");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + "&7You have not been registered yet!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lExecute: &b/register [password] &7to register."));
            player.sendMessage("");
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', plugin.prefix + "&fWelcome back &9" + player.getName() + "&f!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lSign In: &b/signin [password] &7to sign in!"));
        }
    }
}

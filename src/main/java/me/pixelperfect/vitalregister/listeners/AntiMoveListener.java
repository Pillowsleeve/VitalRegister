package me.pixelperfect.vitalregister.listeners;

import me.pixelperfect.vitalregister.VitalRegister;
import me.pixelperfect.vitalregister.files.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AntiMoveListener implements Listener {

    public DataManager data;
    public VitalRegister plugin;
    public AntiMoveListener(VitalRegister plugin, DataManager data) {this.plugin = plugin; this.data = data;}

    @EventHandler
    public void onMoveNotSignedIn(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!(data.getConfig().getBoolean("players." + player.getUniqueId() + ".is-signed-in"))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have to sign-in first!"));
            event.setCancelled(true);
            return;
        }
        if (!(data.getConfig().getBoolean("players." + player.getUniqueId() + ".is-registered"))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have to register and sign-in first!"));
            event.setCancelled(true);
        }
    }
}

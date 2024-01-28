package me.pixelperfect.vitalregister.listeners;

import me.pixelperfect.vitalregister.files.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    public DataManager data;
    public PlayerLeaveListener(DataManager data) {this.data = data;}

    @EventHandler
    public void onPlayerLeaves(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        data.getConfig().set("players." + player.getUniqueId() + ".is-signed-in", false);
        data.saveConfig();
    }

}

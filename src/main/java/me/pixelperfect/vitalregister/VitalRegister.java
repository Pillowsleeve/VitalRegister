package me.pixelperfect.vitalregister;

import me.pixelperfect.vitalregister.commands.MainVitalCommand;
import me.pixelperfect.vitalregister.commands.RegisterCommand;
import me.pixelperfect.vitalregister.commands.SignInCommand;
import me.pixelperfect.vitalregister.files.DataManager;
import me.pixelperfect.vitalregister.listeners.AntiMoveListener;
import me.pixelperfect.vitalregister.listeners.FirstJoinListener;
import me.pixelperfect.vitalregister.listeners.PlayerLeaveListener;
import me.pixelperfect.vitalregister.tabcompleters.MainTabComplete;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class VitalRegister extends JavaPlugin {

    public DataManager data = new DataManager(this);
    public String version = "1.2-BETA";
    public String prefix = ChatColor.translateAlternateColorCodes('&', "&3&lVital&8:&bRegister &8&l> &r");

    public void enableDataFile() {
        if (data.getConfig().contains("enabled")) {
            if (data.getConfig().getBoolean("enabled")){
                this.getLogger().log(Level.INFO, "Data.yml file is enabled!");
                data.reloadConfig();
                data.saveDefaultConfig();
            } else {
                this.getLogger().log(Level.WARNING, "Data.yml file is disabled!");
            }
        } else {
            data.getConfig().set("enabled", true);
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        enableDataFile();
        data.saveConfig();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        data.saveConfig();
    }

    public void registerCommands() {
        this.getCommand("vitalregister").setExecutor(new MainVitalCommand(this, data));
        this.getCommand("vitalregister").setTabCompleter(new MainTabComplete());
        this.getCommand("register").setExecutor(new RegisterCommand(this, data));
        this.getCommand("signin").setExecutor(new SignInCommand(this, data));
    }

    public void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new FirstJoinListener(data, this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerLeaveListener(data), this);
        this.getServer().getPluginManager().registerEvents(new AntiMoveListener(this, data), this);
    }

    public boolean checkPlayerData(Player player) {
        if (data.getConfig().contains("players." + player.getUniqueId())) {return true;}
        return false;
    }
    // ^ Checks if player exists in Data.yml (Check for UUID)
    public boolean isRegistered(Player player) {
        if (data.getConfig().getBoolean("players." + player.getUniqueId() + ".is-registered")) {
            return true;
        }
        return false;
    }
    // ^ Checks if player has registered
    public boolean isSignedIn(Player player) {
        if (data.getConfig().getBoolean("players." + player.getUniqueId() + ".is-signed-in")) {return true;}
        return false;
    }
    // ^ Checks if player is signed in
    public boolean dataFileIsEnabled() {
        if (data.getConfig().getBoolean("enabled")) {
            return true;
        }
        return false;
    }
    // ^ Checks if the Data.yml file is enabled.
}

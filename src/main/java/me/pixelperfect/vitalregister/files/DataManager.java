package me.pixelperfect.vitalregister.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import me.pixelperfect.vitalregister.VitalRegister;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {

    private final VitalRegister plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;


    public DataManager(VitalRegister plugin) {this.plugin = plugin; saveDefaultConfig();}

    //Reload Config Logic.
    public void reloadConfig() {
        if (configFile == null) { //If "data.yml" doesn't exist: create it.
            configFile = new File(plugin.getDataFolder(), "data.yml");
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource("data.yml");
        if (defaultStream != null) { //If "data.yml" does exist: YAML checks errors, then reloads.
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultStream));
            dataConfig.setDefaults(defaultConfig);
        }
    }

    //Used to access the config file.
    public FileConfiguration getConfig() {
        if (dataConfig == null) {
            reloadConfig();
        }
        return dataConfig;
    }

    //Saves changed information to the config file. (Will get rid of "comments")
    public void saveConfig() {
        if (dataConfig == null || this.configFile == null) {return;}
        try {
            getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(
                    Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    //Initializes "data.yml" file. Will save comments.
    //Usually not necessary in "data" files since it is only accessed by the plugin.
    // ^ Meaning players shouldn't open this file and change info themselves.
    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "data.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("data.yml", false);
        }
    }

}

package com.github.Xavbeat03.Tariffs;

import com.Acrobot.ChestShop.Libs.Kyori.adventure.platform.bukkit.BukkitAudiences;
import com.github.Xavbeat03.Tariffs.DataManagement.ConfigManager;
import com.github.Xavbeat03.Tariffs.DataManagement.DB;
import com.github.Xavbeat03.Tariffs.DataManagement.DataManager;
import com.github.Xavbeat03.Tariffs.Objects.TariffObject;
import com.github.Xavbeat03.Tariffs.command.CommandHandler;
import com.github.Xavbeat03.Tariffs.listener.ListenerHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    private static Main instance;

    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;
    private DataManager dataManager;

    private ConfigManager configManager;

    private LinkedHashMap<UUID, TariffObject> TownMap = new LinkedHashMap<>();
    private LinkedHashMap<UUID, TariffObject> NationMap = new LinkedHashMap<>();
    private LinkedHashMap<UUID, TariffObject> PlayerMap = new LinkedHashMap<>();



    /**
     * <h1>Instance of BukkitAudiences for the plugin.</h1>
     * @category Plugin_State
     * @see BukkitAudiences
     */
    private BukkitAudiences adventure;
    /**
     * <h1>The Current instance of this plugin.</h1>
     * @category Plugin_State
     * @see Main
     */

    public static Main getInstance() {
        return instance;
    }

    public void onLoad() {
        instance = this;

        commandHandler = new CommandHandler(instance);
        listenerHandler = new ListenerHandler(instance);
        configManager = new ConfigManager(instance);

        configManager.onLoad();
        commandHandler.onLoad();
        listenerHandler.onLoad();
    }

    public void onEnable() {
        configManager.onEnable();
        commandHandler.onEnable();
        listenerHandler.onEnable();
    }

    public void onDisable() {
        commandHandler.onDisable();
        listenerHandler.onDisable();
        configManager.onDisable();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LinkedHashMap<UUID, TariffObject> getTownMap() {
        return TownMap;
    }

    public void setTownMap(LinkedHashMap<UUID, TariffObject> townMap) {
        TownMap = townMap;
    }

    public LinkedHashMap<UUID, TariffObject> getNationMap() {
        return NationMap;
    }

    public void setNationMap(LinkedHashMap<UUID, TariffObject> nationMap) {
        NationMap = nationMap;
    }

    public LinkedHashMap<UUID, TariffObject> getPlayerMap() {
        return PlayerMap;
    }

    public void setPlayerMap(LinkedHashMap<UUID, TariffObject> playerMap) {
        PlayerMap = playerMap;
    }
}

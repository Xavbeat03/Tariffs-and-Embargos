package com.github.Xavbeat03.Tariffs;

import com.Acrobot.ChestShop.Libs.Kyori.adventure.platform.bukkit.BukkitAudiences;
import com.github.Xavbeat03.Tariffs.DataManagement.ConfigManager;
import com.github.Xavbeat03.Tariffs.DataManagement.DB;
import com.github.Xavbeat03.Tariffs.DataManagement.DataManager;
import com.github.Xavbeat03.Tariffs.Objects.TariffObject;
import com.github.Xavbeat03.Tariffs.command.CommandHandler;
import com.github.Xavbeat03.Tariffs.listener.ListenerHandler;
import com.palmergames.bukkit.towny.TownyAPI;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin {
    private static Main instance;

    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;
    private DataManager dataManager;

    private ConfigManager configManager;

    private LinkedHashMap<UUID, List<TariffObject>> TownMap = new LinkedHashMap<>();
    private LinkedHashMap<UUID, List<TariffObject>> NationMap = new LinkedHashMap<>();
    private LinkedHashMap<UUID, List<TariffObject>> PlayerMap = new LinkedHashMap<>();



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

    public LinkedHashMap<UUID, List<TariffObject>> getTownMap() {
        return TownMap;
    }

    public void setTownMap(LinkedHashMap<UUID, List<TariffObject>> townMap) {
        TownMap = townMap;
    }

    public LinkedHashMap<UUID, List<TariffObject>> getNationMap() {
        return NationMap;
    }

    public void setNationMap(LinkedHashMap<UUID, List<TariffObject>> nationMap) {
        NationMap = nationMap;
    }

    public LinkedHashMap<UUID, List<TariffObject>> getPlayerMap() {
        return PlayerMap;
    }

    public void setPlayerMap(LinkedHashMap<UUID, List<TariffObject>> playerMap) {
        PlayerMap = playerMap;
    }

    public TownyAPI getTownyInstance(){
        return TownyAPI.getInstance();
    }
}

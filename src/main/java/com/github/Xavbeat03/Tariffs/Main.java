package com.github.Xavbeat03.Tariffs;

import com.Acrobot.ChestShop.Libs.Kyori.adventure.platform.bukkit.BukkitAudiences;
import com.github.Xavbeat03.Tariffs.DataManagement.DB;
import com.github.Xavbeat03.Tariffs.DataManagement.DataManager;
import com.github.Xavbeat03.Tariffs.command.CommandHandler;
import com.github.Xavbeat03.Tariffs.listener.ListenerHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;
    private DataManager dataManager;

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

        commandHandler.onLoad();
        listenerHandler.onLoad();
    }

    public void onEnable() {
        commandHandler.onEnable();
        listenerHandler.onEnable();
    }

    public void onDisable() {
        commandHandler.onDisable();
        listenerHandler.onDisable();
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}

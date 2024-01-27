package com.github.Xavbeat03.Tariffs.command;

import com.github.Xavbeat03.Tariffs.Main;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;

/**
 * A class to handle registration of commands.
 */
public class CommandHandler {
    private final Main instance;

    public CommandHandler(Main instance) {
        this.instance = instance;
    }

    public void onLoad() {
    }

    public void onEnable() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(instance).shouldHookPaperReload(true).silentLogs(true));
        CommandAPI.onEnable();

        // Register commands here
        new Tariff();
    }

    public void onDisable() {
        CommandAPI.onDisable();
    }
}
package com.github.Xavbeat03.Tariffs.listener;

import com.github.Xavbeat03.Tariffs.Main;

/**
 * A class to handle registration of event listeners.
 */
public class ListenerHandler {
    private final Main instance;

    public ListenerHandler(Main instance) {
        this.instance = instance;
    }

    public void onLoad() {

    }

    public void onEnable() {
        // Register listeners here
        //instance.getServer().getPluginManager().registerEvents(new PlayerJoinListener(instance), instance);

    }

    public void onDisable() {

    }
}

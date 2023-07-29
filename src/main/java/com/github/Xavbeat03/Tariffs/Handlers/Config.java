package com.github.Xavbeat03.Tariffs.Handlers;

import com.github.Xavbeat03.Tariffs.Main;

public class Config {
    public static de.leonhard.storage.Config get() {
        return Main.getInstance().getConfigManager().getConfig();
    }
}

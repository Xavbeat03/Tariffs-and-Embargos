package com.github.Xavbeat03.Tariffs.DataManagement;

import com.github.Xavbeat03.Tariffs.Main;
import com.palmergames.hikaricp.HikariDataSource;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Borrowed from DB.java on AlathraWar in the alathra repository
 */
public abstract class DB {
	public static @NotNull Connection get() throws SQLException {
		return Main.getInstance().getDataManager().getConnection();
	}

	public static @NotNull HikariDataSource getDataSource() {
		return Main.getInstance().getDataManager().getDataSource();
	}
}
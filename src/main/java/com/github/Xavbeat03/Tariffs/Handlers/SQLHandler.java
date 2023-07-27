package com.github.Xavbeat03.Tariffs.Handlers;

import com.github.Xavbeat03.Tariffs.DataManagement.DB;

import java.sql.SQLException;
import java.sql.Statement;

public class SQLHandler {
    public static void initDB(){
        try(final Statement statement = DB.get().createStatement()){
            statement.addBatch("""
                CREATE TABLE IF NOT EXISTS `Town_Tariffs` (
                `origin` uuid NOT NULL DEFAULT uuid(), 
                `target` uuid NOT NULL DEFAULT uuid(),
                `target_type` tinytext NOT NULL,
                `value` int NOT NULL
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
                """);
            statement.addBatch("""
                CREATE TABLE IF NOT EXISTS `Player_Tariffs` (
                `origin` uuid NOT NULL DEFAULT uuid(), 
                `target` uuid NOT NULL DEFAULT uuid(),
                `target_type` tinytext NOT NULL,
                `value` int NOT NULL
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
                """);
            statement.addBatch("""
                CREATE TABLE IF NOT EXISTS `Nation_Tariffs` (
                `origin` uuid NOT NULL DEFAULT uuid(), 
                `target` uuid NOT NULL DEFAULT uuid(),
                `target_type` tinytext NOT NULL,
                `value` int NOT NULL
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
                """);
            statement.executeLargeBatch();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        public static void SaveAll(){

        }
    }

}

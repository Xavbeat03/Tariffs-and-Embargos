package com.github.Xavbeat03.Tariffs.Handlers;

import com.github.Xavbeat03.Tariffs.DataManagement.DB;
import com.github.Xavbeat03.Tariffs.Main;
import com.github.Xavbeat03.Tariffs.Objects.TariffObject;
import com.github.Xavbeat03.Tariffs.command.Tariff;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.UUID;

public class SQLHandler {
    public static void initDB() {
        try (final Statement statement = DB.get().createStatement()) {
            statement.addBatch("""
                
                    CREATE TABLE IF NOT EXISTS `town_tariffs` (
                `origin` uuid NOT NULL DEFAULT uuid(), 
                `target` uuid NOT NULL DEFAULT uuid(),
                `target_type` tinytext NOT NULL,
                `value` int NOT NULL
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
                """);
            statement.addBatch(
                    """
                            CREATE TABLE IF NOT EXISTS
                                `player_tariffs`  (
                `origin` uuid NOT NULL DEFAULT uuid(), 
                `target` uuid NOT NULL DEFAULT uuid(),
                `target_type` tinytext NOT NULL,
                `value` int NOT NULL
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
                            """);
            statement.addBatch("""
                                
                                    CREATE TABLE IF NOT EXISTS
                                    `nation_tariffs` (
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
    }

    /**
     * Loads all table values into Maps
     */
    public static void LoadAll(){
            LinkedHashMap<UUID, TariffObject> TownMap = new LinkedHashMap<>();
            LinkedHashMap<UUID, TariffObject> NationMap = new LinkedHashMap<>();
            LinkedHashMap<UUID, TariffObject> PlayerMap = new LinkedHashMap<>();

            //Load Town Tariffs
            try(
                Connection con = DB.get();
                PreparedStatement getStatement = con.prepareStatement("SELECT * FROM 'town_tariffs'");
                )
            {
                ResultSet getResult = getStatement.executeQuery();

                if(getResult.equals(null)){
                    throw new SQLException("Failed to get from Table town_tariffs");
                }

                while(getResult.next()){
                    //Data
                    UUID originUuid = UUID.fromString(getResult.getString("origin"));
                    UUID targetUuid = UUID.fromString(getResult.getString("target"));
                    String targetType = getResult.getString("target_type");
                    int value = getResult.getInt("value");

                    TariffObject tariff = new TariffObject(originUuid, targetUuid, targetType, value);
                    TownMap.put(originUuid, tariff);
                }

                Main.getInstance().setTownMap(TownMap);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Load Nation Tariffs
            try(
                Connection con = DB.get();
                PreparedStatement getStatement = con.prepareStatement("SELECT * FROM 'nation_tariffs'");
            )
            {
                ResultSet getResult = getStatement.executeQuery();

                if(getResult.equals(null)){
                    throw new SQLException("Failed to get from Table nation_tariffs");
                }

                while(getResult.next()){
                    //Data
                    UUID originUuid = UUID.fromString(getResult.getString("origin"));
                    UUID targetUuid = UUID.fromString(getResult.getString("target"));
                    String targetType = getResult.getString("target_type");
                    int value = getResult.getInt("value");

                    TariffObject tariff = new TariffObject(originUuid, targetUuid, targetType, value);
                    NationMap.put(originUuid, tariff);
                }

                Main.getInstance().setNationMap(NationMap);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Load Player Tariffs
            try(
                    Connection con = DB.get();
                    PreparedStatement getStatement = con.prepareStatement("SELECT * FROM 'player_tariffs'");
            )
            {
                ResultSet getResult = getStatement.executeQuery();

                if(getResult.equals(null)){
                    throw new SQLException("Failed to get from Table player_tariffs");
                }

                while(getResult.next()){
                    //Data
                    UUID originUuid = UUID.fromString(getResult.getString("origin"));
                    UUID targetUuid = UUID.fromString(getResult.getString("target"));
                    String targetType = getResult.getString("target_type");
                    int value = getResult.getInt("value");

                    TariffObject tariff = new TariffObject(originUuid, targetUuid, targetType, value);
                    PlayerMap.put(originUuid, tariff);
                }

                Main.getInstance().setPlayerMap(PlayerMap);

            } catch (SQLException e) {
                e.printStackTrace();
            }



        }

    /**
     * Used for adjusting old values or creating new ones in the Town Tariffs Table
     */
    public static void TownCreate(Connection con, TariffObject tariff){
         try(
                 PreparedStatement sideStatement = con.prepareStatement("INSERT INTO `town_tariffs` (`origin`, `target`, `target_type`, `value` ) VALUES (?, ?, ?, ?) ")
                 ){
             sideStatement.setString(1,  tariff.getOrigin().toString());
             sideStatement.setString(2, tariff.getTarget().toString());
             sideStatement.setString(3, tariff.getTarget_type());
             sideStatement.setInt(4, tariff.getValue());


         } catch(SQLException e) {
             e.printStackTrace();
         }
    }

    /**
     * Used for adjusting old values or creating new ones in the Nation Tariffs Table
     */
    public static void NationCreate(Connection con, TariffObject tariff){

    }

    /**
     * Used for adjusting old values or creating new ones in the Player Tariffs Table
     */
    public static void PlayerCreate(Connection con, TariffObject tariff){

    }

    public static void Delete(){

    }
}




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
import java.util.List;
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
            LinkedHashMap<UUID, List<TariffObject>> TownMap = new LinkedHashMap<>();
            LinkedHashMap<UUID, List<TariffObject>> NationMap = new LinkedHashMap<>();
            LinkedHashMap<UUID, List<TariffObject>> PlayerMap = new LinkedHashMap<>();

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
                    TownMap.get(originUuid).add(tariff);
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
                   NationMap.get(originUuid).add(tariff);
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
                    PlayerMap.get(originUuid).add(tariff);
                }

                Main.getInstance().setPlayerMap(PlayerMap);

            } catch (SQLException e) {
                e.printStackTrace();
            }



        }

    /**
     * Used for adjusting old values or creating new ones in the Town Tariffs Table
     */
    public static void townCreateTariff(Connection con, TariffObject tariff){ //Should do Upsert
         try(
                 PreparedStatement tariffStatement = con.prepareStatement(""+
                     "IF NOT EXISTS (SELECT * FROM 'town_tariffs' WHERE 'origin' = ? AND 'target' = ? AND 'target_type' = ? " +
                     "  INSERT INTO `town_tariffs` (`origin`, `target`, `target_type`, `value` ) " +
                     "  VALUES (?, ?, ?, ?) " +
                     "ELSE" +
                     "  UPDATE town_tariffs" +
                     "  SET 'origin' = ?" +
                     "  'target' = ?" +
                     " 'target_type' = ?" +
                     " 'value' = ?" +
                     " WHERE 'origin' = ? AND 'target' = ? AND 'target_type' = ?"
                 )
                 ){
             //If statement
             tariffStatement.setString(1,  tariff.getOrigin().toString());
             tariffStatement.setString(2, tariff.getTarget().toString());
             tariffStatement.setString(3, tariff.getTarget_type());
             //Insert statement
             tariffStatement.setString(4,  tariff.getOrigin().toString());
             tariffStatement.setString(5, tariff.getTarget().toString());
             tariffStatement.setString(6, tariff.getTarget_type());
             tariffStatement.setInt(7, tariff.getValue());
             //Update Statement
             tariffStatement.setString(8,  tariff.getOrigin().toString());
             tariffStatement.setString(9, tariff.getTarget().toString());
             tariffStatement.setString(10, tariff.getTarget_type());
             tariffStatement.setInt(11, tariff.getValue());
             //Final Where Statement
             tariffStatement.setString(12,  tariff.getOrigin().toString());
             tariffStatement.setString(13, tariff.getTarget().toString());
             tariffStatement.setString(14, tariff.getTarget_type());

             tariffStatement.executeQuery();
         } catch(SQLException e) {
             e.printStackTrace();
         }
    }

    /**
     * Used for adjusting old values or creating new ones in the Nation Tariffs Table
     */
    public static void nationCreateTariff(Connection con, TariffObject tariff){
        try(
            PreparedStatement tariffStatement = con.prepareStatement(""+
                "IF NOT EXISTS (SELECT * FROM 'nation_tariffs' WHERE 'origin' = ? AND 'target' = ? AND 'target_type' = ? " +
                "  INSERT INTO `nation_tariffs` (`origin`, `target`, `target_type`, `value` ) " +
                "  VALUES (?, ?, ?, ?) " +
                "ELSE" +
                "  UPDATE nation_tariffs" +
                "  SET 'origin' = ?" +
                "  'target' = ?" +
                " 'target_type' = ?" +
                " 'value' = ?" +
                " WHERE 'origin' = ? AND 'target' = ? AND 'target_type' = ?"
            )
        ){
            //If statement
            tariffStatement.setString(1,  tariff.getOrigin().toString());
            tariffStatement.setString(2, tariff.getTarget().toString());
            tariffStatement.setString(3, tariff.getTarget_type());
            //Insert statement
            tariffStatement.setString(4,  tariff.getOrigin().toString());
            tariffStatement.setString(5, tariff.getTarget().toString());
            tariffStatement.setString(6, tariff.getTarget_type());
            tariffStatement.setInt(7, tariff.getValue());
            //Update Statement
            tariffStatement.setString(8,  tariff.getOrigin().toString());
            tariffStatement.setString(9, tariff.getTarget().toString());
            tariffStatement.setString(10, tariff.getTarget_type());
            tariffStatement.setInt(11, tariff.getValue());
            //Final Where Statement
            tariffStatement.setString(12,  tariff.getOrigin().toString());
            tariffStatement.setString(13, tariff.getTarget().toString());
            tariffStatement.setString(14, tariff.getTarget_type());

            tariffStatement.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used for adjusting old values or creating new ones in the Player Tariffs Table
     */
    public static void playerCreateTariff(Connection con, TariffObject tariff){
        try(
            PreparedStatement tariffStatement = con.prepareStatement(""+
                "IF NOT EXISTS (SELECT * FROM 'player_tariffs' WHERE 'origin' = ? AND 'target' = ? AND 'target_type' = ? " +
                "  INSERT INTO `player_tariffs` (`origin`, `target`, `target_type`, `value` ) " +
                "  VALUES (?, ?, ?, ?) " +
                "ELSE" +
                "  UPDATE player_tariffs" +
                "  SET 'origin' = ?" +
                "  'target' = ?" +
                " 'target_type' = ?" +
                " 'value' = ?" +
                " WHERE 'origin' = ? AND 'target' = ? AND 'target_type' = ?"
            )
        ){
            //If statement
            tariffStatement.setString(1,  tariff.getOrigin().toString());
            tariffStatement.setString(2, tariff.getTarget().toString());
            tariffStatement.setString(3, tariff.getTarget_type());
            //Insert statement
            tariffStatement.setString(4,  tariff.getOrigin().toString());
            tariffStatement.setString(5, tariff.getTarget().toString());
            tariffStatement.setString(6, tariff.getTarget_type());
            tariffStatement.setInt(7, tariff.getValue());
            //Update Statement
            tariffStatement.setString(8,  tariff.getOrigin().toString());
            tariffStatement.setString(9, tariff.getTarget().toString());
            tariffStatement.setString(10, tariff.getTarget_type());
            tariffStatement.setInt(11, tariff.getValue());
            //Final Where Statement
            tariffStatement.setString(12,  tariff.getOrigin().toString());
            tariffStatement.setString(13, tariff.getTarget().toString());
            tariffStatement.setString(14, tariff.getTarget_type());

            tariffStatement.executeQuery();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static Boolean deleteTariff(Connection con, TariffObject tariff, char origin_type){
        String tableName = switch(origin_type){
            case 'p' -> "player_tariffs";
            case 'n' -> "nation_tariffs";
            case 't' -> "town_tariffs";
            default -> throw new IllegalStateException("Unexpected value: " + origin_type);
        };
        try(
            PreparedStatement deleteStatement = con.prepareStatement(""
            + "DELETE FROM "
                + tableName
                + " WHERE 'origin' = ? AND 'target' = ? AND 'target_type' = ? "
            )
            ){
            deleteStatement.setString(1, tariff.getOrigin().toString());
            deleteStatement.setString(2, tariff.getTarget().toString());
            deleteStatement.setString(3, tariff.getTarget_type());
            deleteStatement.executeQuery();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}




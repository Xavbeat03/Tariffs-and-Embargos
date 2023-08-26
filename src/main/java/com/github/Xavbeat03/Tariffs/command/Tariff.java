package com.github.Xavbeat03.Tariffs.command;

import com.github.Xavbeat03.Tariffs.DataManagement.DB;
import com.github.Xavbeat03.Tariffs.Handlers.SQLHandler;
import com.github.Xavbeat03.Tariffs.Main;
import com.github.Xavbeat03.Tariffs.Objects.TariffObject;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.IntegerRangeArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tariff {
    final TownyAPI townyInstance = Main.getInstance().getTownyInstance();
    public Tariff() {
        new CommandAPICommand("tariff")
            .withFullDescription("This command allows your town to tariff either a player, town, or nation  with /tariff")
            .withShortDescription("This command allows your town to tariff either a player, town, or nation  with /tariff")
            .withPermission("Tariffs.tariff")
            .withArguments(
                    new StringArgument("t_entity_value" /* p, n, t*/).includeSuggestions(ArgumentSuggestions.strings("p", "n", "t")),
                    new StringArgument("t_tariffable_entity").includeSuggestions(ArgumentSuggestions.strings(String.valueOf(townyInstance.getTowns()), String.valueOf(townyInstance.getNations()))),
                    new IntegerRangeArgument("t_tariff_value").
            )
            .executes(this::tariff)
            .register();

        new CommandAPICommand("ntariff")
                .withFullDescription("This command allows your nation to tariff either a player, town, or nation with /tariff")
                .withShortDescription("This command allows your nation to tariff either a player, town, or nation with /tariff")
                .withPermission("Tariffs.ntariff")
                .withArguments(
                        new StringArgument("n_entity_value" /* p, n, t*/).includeSuggestions(ArgumentSuggestions.strings("p", "n", "t")),
                        new StringArgument("n_tariffable_entity").includeSuggestions(ArgumentSuggestions.strings(String.valueOf(townyInstance.getTowns()), String.valueOf(townyInstance.getNations()))),
                        new IntegerRangeArgument("n_tariff_value")
                )
                .executes(this::ntariff)
                .register();

        new CommandAPICommand("embargo")
            .withFullDescription("This command allows your town to embargo either a player, town, or nation with /embargo")
            .withShortDescription("This command embargos other entities")
            .withPermission("Tariffs.tariff")
            .withArguments(
                new StringArgument("t_entity_value").includeSuggestions(ArgumentSuggestions.strings("p", "n", "t")),
                new StringArgument("t_tariffable_entity").includeSuggestions(ArgumentSuggestions.strings(String.valueOf(townyInstance.getTowns()), String.valueOf(townyInstance.getNations())))
            )
            .executes(this::embargo)
            .register();

        new CommandAPICommand("nembargo")
            .withFullDescription("This command allows your nation to embargo either a player, town, or nation with /embargo")
            .withShortDescription("This command embargos other entities")
            .withPermission("Tariffs.tariff")
            .withArguments(
                new StringArgument("n_entity_value").includeSuggestions(ArgumentSuggestions.strings("p", "n", "t")),
                new StringArgument("n_tariffable_entity").includeSuggestions(ArgumentSuggestions.strings(String.valueOf(townyInstance.getTowns()), String.valueOf(townyInstance.getNations())))
            )
            .executes(this::nembargo)
            .register();
    }

    private void tariff(CommandSender sender, CommandArguments args) {

        if( sender == null ){
            return;
        }
        List<String> entity_value = new ArrayList<>() ;
        entity_value.add("p"); entity_value.add("n"); entity_value.add("t");
        String arg_entity_value = args.get("t_entity_value") != null ? args.get("t_entity_value").toString(): null;
        if (!entity_value.contains(arg_entity_value) || arg_entity_value == null ){
            //Exit Command, first arg isn't p, n, or t
            return;
        }
        String tariffable_entity = (String) args.get("t_tariffable_entity");
        if(args.count() < 3) return;
        int tariff_value = (int) args.get("t_tariff_value");

        try {
            switch (arg_entity_value) {
                case "p" -> {
                    //check that arg 1 is a player, if so add it to the town tariff database
                    townyInstance.getTownyObjectStartingWith(tariffable_entity, "r");
                    if(Bukkit.getPlayerUniqueId(tariffable_entity)==null) return; //TODO give command sender message that states player didn't exist
                    TariffObject tariff = new TariffObject(townyInstance.getTown((Player) sender).getUUID(), Bukkit.getPlayerUniqueId(tariffable_entity), arg_entity_value, tariff_value);

                    SQLHandler.townCreateTariff(DB.get(), tariff);
                }
                case "t" -> {
                    //check that arg 1 is a town, if so add it to the town tariff database
                   if ( townyInstance.getTownyObjectStartingWith(tariffable_entity, "t").isEmpty()) return; //TODO give command sender message that states town didn't exist
                    TariffObject tariff = new TariffObject(townyInstance.getTown((Player) sender).getUUID(), townyInstance.getTown(tariffable_entity).getUUID(), arg_entity_value, tariff_value);

                    SQLHandler.townCreateTariff(DB.get(), tariff);

                }
                case "n" -> {
                    //check that arg 1 is a nation, if so add it to the town tariff database
                    if (townyInstance.getTownyObjectStartingWith(tariffable_entity, "n").isEmpty()) return; //TODO give command sender message that states town didn't exist
                    TariffObject tariff = new TariffObject(townyInstance.getTown((Player) sender).getUUID(), townyInstance.getNation(tariffable_entity).getUUID(), arg_entity_value, tariff_value);

                    SQLHandler.townCreateTariff(DB.get(), tariff);

                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }



    }
    private void ntariff(CommandSender sender, CommandArguments args) {
        if( sender == null ){
            return;
        }
        List<String> entity_value = new ArrayList<>() ;
        entity_value.add("p"); entity_value.add("n"); entity_value.add("t");
        String arg_entity_value = args.get("t_entity_value") != null ? args.get("t_entity_value").toString(): null;
        if (!entity_value.contains(arg_entity_value) || arg_entity_value == null ){
            //Exit Command, first arg isn't p, n, or t
            return;
        }
        String tariffable_entity = (String) args.get("t_tariffable_entity");
        if(args.count() < 3) return;
        int tariff_value = (int) args.get("t_tariff_value");

        try {
            switch (arg_entity_value) {
                case "p" -> {
                    //check that arg 1 is a player, if so add it to the town tariff database
                    townyInstance.getTownyObjectStartingWith(tariffable_entity, "r");
                    if(Bukkit.getPlayerUniqueId(tariffable_entity)==null) return; //TODO give command sender message that states player didn't exist
                    TariffObject tariff = new TariffObject(townyInstance.getTown((Player) sender).getUUID(), Bukkit.getPlayerUniqueId(tariffable_entity), arg_entity_value, tariff_value);

                    SQLHandler.nationCreateTariff(DB.get(), tariff);
                }
                case "t" -> {
                    //check that arg 1 is a town, if so add it to the town tariff database
                    if ( townyInstance.getTownyObjectStartingWith(tariffable_entity, "t").isEmpty()) return; //TODO give command sender message that states town didn't exist
                    TariffObject tariff = new TariffObject(townyInstance.getTown((Player) sender).getUUID(), townyInstance.getTown(tariffable_entity).getUUID(), arg_entity_value, tariff_value);

                    SQLHandler.nationCreateTariff(DB.get(), tariff);

                }
                case "n" -> {
                    //check that arg 1 is a nation, if so add it to the town tariff database
                    if (townyInstance.getTownyObjectStartingWith(tariffable_entity, "n").isEmpty()) return; //TODO give command sender message that states town didn't exist
                    TariffObject tariff = new TariffObject(townyInstance.getTown((Player) sender).getUUID(), townyInstance.getNation(tariffable_entity).getUUID(), arg_entity_value, tariff_value);

                    SQLHandler.nationCreateTariff(DB.get(), tariff);

                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }


    }

    private void embargo(CommandSender sender, CommandArguments commandArguments){
        Object[] argarray = new Object[commandArguments.count() + 1];
        argarray[argarray.length-1] = -1;
        CommandArguments passedArgs = new CommandArguments()
    }

    private void nembargo(CommandSender sender, CommandArguments commandArguments){
        Object[] argarray = new Object[commandArguments.count() + 1];
        argarray[argarray.length-1] = -1;
        CommandArguments passedArgs = new CommandArguments()
    }
}

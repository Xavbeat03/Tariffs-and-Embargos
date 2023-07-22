package com.github.Xavbeat03.Tariffs.command;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.IntegerRangeArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Tariff {
    public Tariff() {
        new CommandAPICommand("tariff")
            .withFullDescription("This command allows your town to tariff either a player, town, or nation  with /tariff")
            .withShortDescription("This command allows your town to tariff either a player, town, or nation  with /tariff")
            .withPermission("Tariffs.tariff")
            .withArguments(
                    new StringArgument("t_entity_value" /* p, n, t*/).includeSuggestions(ArgumentSuggestions.strings("p", "n", "t")),
                    new StringArgument("t_tariffable_entity"),
                    new IntegerRangeArgument("t_tariff_value")
            )
            .executes(this::tariff)
            .register();
        new CommandAPICommand("ntariff")
                .withFullDescription("This command allows your nation to tariff either a player, town, or nation with /tariff")
                .withShortDescription("This command allows your nation to tariff either a player, town, or nation with /tariff")
                .withPermission("Tariffs.ntariff")
                .withArguments(
                        new StringArgument("n_entity_value" /* p, n, t*/).includeSuggestions(ArgumentSuggestions.strings("p", "n", "t")),
                        new StringArgument("n_tariffable_entity"),
                        new IntegerRangeArgument("n_tariff_value")
                )
                .executes(this::ntariff)
                .register();
    }

    private void tariff(CommandSender sender, CommandArguments args) {
        List<String> entity_value = new ArrayList<>() ;
        entity_value.add("p"); entity_value.add("n"); entity_value.add("t");
        String arg_entity_value = args.get("t_entity_value") != null ? args.get("t_entity_value").toString(): null;
        if (!entity_value.contains(arg_entity_value)){
            //Exit Command, first arg isn't p, n, or t
            return;
        }


    }
    private void ntariff(CommandSender sender, CommandArguments args) {


    }
}

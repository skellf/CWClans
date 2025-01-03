package me.skellf.cwclans.clans.commands.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ClansTabCompleter implements TabCompleter {

    private final List<String> subCommands;

    public ClansTabCompleter() {
        subCommands = new ArrayList<>();
        subCommands.add("create");
        subCommands.add("remove");
        subCommands.add("reload");
        subCommands.add("menu");
        subCommands.add("info");
        subCommands.add("leave");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 1) {
            return getMatchingSubCommands(args[0]);
        }

        return null;
    }

    private List<String> getMatchingSubCommands(String input) {
        List<String> matches = new ArrayList<>();
        for (String subCommand : subCommands) {
            if (subCommand.toLowerCase().startsWith(input.toLowerCase())) {
                matches.add(subCommand);
            }
        }
        return matches;
    }
}

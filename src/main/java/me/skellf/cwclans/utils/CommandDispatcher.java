package me.skellf.cwclans.utils;

import me.skellf.cwclans.CWClans;
import me.skellf.cwclans.clans.commands.CWClansCommand;
import me.skellf.cwclans.clans.commands.subcommands.clans.ClanCreateSubCommand;
import me.skellf.cwclans.clans.commands.subcommands.clans.ReloadSubCommand;
import me.skellf.cwclans.utils.config.MessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher implements CommandExecutor {

    private final Map<String, CWClansCommand> subCommands = new HashMap<>();
    private final MessageConfig mc;

    public CommandDispatcher() {
        this.mc = CWClans.getInstance().getMessageConfig();

        subCommands.put("create", new ClanCreateSubCommand());
        subCommands.put("reload", new ReloadSubCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(mc.getMessage("usage"));
        }

        CWClansCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            return subCommand.execute(sender, command, s, args);
        } else  {
            sender.sendMessage(mc.getMessage("usage"));
            return true;
        }
    }
}

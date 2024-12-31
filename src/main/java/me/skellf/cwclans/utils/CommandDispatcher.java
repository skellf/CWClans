package me.skellf.cwclans.utils;

import me.skellf.cwclans.CWClans;
import me.skellf.cwclans.commands.ClanCommand;
import me.skellf.cwclans.commands.subcommands.ClanCreate;
import me.skellf.cwclans.utils.config.MessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher implements CommandExecutor {

    private final Map<String, ClanCommand> subCommands = new HashMap<>();
    private final MessageConfig mc;

    public CommandDispatcher() {
        this.mc = CWClans.getInstance().getMessageConfig();

        subCommands.put("create", new ClanCreate());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(mc.getMessage("usage"));
        }

        ClanCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            return subCommand.execute(sender, command, s, args);
        } else  {
            sender.sendMessage(mc.getMessage("usage"));
            return true;
        }
    }
}

package me.skellf.cwclans.clans.commands.subcommands.clans;

import me.skellf.cwclans.clans.commands.CWClansCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadSubCommand extends CWClansCommand {

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("cwclans.reload")) {
            sender.sendMessage(mc.getMessage("no-permission"));
        }

        db.disconnect();
        db.setupDB();

        plugin.reloadMessageConfig();

        sender.sendMessage(mc.getMessage("reloaded"));

        return true;
    }
}

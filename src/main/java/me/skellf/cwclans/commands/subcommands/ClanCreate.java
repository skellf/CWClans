package me.skellf.cwclans.commands.subcommands;

import me.skellf.cwclans.commands.ClanCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClanCreate extends ClanCommand {

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(mc.getMessage("only-players"));
            return true;
        }

        if (player.hasPermission("cwclans.createclan")) {
            player.sendMessage(mc.getMessage("no-permission"));
            return true;
        }

        if (args.length != 2) {
            player.sendMessage(mc.getMessage("create-clan.usage"));
            return true;
        }

        String name = args[1];
        cm.createClan(player, name);

        return true;
    }
}

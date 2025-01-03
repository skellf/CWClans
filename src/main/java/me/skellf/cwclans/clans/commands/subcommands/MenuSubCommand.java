package me.skellf.cwclans.clans.commands.subcommands;

import me.skellf.cwclans.clans.commands.CWClansCommand;
import me.skellf.cwclans.clans.menu.GeneralMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuSubCommand extends CWClansCommand {

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(mc.getMessage("only-players"));
            return true;
        }

        if (!player.hasPermission("cwclans.menu")) {
            player.sendMessage(mc.getMessage("no-permission"));
            return true;
        }

        new GeneralMenu().displayTo(player);

        return false;
    }
}

package me.skellf.cwclans.clans.commands.subcommands.clans;

import com.Zrips.CMI.Containers.CMIUser;
import me.skellf.cwclans.clans.commands.CWClansCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClanCreateSubCommand extends CWClansCommand {

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

        CMIUser user = CMIUser.getUser(player);

        if (user.hasMoney(100000.0)) {
            user.withdraw(100000.0);
            cm.createClan(player, name);
        } else {
            player.sendMessage(mc.getMessage("clan.create.no-money"));
        }

        return true;
    }
}

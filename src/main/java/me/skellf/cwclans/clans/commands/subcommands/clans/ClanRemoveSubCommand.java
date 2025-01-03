package me.skellf.cwclans.clans.commands.subcommands.clans;

import me.skellf.cwclans.clans.Clan;
import me.skellf.cwclans.clans.commands.CWClansCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClanRemoveSubCommand extends CWClansCommand {

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(mc.getMessage("only-players"));
            return true;
        }

        if (!sender.hasPermission("cwclans.remove.own")) {
            sender.sendMessage(mc.getMessage("no-permission"));
            return true;
        }

        String clanName = args[1];

        try {
            Clan clan = cm.getClan(clanName);

            if (clan == null) {
                sender.sendMessage(mc.getMessage("clan.remove.not-found"));
                return true;
            }

            if (sender.hasPermission("cwclans.remove") ||
                    (clan.getLeader().equals(player.getName()) && sender.hasPermission("cwclans.remove.own"))) {

                try (PreparedStatement stmt = db.getConnection().prepareStatement(
                        "DELETE FROM clans WHERE name = ?;"
                )) {
                    stmt.setString(1, clanName);
                    stmt.executeUpdate();
                    sender.sendMessage(mc.getMessage("clan.removed"));
                    return true;
                }
            } else {
                sender.sendMessage(mc.getMessage("no-permission"));
                return true;
            }

        } catch (SQLException e) {
            sender.sendMessage(mc.getMessage("error"));
            e.printStackTrace();
            return true;
        }
    }

}

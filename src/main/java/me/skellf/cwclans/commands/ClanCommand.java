package me.skellf.cwclans.commands;

import me.skellf.cwclans.CWClans;
import me.skellf.cwclans.db.DBManager;
import me.skellf.cwclans.utils.ClanManager;
import me.skellf.cwclans.utils.config.MessageConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class ClanCommand {

    protected final CWClans plugin = CWClans.getInstance();
    protected final MiniMessage mm = MiniMessage.miniMessage();
    protected final DBManager db = CWClans.getInstance().getDbManager();
    protected final MessageConfig mc = CWClans.getInstance().getMessageConfig();
    protected final ClanManager cm = CWClans.getInstance().getClanManager();

    public abstract boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

}

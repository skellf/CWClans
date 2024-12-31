package me.skellf.cwclans.utils;

import me.skellf.cwclans.CWClans;
import me.skellf.cwclans.db.DBManager;
import me.skellf.cwclans.utils.config.MessageConfig;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClanManager {

    private final MessageConfig mc;
    private final DBManager db;

    public ClanManager(MessageConfig mc, DBManager db) {

        this.mc = mc;
        this.db = db;

    }

    public void createClan(Player player, String name) {
        try (PreparedStatement statement = db.getConnection().prepareStatement(
                "INSERT INTO clans (name, leader, members) VALUES (?, ?, ?);"
        )) {
            statement.setString(1, name);
            statement.setString(2, player.getName());
            statement.setString(3, player.getName());

            statement.executeUpdate();
            player.sendMessage(mc.getMessage("clan-created"));
        } catch (SQLException e) {
            player.sendMessage(mc.getMessage("error"));
            e.printStackTrace();
        }
    }

}

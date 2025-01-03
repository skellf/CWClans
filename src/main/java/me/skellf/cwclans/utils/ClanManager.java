package me.skellf.cwclans.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.skellf.cwclans.clans.Clan;
import me.skellf.cwclans.db.DBManager;
import me.skellf.cwclans.utils.config.MessageConfig;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClanManager {

    private final MessageConfig mc;
    private final DBManager db;

    public ClanManager(MessageConfig mc, DBManager db) {
        this.mc = mc;
        this.db = db;
    }

    public void createClan(Player player, String name) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "INSERT INTO clans (name, leader, members, coins, lore, rating) VALUES (?, ?, ?, ?, ?, ?);"
        )) {
            stmt.setString(1, name);
            stmt.setString(2, player.getName());
            stmt.setString(3, player.getName());
            stmt.setString(4, String.valueOf(0));
            stmt.setString(5, "[]");
            stmt.setString(6, String.valueOf(0));

            stmt.executeUpdate();
            player.sendMessage(mc.getMessage("clan-created"));
        } catch (SQLException e) {
            player.sendMessage(mc.getMessage("error"));
            e.printStackTrace();
        }
    }

    public Clan getClan(String name) throws SQLException {
        String sql = "SELECT * FROM clans WHERE name = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setString(1, name);
            var result = stmt.executeQuery();

            if (result.next()) {
                return new Clan(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("leader"),
                        result.getString("members"),
                        result.getInt("coins"),
                        result.getString("lore"),
                        result.getInt("rating")
                );
            }
        }
        return null;
    }

    public void addCoins(String name, int amount) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "UPDATE clans SET coins = coins + ? WHERE name = ?;"
        )) {
            stmt.setInt(1, amount);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Clan getPlayerClan(String playerName) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "SELECT * FROM clans WHERE members LIKE ?"
        )) {
            stmt.setString(1, "%" + playerName + "%");
            var result = stmt.executeQuery();

            if (result.next()) {
                return new Clan(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("leader"),
                        result.getString("members"),
                        result.getInt("coins"),
                        result.getString("lore"),
                        result.getInt("rating")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Clan> getAllClans() {
        List<Clan> clans = new ArrayList<>();

        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "SELECT * FROM clans"
        )) {
            var result = stmt.executeQuery();

            while (result.next()) {
                clans.add(new Clan(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("leader"),
                        result.getString("members"),
                        result.getInt("coins"),
                        result.getString("lore"),
                        result.getInt("rating")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clans;
    }

    public void updateClanLore(String name, List<String> lore) throws SQLException {
        String json = new Gson().toJson(lore);
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "UPDATE clans SET lore = ? WHERE name = ?"
        )) {
            stmt.setString(1, json);
            stmt.setString(2, name);
            stmt.executeUpdate();
        }
    }

    public List<String> getCLanLore(String name) throws SQLException {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "SELECT lore FROM clans WHERE name = ?"
        )) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String json = rs.getString("lore");
                Type listType = new TypeToken<List<String>>(){}.getType();
                return new Gson().fromJson(json, listType);
            }
        }
        return new ArrayList<>();
    }

}

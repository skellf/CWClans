package me.skellf.cwclans;

import me.skellf.cwclans.db.DBManager;
import me.skellf.cwclans.utils.ClanManager;
import me.skellf.cwclans.utils.CommandDispatcher;
import me.skellf.cwclans.utils.config.MessageConfig;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.sql.SQLException;
import java.util.logging.Logger;

public final class CWClans extends SimplePlugin {

    private static final Logger log = Logger.getLogger("CWClans");
    private DBManager dbManager;
    private MessageConfig messageConfig;
    private ClanManager clanManager;

    @Override
    public void onPluginStart() {
        // Plugin startup logic

        this.saveDefaultConfig();
        this.saveConfig();

        dbManager = new DBManager(log);
        messageConfig = new MessageConfig(getConfig(), messageConfig.loadMessages());
        clanManager = new ClanManager(messageConfig, dbManager);

        try {
            dbManager.connect();
            log.info("DB Successfully loaded!");
            dbManager.setupDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.getCommand("clans").setExecutor(new CommandDispatcher());
    }

    @Override
    public void onPluginStop() {
        dbManager.disconnect();
    }

    public static CWClans getInstance() {
        return (CWClans) SimplePlugin.getInstance();
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public ClanManager getClanManager() {
        return clanManager;
    }
}

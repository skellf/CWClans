package me.skellf.cwclans;

import me.skellf.cwclans.clans.commands.tabcomplete.ClansTabCompleter;
import me.skellf.cwclans.clans.listener.DeathListener;
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

        log.info("""
                
                 ██████ ██     ██  ██████ ██       █████  ███    ██ ███████\s
                ██      ██     ██ ██      ██      ██   ██ ████   ██ ██     \s
                ██      ██  █  ██ ██      ██      ███████ ██ ██  ██ ███████\s
                ██      ██ ███ ██ ██      ██      ██   ██ ██  ██ ██      ██\s
                 ██████  ███ ███   ██████ ███████ ██   ██ ██   ████ ███████\s
                                        developer: skellf, version: 1.0    \s
                """);

        this.saveDefaultConfig();
        this.saveConfig();

        log.info("Loading DB...");
        dbManager = new DBManager(log);

        try {
            dbManager.connect();
            log.info("DB Successfully loaded!");
            dbManager.setupDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("Loading messages...");
        messageConfig = new MessageConfig(getConfig(), messageConfig.loadMessages());
        log.info("Successfully loaded messages!");

        clanManager = new ClanManager(messageConfig, dbManager);

        //Listeners
        this.getServer().getPluginManager().registerEvents(new DeathListener(clanManager), this);

        // Commands
        this.getCommand("clans").setExecutor(new CommandDispatcher());
        this.getCommand("clans").setTabCompleter(new ClansTabCompleter());
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

    public void reloadMessageConfig() {
        messageConfig = new MessageConfig(getConfig(), messageConfig.loadMessages());
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public ClanManager getClanManager() {
        return clanManager;
    }
}

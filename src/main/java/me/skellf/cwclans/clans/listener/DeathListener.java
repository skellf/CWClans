package me.skellf.cwclans.clans.listener;

import me.skellf.cwclans.clans.Clan;
import me.skellf.cwclans.utils.ClanManager;
import me.skellf.cwclans.utils.config.MessageConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final ClanManager cm;
    private final MessageConfig mc;

    public DeathListener(ClanManager clanManager, MessageConfig messageConfig) {
        this.cm = clanManager;
        this.mc = messageConfig;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = player.getKiller();

        Clan playerClan = cm.getPlayerClan(player.getName());
        if (playerClan != null) {
            cm.addCoins(playerClan.getName(), -1);
            player.sendMessage(mc.getMessage("clan.death"));
        }

        if (killer != null) {
            Clan killerClan = cm.getPlayerClan(killer.getName());
            if (killerClan != null) {
                cm.addCoins(killerClan.getName(), 1);
                player.sendMessage("clan.death");
            }
        }
    }

}

package me.skellf.cwclans.clans.listener;

import me.skellf.cwclans.clans.Clan;
import me.skellf.cwclans.utils.ClanManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final ClanManager cm;

    public DeathListener(ClanManager clanManager) {
        this.cm = clanManager;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = player.getKiller();

        Clan playerClan = cm.getPlayerClan(player.getName());
        if (playerClan != null) {
            cm.addCoins(playerClan.getName(), -1);
        }

        if (killer != null) {
            Clan killerClan = cm.getPlayerClan(killer.getName());
            if (killerClan != null) {
                cm.addCoins(killerClan.getName(), 1);
            }
        }
    }

}

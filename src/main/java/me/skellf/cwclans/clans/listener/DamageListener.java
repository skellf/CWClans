package me.skellf.cwclans.clans.listener;

import me.skellf.cwclans.utils.ClanManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final ClanManager cm;

    public DamageListener(ClanManager cm) {
        this.cm = cm;
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player defender) || !(event.getDamager() instanceof Player damager)) return;

        if (cm.getPlayerClan(defender.getName()).equals(cm.getPlayerClan(damager.getName()))) {
            event.setCancelled(true);
        }
    }

}

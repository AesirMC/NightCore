package org.night.nightcore.stats.statmats;

import org.bukkit.entity.Player;
import org.night.nightcore.Main;
import org.night.nightcore.stats.statdatabase.PvPDatabase;
import org.night.nightcore.stats.statmanager.PlayerAccount;

public class StrikeMath {
    private Main plugin;
    public StrikeMath(Main plugin){
        this.plugin = plugin;
    }
    public void StrikeAbuse(Player player, Player killer) {
        if (Main.getInstance().getConfig().getInt("AntiAbuse") == 1) {
            if (!killer.hasPermission("night.abuse.kill") && !player.hasPermission("night.abuse.dead"))
                updateStrike(player, killer);
        } else {
            updateStrike(player, killer);
        }
    }

    public void updateStrike(Player player, Player killer) {
        PlayerAccount playerDatabaseMap = Main.getInstance().getPvPDatabaseMap().get(player.getUniqueId());
        PlayerAccount killerDatabaseMap = Main.getInstance().getPvPDatabaseMap().get(killer.getUniqueId());
        playerDatabaseMap.setPlayerStreak(0);
        killerDatabaseMap.setPlayerStreak(killerDatabaseMap.getPlayerStreak() + 1);
    }
}
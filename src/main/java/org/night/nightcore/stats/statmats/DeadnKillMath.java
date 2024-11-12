package org.night.nightcore.stats.statmats;

import org.bukkit.entity.Player;
import org.night.nightcore.Main;
import org.night.nightcore.minimessage.Minimessage;
import org.night.nightcore.stats.statmanager.PlayerAccount;

public class DeadnKillMath {

    private final Main plugin;
    public DeadnKillMath(Main plugin) {this.plugin = plugin;}

    public void AntiAbuseDead(Player player, Player killer) {
        if (Main.getInstance().getConfig().getInt("AntiAbuse") == 1) {
            if (!killer.hasPermission("night.abuse.kill") && !player.hasPermission("night.abuse.dead"))
                updateKillnDead(player, killer);
        } else {
            updateKillnDead(player, killer);
        }
    }

    public void updateKillnDead(Player player, Player killer) {
        PlayerAccount playerDatabase = Main.getInstance().getPvPDatabaseMap().get(player.getUniqueId());
        PlayerAccount killerDatabase = Main.getInstance().getPvPDatabaseMap().get(killer.getUniqueId());
        playerDatabase.setPlayerDeath(playerDatabase.getPlayerDeath() + 1);
        killerDatabase.setPlayerKill(killerDatabase.getPlayerKill() + 1);
    }

    public String statnameCH(String string, String string2, int value) {
        String s = string2;
        String dcommand = string.replace("stat", s);
        String d2command = dcommand.replace("value", "" + value);
        return d2command;
    }
}

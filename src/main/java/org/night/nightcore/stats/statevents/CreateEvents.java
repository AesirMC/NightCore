package org.night.nightcore.stats.statevents;

import java.sql.SQLException;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.night.nightcore.Main;
import org.night.nightcore.minimessage.Minimessage;
import org.night.nightcore.stats.statdatabase.PvPDatabase;
import org.night.nightcore.stats.statmanager.PlayerAccount;

public class CreateEvents implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        if (!Main.getInstance().getPvPDatabase().playerAccountTrue(player.getUniqueId().toString()))
            try {
                Main.getInstance().getPvPDatabase().createPlayer(uuid, player.getName(), 0, 0, 1500, 0);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        if (Main.getInstance().getPvPDatabaseMap().get(player.getUniqueId()) == null) {
            PlayerAccount playerAccount = new PlayerAccount(uuid, player.getName(), 0, 0, 1500, 0);
            Main.getInstance().getPvPDatabaseMap().put(player.getUniqueId(), playerAccount);
        }
    }
}

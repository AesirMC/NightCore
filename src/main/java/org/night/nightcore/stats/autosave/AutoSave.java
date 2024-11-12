package org.night.nightcore.stats.autosave;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.night.nightcore.Main;
import org.night.nightcore.stats.placeholder.PlaceHolderLists;
import org.night.nightcore.stats.statdatabase.PvPDatabase;

public class AutoSave {
    private Main plugin;
    public AutoSave(Main plugin) {this.plugin = plugin;}

    public void SaveTask() {
        BukkitTask task = (new BukkitRunnable() {
            public void run() {
                try {
                    Main.getInstance().getPvPDatabase().putAllAccounts();
                    Bukkit.getConsoleSender().sendMessage("Database yüklendi.");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }).runTaskTimer(Main.getInstance(), 500L, Main.getInstance().getConfigManager().getAutoSaveTick());
        Main.getInstance().getTimetask().put(Bukkit.getServer(), task);
    }

    public void reset() {
        try {Main.getInstance().getPvPDatabase().deleteAll();} catch (SQLException e) {throw new RuntimeException(e);}
        if(Main.getInstance().getConfigManager().getListCommandsActive() == 1) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            for (int i = 0; i < Main.getInstance().getConfigManager().getListCommands().size(); i++) {
                for (int j = 0; j < 4; j++) {
                    Main.getInstance().getPlaceHolderLists().killist(0, j);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(Main.getInstance().getPapiMap().get(0).getUuid()));
                    Bukkit.dispatchCommand(console, player_nameCh(Main.getInstance().getConfigManager().getListCommands().get(i), offlinePlayer));
                }
            }
        }
    }
    public String player_nameCh(String string, OfflinePlayer player) {
        String player_name = player.getName();
        assert player_name != null;
        String dcommand = string.replace("player_name", player_name);
        return dcommand;
    }
}

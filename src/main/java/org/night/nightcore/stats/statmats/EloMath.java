package org.night.nightcore.stats.statmats;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.night.nightcore.Main;
import org.night.nightcore.minimessage.Minimessage;
import org.night.nightcore.stats.statdatabase.PvPDatabase;
import org.night.nightcore.stats.statmanager.PlayerAccount;

import java.util.Objects;

public class EloMath {

    private Main plugin;

    public EloMath(Main plugin) {
        this.plugin = plugin;
    }
    public void AntiAbuse(Player player, Player killer) {
        int abusesecond = Main.getInstance().getConfig().getInt("AntiAbuseSecond");
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = "lp user " + killer.getName() + " permission settemp night.abuse.kill true " + abusesecond + "s";
        String command2 = "lp user " + player.getName() + " permission settemp night.abuse.dead true " + abusesecond + "s";
        if (Main.getInstance().getConfig().getInt("AntiAbuse") == 1) {
            if (!killer.hasPermission("night.abuse.kill") && !player.hasPermission("night.abuse.dead")) {
                updateElo(player, killer);
                Bukkit.dispatchCommand(console, command);
                Bukkit.dispatchCommand(console, command2);
            }
        } else {
            updateElo(player, killer);
        }
    }

    public void updateElo(Player player, Player killer) {
        Minimessage minimessage = Main.getInstance().getMinimessage();
        PlayerAccount databasemap = Main.getInstance().getPvPDatabaseMap().get(player.getUniqueId());
        PlayerAccount killermap = Main.getInstance().getPvPDatabaseMap().get(killer.getUniqueId());
        String cantUpdate = minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("cantUpdateElo"));
        int def = 18;
        double yuzdeMath = (double) databasemap.getPlayerElo() / killermap.getPlayerElo();
        if (databasemap.getPlayerElo() - def * yuzdeMath > 0.0D) {
            databasemap.setPlayerElo((int)Math.round(databasemap.getPlayerElo() - def * yuzdeMath));
            killermap.setPlayerElo((int)Math.round(killermap.getPlayerElo() + 3.0D + def * yuzdeMath));
            var deadnKillMath = Main.getInstance().getDeadnKillMath();
            killer.sendMessage(minimessage.MessageCheange(deadnKillMath.statnameCH(Objects.requireNonNull(Main.getLang().getString("CheanceMessageKill")), "elo", (int)Math.round(3.0D + def * yuzdeMath)) + killermap.getPlayerElo()));
            player.sendMessage(minimessage.MessageCheange(deadnKillMath.statnameCH(Objects.requireNonNull(Main.getLang().getString("CheanceMessageDeath")), "elo", (int)Math.round(def * yuzdeMath))) + databasemap.getPlayerElo());
        } else {
            player.sendMessage(cantUpdate);
        }
    }
}

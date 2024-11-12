package org.night.nightcore.stats.statmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.night.nightcore.Main;
import org.night.nightcore.minimessage.Minimessage;
import org.night.nightcore.stats.statevents.CreateEvents;

public class Commandelo implements CommandExecutor, TabCompleter {
    public boolean onCommand(CommandSender sender, Command c, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length == 0) {
                eloschem(player,player);
                return true;
            }
            if (args[0].equalsIgnoreCase("elo")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                eloschem(player, target);
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> c = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("elo") &&
                args.length == 1)
            c.addAll(Bukkit.getOnlinePlayers().stream()
                    .map(OfflinePlayer::getName)
                    .collect(Collectors.toList()));
        return c;
    }

    public void eloschem(Player player, OfflinePlayer oplayer) {
        PlayerAccount playerAccount = Main.getInstance().getPvPDatabaseMap().get(oplayer.getUniqueId());
        var minimessage = Main.getInstance().getMinimessage();
        player.sendMessage(minimessage.MessageCheange(Main.getLang().getString("Listtop")));
        ChatColor var10001 = ChatColor.BLUE;
        player.sendMessage(var10001 + "Öldürme: " + playerAccount.getPlayerKill());
        player.sendMessage(var10001 + "Ölüm: " + playerAccount.getPlayerDeath());
        player.sendMessage(var10001 + "Streak: " + playerAccount.getPlayerStreak());
        player.sendMessage(var10001 + "Elo: " + playerAccount.getPlayerElo());
        player.sendMessage(minimessage.MessageCheange(Main.getLang().getString("Listtop")));
    }
}

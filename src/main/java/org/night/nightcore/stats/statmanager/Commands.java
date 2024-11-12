package org.night.nightcore.stats.statmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.night.nightcore.Main;
import org.night.nightcore.minimessage.Minimessage;
import org.night.nightcore.stats.placeholder.PlaceHolderLists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Commands implements CommandExecutor, TabCompleter {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, String[] args) {
        if (sender instanceof Player) {
            Minimessage minimessage = Main.getInstance().getMinimessage();
            Player player = (Player)sender;
            if (args.length == 0) {
                for (int i = 0; i < Objects.requireNonNull(Main.getLang()).getStringList("UsageCommand").size(); i++)
                    player.sendMessage(minimessage.MessageCheange(Main.getLang().getStringList("UsageCommand").get(i)));
            }else if (args[0].equalsIgnoreCase("stat")) {
                if (args[1].equalsIgnoreCase("set")) {
                    Player target = Bukkit.getPlayer(args[3]);
                    int amount = Integer.parseInt(args[4]);
                    PlayerAccount playerAccount = Main.getInstance().getPvPDatabaseMap().get(Objects.requireNonNull(target).getUniqueId());
                    if (args.length >= 4) {
                        if (args[2].equalsIgnoreCase("kill")) {playerAccount.setPlayerKill(amount);player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("SuccesSetStat")));}
                        else if (args[2].equalsIgnoreCase("death")) {playerAccount.setPlayerDeath(amount);player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("SuccesSetStat")));}
                        else if (args[2].equalsIgnoreCase("strike")) {playerAccount.setPlayerStreak(amount);player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("SuccesSetStat")));}
                        else if (args[2].equalsIgnoreCase("elo")) {playerAccount.setPlayerElo(amount);player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("SuccesSetStat")));}
                        else {player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("WrongUsage")));}
                    } else {player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("WrongUsage")));}
                } else if (args[1].equalsIgnoreCase("statsinfo")) {
                    Player target = Bukkit.getPlayer(args[3]);
                    PlayerAccount playerAccount = Main.getInstance().getPvPDatabaseMap().get(Objects.requireNonNull(target).getUniqueId());
                    if (args[2].equalsIgnoreCase("kill")) {player.sendMessage(ChatColor.GOLD + target.getName() + minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("PlayerKillStat")) + playerAccount.getPlayerKill());}
                    else if (args[2].equalsIgnoreCase("death")) {player.sendMessage(ChatColor.GOLD + target.getName() + minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("PlayerDeathStat")) + playerAccount.getPlayerDeath());}
                    else if (args[2].equalsIgnoreCase("strike")) {player.sendMessage(ChatColor.GOLD + target.getName() + minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("PlayerStrikeStat")) + playerAccount.getPlayerStreak());}
                    else if (args[2].equalsIgnoreCase("elo")) {player.sendMessage(ChatColor.GOLD + target.getName() + minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("PlayerEloStat")) + playerAccount.getPlayerElo());}
                    else {player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("WrongUsage")));}
                } else if (args[1].equalsIgnoreCase("statstop")) {
                    PlaceHolderLists placeHolderLists = Main.getInstance().getPlaceHolderLists();
                    if (args[2].equalsIgnoreCase("kill")) {for (int i = 0; i < 11; i++) {placeHolderLists.killist(i, 5);}}
                    else if (args[2].equalsIgnoreCase("death")) {for (int i = 0; i < 11; i++) {placeHolderLists.killist(i, 0);}}
                    else if (args[2].equalsIgnoreCase("strike")) {for (int i = 0; i < 11; i++) {placeHolderLists.killist(i, 2);}}
                    else if (args[2].equalsIgnoreCase("elo")) {for (int i = 0; i < 11; i++) {placeHolderLists.killist(i, 1);}}
                    else {player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("WrongUsage")));}
                }else if (args[1].equalsIgnoreCase("reset")) {
                    Main.getInstance().getAutoSave().reset();
                }
            }else if(args[0].equalsIgnoreCase("reload")) {
                player.sendMessage(minimessage.MessageCheange(Main.getLang().getString("reloadMessage")));
                Main.getInstance().reloadConfig();
                Main.getInstance().saveConfig();
                Main.getInstance().setConfigManager();
                Main.getInstance().nightsender();
            }
        }
        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String alias, String[] args) {
        List<String> c = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("nightcore")) {
            if(args.length == 1) {
                if(args[0].startsWith("s")) {c.add("stat");}
                else if(args[0].startsWith("r")) {c.add("reload");}
                else {c.add("stat");c.add("reload");}
            }else {
                if(args[0].equalsIgnoreCase("stat")) {
                    if(args.length == 2) {
                        if(sender.isOp()) {if (args[1].startsWith("se")) {c.add("set");}}
                        if (args[1].startsWith("statst")) {c.add("statstop");}
                        else if (args[1].startsWith("st")) {c.add("statsinfo");c.add("statstop");}
                        else if (args[1].startsWith("r")) {c.add("reset");}
                        else {c.add("statsinfo");c.add("statstop");c.add("reset");if(sender.isOp()) {c.add("set");}}
                    }else if(args.length == 3) {
                        if (args[1].startsWith("k")) {c.add("kill");}
                        else if (args[1].startsWith("d")) {c.add("death");}
                        else if (args[1].startsWith("s")) {c.add("strike");}
                        else if (args[1].startsWith("e")) {c.add("elo");}
                        else {c.add("kill");c.add("death");c.add("strike");c.add("elo");}
                    }else if(args.length == 4) {
                        if(args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("statsinfo"))
                        {c.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());}
                    }else if(args.length == 5) {
                        if(args[1].equalsIgnoreCase("set")) {c.add("miktar");}
                    }
                }
            }
        }
        return c;
    }
    /*private String MessageTypeCh(int i, int j) {
        String string2 = Main.getLang().getString("EloMessageType");
        var miniMessage = Main.getInstance().getMinimessage();
        String value = Integer.toString(i);
        String dcommand = string2.replace("value", value);
        dcommand.replace("type", Integer.toString(j));
        return miniMessage.MessageCheange(dcommand);
    }*/

    //player.sendMessage(minimessage.MessageCheange(Objects.requireNonNull(Main.getLang()).getString("WrongUsage")));
}

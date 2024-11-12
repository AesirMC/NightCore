package org.night.nightcore.stats.placeholder;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.night.nightcore.Main;
import org.night.nightcore.stats.statmanager.PlayerAccount;

import java.util.*;

public class PlaceHolderLists {
    private final Main plugin;
    public PlaceHolderLists(Main plugin){
        this.plugin = plugin;
    }
    private final List<PlayerAccount> liste = new ArrayList<>();

    public String killist(int i, int s) {
        var PapiMap = Main.getInstance().getPapiMap();
        if(PapiMap.get(i) != null) {
            if(PapiMap.size() > i) {
            var degistirilmisComp = Comparator.comparing(PlayerAccount::getPlayerKill);
            var papiMapDegeri = PapiMap.get(i).getPlayerKill();
            if(s == 0) {papiMapDegeri = PapiMap.get(i).getPlayerDeath(); degistirilmisComp = Comparator.comparing(PlayerAccount::getPlayerDeath);}
            else if(s == 1) {papiMapDegeri = PapiMap.get(i).getPlayerElo(); degistirilmisComp = Comparator.comparing(PlayerAccount::getPlayerElo);}
            else if(s == 2) {papiMapDegeri = PapiMap.get(i).getPlayerStreak(); degistirilmisComp = Comparator.comparing(PlayerAccount::getPlayerStreak);}
            if(!liste.isEmpty()) {
                if(PapiMap.get(i) != liste.get(i)) {
                    PapiMap.sort(degistirilmisComp);
                    Collections.reverse(PapiMap);
                    liste.clear();
                    liste.addAll(PapiMap);
                    //getTop(10, PapiMap, 3);
                }
            }else {liste.addAll(PapiMap);}
            OfflinePlayer playerName = Bukkit.getOfflinePlayer(UUID.fromString(PapiMap.get(i).getUuid()));
            String playerScore = Integer.toString(papiMapDegeri);
            return Main.getInstance().getMinimessage().MessageCheange(player_nameCh(Objects.requireNonNull(Objects.requireNonNull(Main.getLang()).getString("PlayerTop")), playerScore, playerName));
            }
        }
        return "Boş";
    }
    /*public void getTop(int size, List<PlayerAccount> list, int type) {
        for(int count = 0; count < size; count++){
            int top = 0;
            PlayerAccount topAC = null;
            for(PlayerAccount y : list) {
                int stat;
                switch (type) {
                    case 0: stat = y.getPlayerDeath(); break;
                    case 1: stat = y.getPlayerElo(); break;
                    case 2: stat = y.getPlayerStreak(); break;
                    case 3: stat = y.getPlayerKill(); break;
                    default: stat = 0;
                }
                if(stat >= top) {
                    boolean flag = false;
                    for(PlayerAccount asd : liste){
                        if(asd == y){
                            flag = true;
                            break;
                        }
                    } if(!flag) {
                        top = stat;
                        topAC = y;
                    }
                }
            }
            liste.add(topAC);
        }
    }*/

    public String player_nameCh(String string, String val, OfflinePlayer player) {
        String player_name = player.getName();
        assert player_name != null;
        String dcommand = string.replace("player_name", player_name);
        return dcommand.replace("value", val);
    }
}
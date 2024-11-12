package org.night.nightcore.stats.statmanager;

import lombok.Getter;
import lombok.Setter;

public class PlayerAccount {
    @Getter @Setter private String uuid;
    @Getter @Setter private String playerName;
    @Getter @Setter private int PlayerKill;
    @Getter @Setter private int PlayerDeath;
    @Getter @Setter private int PlayerElo;
    @Getter @Setter private int PlayerStreak;
    public PlayerAccount(String uuid, String playerName, int playerKill, int playerDeath, int playerElo, int playerStreak) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.PlayerKill = playerKill;
        this.PlayerDeath = playerDeath;
        this.PlayerElo = playerElo;
        this.PlayerStreak = playerStreak;
    }
}

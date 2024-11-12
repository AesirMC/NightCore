package org.night.nightcore.main;

import lombok.Getter;
import lombok.Setter;
import org.night.nightcore.Main;

import java.util.List;
import java.util.Map;

public class ConfigManager {
    @Getter @Setter private Integer AntiAbuse;
    @Getter @Setter private Integer AntiAbuseSecond;
    @Getter @Setter private Integer AutoSaveTick;
    @Getter @Setter private Integer ListCommandsActive;
    @Getter @Setter private List<String> ListCommands;
    @Getter @Setter private String ServerName;
    @Getter @Setter private Integer AntiItemNameAnvil;
    @Getter @Setter private Integer WhiteListMode;
    @Getter @Setter private List<String> WhiteListRegions;
    public ConfigManager(Integer antiAbuse, Integer antiAbuseSecond,
                         Integer autoSaveTick, Integer listCommandsActive,
                         List<String> listCommands, String serverName,
                         Integer antiItemNameAnvil, Integer whiteListMode,
                         List<String> whiteListRegions) {
        this.AntiAbuse = antiAbuse; this.AntiAbuseSecond = antiAbuseSecond;
        this.AutoSaveTick = autoSaveTick; this.ListCommandsActive = listCommandsActive;
        this.ListCommands = listCommands; this.ServerName = serverName;
        this.AntiItemNameAnvil = antiItemNameAnvil; this.WhiteListMode = whiteListMode;
        this.WhiteListRegions = whiteListRegions;
    }
}

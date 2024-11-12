package org.night.nightcore.stats.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.night.nightcore.Main;

public class Papi extends PlaceholderExpansion {
    private final Main plugin;

    public Papi(Main plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public String getIdentifier() {
        return "Night";
    }

    @NotNull
    public String getAuthor() {
        return "Night";
    }

    @NotNull
    public String getVersion() {
        return "1.0";
    }

    @Nullable
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        if (params.startsWith("elo_top_")) {
            String[] args = params.split("_");
            int index = Integer.parseInt(args[2]) - 1;
            return Main.getInstance().getPlaceHolderLists().killist(index, 1);
        }else if (params.startsWith("kill_top_")) {
            String[] args = params.split("_");
            int index = Integer.parseInt(args[2]) - 1;
            return Main.getInstance().getPlaceHolderLists().killist(index, 5);
        }else if (params.startsWith("death_top_")) {
            String[] args = params.split("_");
            int index = Integer.parseInt(args[2]) - 1;
            return Main.getInstance().getPlaceHolderLists().killist(index, 0);
        }else if (params.startsWith("strike_top_")) {
            String[] args = params.split("_");
            int index = Integer.parseInt(args[2]) - 1;
            return Main.getInstance().getPlaceHolderLists().killist(index, 2);
        }
        return null;
    }
}

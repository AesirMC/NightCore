package org.night.nightcore.main;

import org.night.nightcore.Main;
import org.night.nightcore.item.ItemNameCh;
import org.night.nightcore.stats.statevents.CreateEvents;
import org.night.nightcore.stats.statevents.PvPEvents;

public class MainListener {
    public MainListener() {
        Main.getInstance().getServer().getPluginManager().registerEvents(new PvPEvents(), Main.getInstance());
        Main.getInstance().getServer().getPluginManager().registerEvents(new CreateEvents(), Main.getInstance());
        Main.getInstance().getServer().getPluginManager().registerEvents(new ItemNameCh(), Main.getInstance());
    }
}

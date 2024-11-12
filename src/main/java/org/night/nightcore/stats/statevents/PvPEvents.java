package org.night.nightcore.stats.statevents;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.night.nightcore.Main;
import org.night.nightcore.stats.statmats.DeadnKillMath;
import org.night.nightcore.stats.statmats.EloMath;
import org.night.nightcore.stats.statmats.StrikeMath;

import java.util.Objects;

public class PvPEvents implements Listener {
    @EventHandler
    private void KillEvent(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity();
            Player killer = event.getEntity().getKiller();
            assert killer != null;
            var configManager = Main.getInstance().getConfigManager();
            if(configManager.getWhiteListMode() == 1) {
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regions = container.get((World) player.getWorld());
                ApplicableRegionSet set = Objects.requireNonNull(regions).getApplicableRegions(BlockVector3.at(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
                for (int i = 0; i < configManager.getWhiteListRegions().size(); i++) {
                    int finalI = i;
                    boolean isInSpecificRegion = set.getRegions().stream().anyMatch(region -> region.getId().equals(configManager.getWhiteListRegions().get(finalI)));
                    if(isInSpecificRegion) {
                        maths(player, killer);
                    }
                }
            }else {
                maths(player, killer);
            }
        }
    }

    private void maths(Player player, Player killer) {
        Main.getInstance().getEloMath().AntiAbuse(player, killer);
        Main.getInstance().getStrikeMath().StrikeAbuse(player, killer);
        Main.getInstance().getDeadnKillMath().AntiAbuseDead(player, killer);
    }
}

package org.night.nightcore.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.night.nightcore.Main;

public class ItemNameCh implements Listener {
    @EventHandler
    private void ItemNameCh(InventoryClickEvent e) {
        if (Main.getInstance().getConfigManager().getAntiItemNameAnvil() == 1) {
            if (e.getInventory().getType() == InventoryType.ANVIL &&
                    e.getRawSlot() == e.getView().convertSlot(e.getRawSlot()) && e.getRawSlot() == 2 &&
                    e.getInventory().getItem(0) != null &&
                    e.getInventory().getItem(0).getType() != Material.AIR &&
                    e.getInventory().getItem(2) != null &&
                    e.getInventory().getItem(2).getType() != Material.AIR) {
                ItemMeta oldMeta = e.getInventory().getItem(0).getItemMeta();
                ItemMeta meta = e.getCurrentItem().getItemMeta();
                if (meta.hasDisplayName() && meta.hasDisplayName()) {
                    if (!meta.getDisplayName().equals(oldMeta.getDisplayName()) &&
                            !e.getWhoClicked().hasPermission("nightcore.bypass.all"))
                        dontRename((Player) e.getWhoClicked(), e.getCurrentItem(), meta, oldMeta);
                } else if (meta.hasDisplayName() && !oldMeta.hasDisplayName() &&
                        !e.getWhoClicked().hasPermission("nightcore.bypass.all")) {
                    dontRename((Player) e.getWhoClicked(), e.getCurrentItem(), meta, oldMeta);
                }
            }
        }
    }

    private void dontRename(Player p, ItemStack item, ItemMeta meta, ItemMeta oldMeta) {
        meta.setDisplayName(oldMeta.getDisplayName());
        item.setItemMeta(meta);
        p.giveExpLevels(1);
    }
}

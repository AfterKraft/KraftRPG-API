package com.afterkraft.kraftrpg.api.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.milkbowl.vault.item.Items;

import org.bukkit.inventory.ItemStack;

/**
 * @author gabizou
 */
public class SpellRequirement {

    private Map<String, ItemStack> items = new HashMap<String, ItemStack>();

    public SpellRequirement() { }

    public SpellRequirement(ItemStack... items) {
        for (ItemStack item : items) {
            if (item != null) {
                String itemName;
                if (item.getItemMeta().hasDisplayName()) {
                    itemName = item.getItemMeta().getDisplayName();
                }
                else {
                    itemName = Items.itemByStack(item).getName();
                }
                if (this.items.containsKey(itemName))
                this.items.put(itemName, item.clone());
            }
        }
    }

    public Map<String, ItemStack> getItems() {
        return Collections.unmodifiableMap(this.items);
    }

    /**
     * Check if this SpellRequirement contains a specific Item. This
     * checks the item's label or custom lore name if applicable.
     *
     * SpellRerquirement is, by nature, lore and enchantment independent for matching.
     * @param item
     * @return
     */
    public boolean hasItem(ItemStack item) {
        String itemName;
        if (item.getItemMeta().hasDisplayName()) {
            itemName = item.getItemMeta().getDisplayName();
        }
        else {
            itemName = Items.itemByStack(item).getName();
        }
        return this.items.containsKey(itemName);
    }

    /**
     * Check if this SpellRequirement
     * @param item
     * @return
     */
    public boolean hasExactItem(ItemStack item) {
        String itemName;
        if (item.getItemMeta().hasDisplayName()) {
            itemName = item.getItemMeta().getDisplayName();
        }
        else {
            itemName = Items.itemByStack(item).getName();
        }
        ItemStack mapItem = this.items.get(itemName);
        return mapItem != null && item.equals(mapItem);
    }

    public boolean addItem(ItemStack item) {
        if (item == null || !hasExactItem(item)) {
            return false;
        }
        String itemName;
        if (item.getItemMeta().hasDisplayName()) {
            itemName = item.getItemMeta().getDisplayName();
        }
        else {
            itemName = Items.itemByStack(item).getName();
        }
        this.items.put(itemName, item);
        return true;

    }


}

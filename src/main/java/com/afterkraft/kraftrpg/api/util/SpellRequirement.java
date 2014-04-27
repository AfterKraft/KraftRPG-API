package com.afterkraft.kraftrpg.api.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

/**
 * @author gabizou
 */
public class SpellRequirement {

    private Set<ItemStack> items = new HashSet<ItemStack>();

    public SpellRequirement() { }

    public SpellRequirement(ItemStack... items) {
        for (ItemStack item : items) {
            if (item != null) {
                this.items.add(item.clone());
            }
        }
    }

    public Set<ItemStack> getItems() {
        return Collections.unmodifiableSet(this.items);
    }

    /**
     * Add the desired ItemStack to this requirement
     *
     * @param item to be added
     * @param force whether to overwrite the existing item if it exists
     * @return true if the item did not exist already, false if the item exists
     * and was not forced to be overwritten
     */
    public boolean addItem(ItemStack item, boolean force) {
        return item != null && (force || !hasItem(item)) && this.items.add(item);
    }

    /**
     * Check if this SpellRequirement contains a specific Item. This checks the
     * item's label or custom lore name if applicable.
     * <p/>
     * SpellRerquirement is, by nature, lore and enchantment independent for
     * matching.
     *
     * @param item
     * @return
     */
    public boolean hasItem(ItemStack item) {
        return this.items.contains(item);
    }
}

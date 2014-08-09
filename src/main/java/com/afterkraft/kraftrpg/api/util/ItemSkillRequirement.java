/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;


public class ItemSkillRequirement implements SkillRequirement {

    private Set<ItemStack> items = new HashSet<ItemStack>();

    public ItemSkillRequirement() {

    }

    public ItemSkillRequirement(ItemStack... items) {
        for (ItemStack item : items) {
            if (item != null) {
                this.items.add(item.clone());
            }
        }
    }


    @Override
    public boolean isSatisfied(SkillCaster caster) {
        return true;
    }

    public Set<ItemStack> getItems() {
        return Collections.unmodifiableSet(this.items);
    }

    /**
     * Add the desired ItemStack to this requirement
     *
     * @param item  to be added
     * @param force whether to overwrite the existing item if it exists
     * @return true if the item did not exist already, false if the item
     * exists and was not forced to be overwritten
     */
    public boolean addItem(ItemStack item, boolean force) {
        return item != null && (force || !hasItem(item)) && this.items.add(item);
    }

    /**
     * Check if this SkillRequirement contains a specific Item. This checks
     * the item's label or custom lore name if applicable.
     * <p/>
     * {@link com.afterkraft.kraftrpg.api.util.ItemSkillRequirement} is, by
     * nature, lore and enchantment independent for matching.
     *
     * @param item to check whether it is in this SkillRequirement
     * @return true if the item is in this requirement
     */
    public boolean hasItem(ItemStack item) {
        return this.items.contains(item);
    }
}

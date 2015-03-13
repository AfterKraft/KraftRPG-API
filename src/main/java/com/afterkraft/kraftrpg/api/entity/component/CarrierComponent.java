/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.afterkraft.kraftrpg.api.entity.component;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.base.Optional;

public interface CarrierComponent extends Component<CarrierComponent> {


    /**
     * A cleaning method provided to ensure possible connected client's view of an inventory is
     * properly refreshed, and not erroneously represented.
     */
    void updateInventory();

    /**
     * Assumes this being has a functional hand, this will return the {@link ItemStack} in said
     * hand.
     *
     * @return the ItemStack in the hand of the being.
     */
    Optional<ItemStack> getItemInHand();

    /**
     * Returns a proper {@link Inventory} that belongs to this being. It can be modified and queried
     * without exception.
     *
     * @return the inventory belonging to this being.
     */
    Inventory getInventory();

    /**
     * Provided as a utility method to get a copy of the being's armor. Implementations may vary,
     * but this should be assured to follow the index of: <ol><li>0 - Boots</li><li>1 -
     * Leggings</li><li>2 - Chestplate</li><li>3 - Helmet</li></ol>.
     *
     * <p>Any customized armor pieces not covered by this array should be handled on a case by case
     * basis.</p>
     *
     * @return The copy of the ItemStack list of the armor for this being
     */
    ItemStack[] getArmor();

    /**
     * Set the armor piece of the specified armor slot. The item can be null
     *
     * @param item      to set, if not null
     * @param armorSlot to set
     */
    void setArmor(ItemStack item, int armorSlot);

    /**
     * Check if this being is capable of equipping the {@link ItemStack}. This is primarily used for
     * damage listeners and other logic that may be needed throughout KraftRPG
     *
     * @param itemStack to check
     *
     * @return true if the item is a valid item to equip
     */
    boolean canEquipItem(ItemStack itemStack);

}

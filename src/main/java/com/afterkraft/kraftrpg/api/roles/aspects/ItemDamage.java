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

package com.afterkraft.kraftrpg.api.roles.aspects;

import java.util.Map;

import org.spongepowered.api.item.ItemType;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.roles.RoleAspect;

public final class ItemDamage implements RoleAspect {
    private final ItemType[] itemTypes;
    private final double[] baseDamages;
    private final double[] damageIncreases;

    public Optional<Double> getItemBaseDamage(ItemType type);

    public Optional<Double> getItemDamageAtLevel(ItemType type, int level);

    /**
     *
     * @param type
     * @return
     */
    public Optional<Double> getItemDamagePerLevel(ItemType type);

    /**
     * Gets a map of all configured item damages by {@link ItemType}.
     *
     * @return A map of all item types and their prescribed damages
     */
    public Map<ItemType, Double> getItemDamages();
}

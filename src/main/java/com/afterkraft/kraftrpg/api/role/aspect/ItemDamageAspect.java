/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.role.aspect;

import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.item.ItemType;

import com.google.common.collect.Maps;

import com.afterkraft.kraftrpg.api.role.RoleAspect;

/**
 * Represents a {@link RoleAspect} that handles item weapon damages.
 */
public final class ItemDamageAspect implements RoleAspect {
    private final ItemType[] itemTypes;
    private final double[] baseDamages;
    private final double[] damageIncreases;

    private ItemDamageAspect(ItemDamageAspectBuilder builder) {
        Map<ItemType, Double> baseDamages = builder.baseDamages;
        this.itemTypes = new ItemType[baseDamages.size()];
        this.baseDamages = new double[this.itemTypes.length];
        this.damageIncreases = new double[this.itemTypes.length];
        int counter = 0;
        for (Map.Entry<ItemType, Double> entry : baseDamages.entrySet()) {
            this.itemTypes[counter] = entry.getKey();
            this.baseDamages[counter] = entry.getValue();
            this.damageIncreases[counter] =
                    builder.damageIncreases.get(entry.getKey());
            counter++;
        }

    }

    /**
     * Creates a new {@link ItemDamageAspectBuilder} to create {@link ItemDamageAspect}s.
     *
     * @return A new builder
     */
    public static ItemDamageAspectBuilder builder() {
        return new ItemDamageAspectBuilder();
    }

    /**
     * Gets the base damage  of the given {@link ItemType}.
     *
     * @param type The item type to check
     *
     * @return The base damage, if available
     */
    public Optional<Double> getItemBaseDamage(ItemType type) {
        int index = 0;
        for (ItemType itemType : this.itemTypes) {
            if (itemType.equals(type)) {
                return Optional.of(this.baseDamages[index]);
            }
            index++;
        }
        return Optional.empty();
    }

    /**
     * Gets the prescribed damage at the desired level for a given {@link ItemType}.
     *
     * @param type  The item type to check
     * @param level The level to check at
     *
     * @return The damage at the given leve, if available
     */
    public Optional<Double> getItemDamageAtLevel(ItemType type, int level) {
        int index = 0;
        for (ItemType itemType : this.itemTypes) {
            if (itemType.equals(type)) {
                return Optional.of(this.baseDamages[index] + ((level - 1) * this
                        .damageIncreases[index]));
            }
            index++;
        }
        return Optional.empty();
    }

    /**
     * Gets the increase of damage per level of the given {@link ItemType}.
     *
     * @param type The item type to check
     *
     * @return The increase per level, if available
     */
    public Optional<Double> getItemDamagePerLevel(ItemType type) {
        int index = 0;
        for (ItemType itemType : this.itemTypes) {
            if (itemType.equals(type)) {
                return Optional.of(this.damageIncreases[index]);
            }
            index++;
        }
        return Optional.empty();
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return null;
    }


    /**
     * Represents a builder for {@link ItemDamageAspect}s.
     */
    public static final class ItemDamageAspectBuilder {
        Map<ItemType, Double> baseDamages = Maps.newHashMap();
        Map<ItemType, Double> damageIncreases = Maps.newHashMap();

        private ItemDamageAspectBuilder() {
        }

        /**
         * Sets the base damage of the given {@link ItemType}.
         *
         * @param type   The item type
         * @param damage The base damage to deal
         *
         * @return This builder, for chaining
         */
        public ItemDamageAspectBuilder setBaseDamage(ItemType type,
                                                     double damage) {
            checkNotNull(type);
            checkArgument(damage > 0);
            this.baseDamages.put(type, damage);
            return this;
        }

        /**
         * Sets the damage incrase per level of the given {@link ItemType}.
         *
         * @param type           The item type
         * @param damageIncrease The base damage to deal
         *
         * @return This builder, for chaining
         */
        public ItemDamageAspectBuilder setDamageIncrease(ItemType type,
                                                         double damageIncrease) {
            checkNotNull(type);
            checkState(this.baseDamages.containsKey(type));
            checkArgument(damageIncrease >= 0);
            this.damageIncreases.put(type, damageIncrease);
            return this;
        }

        /**
         * Creates a new {@link ItemDamageAspect}.
         *
         * @return A new ItemDamageAspect
         */
        public ItemDamageAspect build() {
            return new ItemDamageAspect(this);
        }

        /**
         * Clears all pre-existing values of this builder for reuse.
         *
         * @return This builder, for chaining
         */
        public ItemDamageAspectBuilder reset() {
            this.baseDamages = Maps.newHashMap();
            this.damageIncreases = Maps.newHashMap();
            return this;
        }
    }
}

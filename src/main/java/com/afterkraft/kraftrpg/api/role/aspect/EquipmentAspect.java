/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.Lists;

import com.afterkraft.kraftrpg.api.role.RoleAspect;

/**
 * Represents a {@link RoleAspect} that determines whether an {@link ItemStack} or {@link ItemType}
 * is equippable as weapon and/or armor.
 */
public final class EquipmentAspect implements RoleAspect {
    private final ItemType[] allowedWeapons;
    private final ItemType[] allowedArmor;

    private EquipmentAspect(EquipmentAspectBuilder builder) {
        this.allowedArmor = builder.allowedArmor
                .toArray(new ItemType[builder.allowedArmor.size()]);
        this.allowedWeapons = builder.allowedWeapons
                .toArray(new ItemType[builder.allowedWeapons.size()]);
    }

    /**
     * Gets a builder for an EquipmentAspect aspect.
     *
     * @return A new builder
     */
    public static EquipmentAspectBuilder builder() {
        return new EquipmentAspectBuilder();
    }

    /**
     * Checks if the material type is considered to be an equipable Weapon for this role.
     *
     * @param itemStack The itemstack itself
     *
     * @return True if the material is allowed as a weapon
     */
    boolean isWeaponEquippable(ItemStack itemStack) {
        return isWeaponEquippable(itemStack.getItem());
    }


    /**
     * Checks if the material type is considered to be an equipable Weapon for this role.
     *
     * @param itemType The type of material to check
     *
     * @return True if the material is allowed as a weapon
     */
    boolean isWeaponEquippable(ItemType itemType) {
        checkNotNull(itemType);
        for (ItemType item : this.allowedWeapons) {
            if (itemType.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the material type is considered to be a wearable Armor piece for this role.
     *
     * @param itemStack The type of material to check
     *
     * @return True if the material is allowed as an armor piece
     */
    boolean isArmorEquippable(ItemStack itemStack) {
        return isArmorEquippable(itemStack.getItem());
    }

    /**
     * Checks if the material type is considered to be a wearable Armor piece for this role.
     *
     * @param itemType The type of material to check
     *
     * @return True if the material is allowed as an armor piece
     */
    boolean isArmorEquippable(ItemType itemType) {
        checkNotNull(itemType);
        for (ItemType item : this.allowedArmor) {
            if (itemType.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Represents a builder for an EquipmentAspect aspect
     */
    public static class EquipmentAspectBuilder {

        List<ItemType> allowedWeapons = Lists.newArrayList();
        List<ItemType> allowedArmor = Lists.newArrayList();

        private EquipmentAspectBuilder() {

        }

        /**
         * Adds the specified ItemType type to the allowed armor for this role. Armor can restricted
         * to different roles.
         *
         * @param type Type of armor material
         *
         * @return This builder for chaining
         */
        @SuppressWarnings("unused")
        public EquipmentAspectBuilder addAllowedArmor(ItemType type) {
            checkNotNull(type);
            this.allowedArmor.add(type);
            return this;
        }

        /**
         * Adds the given item type as an allowed weapon.
         *
         * @param type The item type
         *
         * @return This buidler for chaining
         */
        @SuppressWarnings("unused")
        public EquipmentAspectBuilder addAllowedWeapon(ItemType type) {
            checkNotNull(type);
            this.allowedWeapons.add(type);
            return this;
        }

        /**
         * Clears this builder of all settings ready for new data.
         *
         * @return This bulider for chaining
         */
        public EquipmentAspectBuilder reset() {
            this.allowedArmor = Lists.newArrayList();
            this.allowedWeapons = Lists.newArrayList();
            return this;
        }

        /**
         * Returns a new {@link EquipmentAspect}.
         *
         * @return A new equipment aspect
         */
        public EquipmentAspect build() {
            return new EquipmentAspect(this);
        }
    }
}

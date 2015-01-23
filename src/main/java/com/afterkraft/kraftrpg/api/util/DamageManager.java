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
package com.afterkraft.kraftrpg.api.util;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.projectile.Arrow;
import org.spongepowered.api.entity.projectile.Egg;
import org.spongepowered.api.entity.projectile.EnderPearl;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.entity.projectile.ThrownExpBottle;
import org.spongepowered.api.entity.projectile.fireball.LargeFireball;
import org.spongepowered.api.entity.projectile.fireball.SmallFireball;
import org.spongepowered.api.entity.projectile.fireball.WitherSkull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;

import com.typesafe.config.Config;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.Monster;
import com.afterkraft.kraftrpg.common.DamageCause;

/**
 * The generalized manager for calculating and fetching damages for entities and items alike.
 */
public interface DamageManager extends Manager {

    /**
     * Attempts to calculate the highest damage given for the given ItemStack. This will check all
     * possibilities of damage modification if the Insentient is a {@link
     * com.afterkraft.kraftrpg.api.entity.SkillCaster} or {@link com.afterkraft.kraftrpg.api.entity.Sentient}
     * to which {@link com.afterkraft.kraftrpg.api.roles.Role}s will be allowed to modify the damage
     * dealt from the Item.  If the Insentient has no default damages for the Item, the damage
     * returned will be 0.
     *
     * @param being         the Insentient to query for damages
     * @param defender      The defendant
     * @param defaultDamage The default damage to deal
     *
     * @return the highest possible damage for the item from any possible modifications
     */
    double getHighestItemDamage(Insentient being, Insentient defender, double defaultDamage);

    double getHighestProjectileDamage(Insentient champion, ProjectileType type);

    public double getDefaultItemDamage(ItemType type, double damage);

    double getDefaultItemDamage(ItemType type);

    void setDefaultItemDamage(ItemType type, double damage);

    boolean doesItemDamageVary(ItemType type);

    void setItemDamageVarying(ItemType type, boolean isVarying);

    /**
     * Gets the default damage for the given EntityType.
     *
     * @param type of Entity we are querying
     *
     * @return the damage from the default configuration for the given EntityType
     */
    double getEntityDamage(EntityType type);

    double getEnvironmentalDamage(DamageCause cause);

    double getEnchantmentDamage(Enchantment enchantment, int enchantmentLevel);

    /**
     * Utility method to calculate the modified damage from an Enchantment damage caused by an Armor
     * piece or Weapon. The {@link com.afterkraft.kraftrpg.api.entity.Insentient} is used to check
     * for any possible {@link com.afterkraft.kraftrpg.api.roles.Role} damage modifications
     * necessary.  If varying damage is enabled, the damage will already take this into account.
     *
     * @param being       wearing/using the enchanted ItemStack
     * @param enchantment to calculate for
     * @param item        that is enchanted
     *
     * @return calculated damage that may be varied if enabled
     */
    double getItemEnchantmentDamage(Insentient being, Enchantment enchantment,
                                    ItemStack item);

    /**
     * Calculate the fall reduction for this being considering various sources including armor.
     *
     * @param being The being that is falling
     *
     * @return The damage being dealt by falling
     */
    double getFallReduction(Insentient being);

    /**
     * Attempts to calculate various possible modifications to the default damage for the given
     * Monster depending on various things like location, base damage, whether it was spawned from a
     * Mob Spawner and possibly other things varying by the implementation.
     *
     * @param monster     the Monster to query
     * @param location    the spawn location of the Monster
     * @param baseDamage  the default base damage of the Monster
     * @param fromSpawner whether the Monster originated from a Spawner
     *
     * @return the calculated customized damage for the Monster
     */
    double getModifiedEntityDamage(final Monster monster, final Location location,
                                   final double baseDamage,
                                   final Cause fromSpawner);

    double getDefaultEntityHealth(final Living entity);

    double getModifiedEntityHealth(final Living entity);

    boolean doesEntityDealVaryingDamage(EntityType type);

    void setEntityToDealVaryingDamage(EntityType type, boolean
            dealsVaryingDamage);

    boolean isStandardWeapon(ItemType material);

    /**
     * Load defaults for the default damages from the given Configuration
     *
     * @param config repsective for this manager
     */
    void load(Config config);

    /**
     * Standard projectile type enum for use by the DamageManager.
     */
    enum ProjectileType {
        ARROW,
        EGG,
        SNOWBALL,
        SMALL_FIREBALL,
        LARGE_FIREBALL,
        ENDERPEARL,
        WITHER_SKULL,
        THROWN_EXP_BOTTLE;

        static ProjectileType valueOf(Entity entity) {
            if (entity instanceof Arrow) {
                return ARROW;
            } else if (entity instanceof Snowball) {
                return SNOWBALL;
            } else if (entity instanceof Egg) {
                return EGG;
            } else if (entity instanceof LargeFireball) {
                return LARGE_FIREBALL;
            } else if (entity instanceof WitherSkull) {
                return WITHER_SKULL;
            } else if (entity instanceof SmallFireball) {
                return SMALL_FIREBALL;
            } else if (entity instanceof EnderPearl) {
                return ENDERPEARL;
            } else if (entity instanceof ThrownExpBottle) {
                return THROWN_EXP_BOTTLE;
            } else {
                return null;
            }
        }
    }

}

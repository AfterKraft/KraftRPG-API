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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.Monster;

/**
 * The generalized manager for calculating and fetching damages for entities
 * and items alike.
 */
public interface DamageManager extends Manager {

    /**
     * Attempts to calculate the highest damage given for the given ItemStack.
     * This will check all possibilities of damage modification if the
     * Insentient is a {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}
     * or {@link com.afterkraft.kraftrpg.api.entity.Sentient} to which
     * {@link com.afterkraft.kraftrpg.api.roles.Role}s will be allowed to
     * modify the damage dealt from the Item.
     * <p/>
     * If the Insentient has no default damages for the Item, the damage
     * returned will be 0.
     * 
     * @param being the Insentient to query for damages
     * @param defaultDamage
     * @return the highest possible damage for the item from any possible
     *         modifications
     */
    public double getHighestItemDamage(Insentient being, Insentient defender, double defaultDamage);

    public double getHighestProjectileDamage(Insentient champion, ProjectileType type);

    /**
     * Request the default damage for the provided {@link org.bukkit.Material}
     * and uses the default damage provided if the default damage is not
     * configured.
     * 
     * @param type
     * @param damage
     * @return
     */
    public double getDefaultItemDamage(Material type, double damage);

    /**
     * 
     * @param type
     * @return
     */
    public double getDefaultItemDamage(Material type);

    /**
     * 
     * @param type
     * @param damage
     */
    public void setDefaultItemDamage(Material type, double damage);

    /**
     * 
     * @param type
     * @return
     */
    public boolean doesItemDamageVary(Material type);

    /**
     * 
     * @param type
     * @param isVarying
     */
    public void setItemDamageVarying(Material type, boolean isVarying);

    /**
     * Gets the default damage for the given EntityType.
     * 
     * @param type of Entity we are querying
     * @return the damage from the default configuration for the given
     *         EntityType
     */
    public double getEntityDamage(EntityType type);

    public double getEnvironmentalDamage(DamageCause cause);

    public double getEnchantmentDamage(Enchantment enchantment, int enchantmentLevel);

    /**
     * Utility method to calculate the modified damage from an Enchantment
     * damage caused by an Armor piece or Weapon. The
     * {@link com.afterkraft.kraftrpg.api.entity.Insentient} is used to check
     * for any possible {@link com.afterkraft.kraftrpg.api.roles.Role} damage
     * modifications necessary.
     * 
     * If varying damage is enabled, the damage will already take this into
     * account.
     * 
     * @param being wearing/using the enchanted ItemStack
     * @param enchantment to calculate for
     * @param item that is enchanted
     * @return calculated damage that may be varied if enabled
     */
    public double getItemEnchantmentDamage(Insentient being, Enchantment enchantment, ItemStack item);

    /**
     * Calculate the fall reduction for this being considering various sources
     * including armor.
     * 
     * @param being
     * @return
     */
    public double getFallReduction(Insentient being);

    /**
     * Attempts to calculate various possible modifications to the default
     * damage for the given Monster depending on various things like location,
     * base damage, whether it was spawned from a Mob Spawner and possibly
     * other things varying by the implementation.
     * 
     * @param monster the Monster to query
     * @param location the spawn location of the Monster
     * @param baseDamage the default base damage of the Monster
     * @param fromSpawner whether the Monster originated from a Spawner
     * @return the calculated customized damage for the Monster
     */
    public double getModifiedEntityDamage(final Monster monster, final Location location, final double baseDamage, final CreatureSpawnEvent.SpawnReason fromSpawner);

    public double getDefaultEntityHealth(final LivingEntity entity);

    public double getModifiedEntityHealth(final LivingEntity entity);

    public boolean doesEntityDealVaryingDamage(EntityType type);

    public void setEntityToDealVaryingDamage(EntityType type, boolean dealsVaryingDamage);

    public boolean isStandardWeapon(Material material);

    /**
     * Load defaults for the default damages from the given Configuration
     * 
     * @param config repsective for this manager
     */
    public void load(Configuration config);

    public enum ProjectileType {
        ARROW,
        EGG,
        SNOWBALL,
        SMALL_FIREBALL,
        LARGE_FIREBALL,
        ENDERPEARL,
        WITHER_SKULL,
        THROWN_EXP_BOTTLE;

        public static ProjectileType valueOf(Entity entity) {
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

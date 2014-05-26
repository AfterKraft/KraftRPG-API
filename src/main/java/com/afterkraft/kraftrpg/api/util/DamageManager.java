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
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Monster;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * The generalized manager for calculating and fetching damages for entities
 * and items alike.
 */
public interface DamageManager extends Manager {

    /**
     * Attempts to calculate the highest damage given for the given ItemStack.
     * This will check all active
     * {@link com.afterkraft.kraftrpg.api.roles.Role}s that the given
     * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} may have.
     * <p/>
     * If the SkillCaster has no active Roles or the resulting damage is
     * negative, 0 will be returned
     * 
     * @param caster the SkillCaster to query for Roles
     * @param item the Item to query for the damage
     * @return the highest possible damage for the item from any active Roles,
     *         if not 0
     */
    public double getHighestItemDamage(SkillCaster caster, ItemStack item);

    public double getHighestProjectileDamage(Champion champion, ProjectileType type);

    /**
     * Gets the default damage for the given EntityType.
     * 
     * @param type of Entity we are querying
     * @return the damage from the default configuration for the given
     *         EntityType
     */
    public double getEntityDamage(EntityType type);

    public double getEnvironmentalDamage(EntityDamageEvent.DamageCause cause);

    public double getEnchantmentDamage(Enchantment enchantment);

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

    /**
     * Load defaults for the default damages from the given Configuration
     * 
     * @param config repsective for this manager
     */
    public void load(Configuration config);

    public enum ProjectileType {
        ARROW,
        EGG,
        SNOWBALL;

        public static ProjectileType match(final String name) {
            if (name.equalsIgnoreCase("arrow")) {
                return ARROW;
            } else if (name.equalsIgnoreCase("snowball")) {
                return SNOWBALL;
            } else if (name.equalsIgnoreCase("egg")) {
                return EGG;
            } else {
                return null;
            }
        }

        public static ProjectileType valueOf(Entity entity) {
            if (entity instanceof Arrow) {
                return ARROW;
            } else if (entity instanceof Snowball) {
                return SNOWBALL;
            } else if (entity instanceof Egg) {
                return EGG;
            } else {
                return null;
            }
        }
    }

}

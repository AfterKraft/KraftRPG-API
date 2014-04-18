package com.afterkraft.kraftrpg.api.util;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Monster;

/**
 * The generalized manager for calculating and fetching damages for entities
 * and items alike.
 */
public interface DamageManager extends Manager {

    /**
     * Attempts to calculate the highest damage given for the given ItemStack. This will
     * check all active {@link com.afterkraft.kraftrpg.api.entity.roles.Role}s that the given
     * {@link com.afterkraft.kraftrpg.api.entity.Champion} may have.
     * <p>
     * If the Champion has no active Roles or the resulting damage is negative, 0 will be returned
     * @param champion the champion to query for Roles
     * @param item the Item to query for the damage
     * @return the highest possible damage for the item from any active Roles, if not 0
     */
    public double getHighestItemDamage(Champion champion, ItemStack item);

    /**
     * Gets the default damage for the given EntityType.
     * @param type of Entity we are querying
     * @return the damage from the default configuration for the given EntityType
     */
    public double getEntityDamage(EntityType type);

    /**
     * Attempts to calculate various possible modifications to the default damage for the given Monster
     * depending on various things like location, base damage, whether it was spawned from a
     * Mob Spawner and possibly other things varying by the implementation.
     * @param monster the Monster to query
     * @param location the spawn location of the Monster
     * @param baseDamage the default base damage of the Monster
     * @param fromSpawner whether the Monster originated from a Spawner
     * @return the calculated customized damage for the Monster
     */
    public double getModifiedEntityDamage(final Monster monster, final Location location, final double baseDamage, final boolean fromSpawner);

    /**
     * Load defaults for the default damages from the given Configuration
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

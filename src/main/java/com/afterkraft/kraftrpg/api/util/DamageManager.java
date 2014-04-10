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
 * @author gabizou
 */
public interface DamageManager extends Manager {

    public double getHighestItemDamage(Champion champion, ItemStack item);

    public double getEntityDamage(EntityType type);

    public double getModifiedEntityDamage(final Monster monster, final Location location, final double baseDamage, final boolean fromSpawner);

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

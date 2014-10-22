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
package com.afterkraft.kraftrpg.api.entity;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Represents an {@link Entity} linked with KraftRPG. This is the base for all entities in
 * KraftRPG.
 */
public interface IEntity {

    /**
     * Get the Bukkit name of this Entity
     *
     * @return the Bukkit name of this Entity
     */
    String getName();

    /**
     * Get the cleansed name for this Entity. It may be a customized name.
     *
     * @return The customized display name for this Entity
     */
    String getDisplayName();

    /**
     * Check if the attached {@link org.bukkit.entity.LivingEntity} is still valid and not null
     *
     * @return true if the LivingEntity is not null
     */
    boolean isValid();

    /**
     * Check if the {@link org.bukkit.entity.Entity#isValid()}. This also checks if the reference of
     * the entity {@link #isValid()}.
     *
     * @return true if the LivingEntity is alive and valid
     */
    boolean isEntityValid();

    /**
     * Returns the Entity if the Entity {@link #isEntityValid()}.
     *
     * @return the linked Entity if not null
     */
    Entity getEntity();

    /**
     * Gets the type of Entity this IEntity is wrapping.
     *
     * @return The type of entity this IEntity is wrapping
     */
    EntityType getEntityType();

    /**
     * Reset the linked Entity to the provided {@link org.bukkit.entity.Entity} if the {@link
     * java.util.UUID} match for the old reference and the provided reference
     *
     * @param entity the Entity to re-attach this IEntity to
     *
     * @return true if successful, false if UUID did not match
     * @throws IllegalArgumentException If the entity is null
     */
    boolean setEntity(Entity entity);

    /**
     * Returns the linked Entity's UUID provided by the server.
     *
     * @return the linked UUID to this entity.
     */
    UUID getUniqueID();

    /**
     * Return the {@link org.bukkit.Location} of this being.
     *
     * @return the location of the being.
     */
    Location getLocation();

    /**
     * Gets a list of entities within a bounding box centered around this IEntity.
     *
     * @param x Half the size of the box along the x axis
     * @param y Half the size of the box along the y axis
     * @param z Half the size of the box along the z axis
     *
     * @return The List of entities nearby
     */
    List<Entity> getNearbyEntities(double x, double y, double z);

    /**
     * Shortcut method for getting the entity and then the world.
     *
     * @return The world of this being.
     */
    World getWorld();

    /**
     * Check if this being is on solid ground (not flying or jumping) in the air
     *
     * @return true if the being is not in the air
     */
    boolean isOnGround();

}

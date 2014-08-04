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
package com.afterkraft.kraftrpg.api.entity;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Represents an {@link Entity} linked with KraftRPG. This is the base for all
 * entities in KraftRPG.
 */
public interface IEntity {

    /**
     * Get the Bukkit name of this Entity
     * 
     * @return the Bukkit name of this Entity
     */
    public String getName();

    /**
     * Get the cleansed name for this Entity. It may be a customized name.
     *
     * @return The customized display name for this Entity
     */
    public String getDisplayName();

    /**
     * Check if the attached {@link org.bukkit.entity.LivingEntity} is still
     * valid and not null
     * 
     * @return true if the LivingEntity is not null
     */
    public boolean isValid();

    /**
     * Check if the {@link org.bukkit.entity.Entity#isValid()}. This also
     * checks if the reference of the entity {@link #isValid()}.
     * 
     * @return true if the LivingEntity is alive and valid
     */
    public boolean isEntityValid();

    /**
     * Returns the Entity if the Entity {@link #isEntityValid()}.
     * 
     * @return the linked Entity if not null
     */
    public Entity getEntity();

    /**
     * Gets the type of Entity this IEntity is wrapping.
     *
     * @return The type of entity this IEntity is wrapping
     */
    public EntityType getEntityType();

    /**
     * Reset the linked Entity to the provided
     * {@link org.bukkit.entity.Entity} if the {@link java.util.UUID} match
     * for the old reference and the provided reference
     * 
     * @param entity the Entity to re-attach this IEntity to
     * @return true if successful, false if UUID did not match
     * @throws IllegalArgumentException If the entity is null
     */
    public boolean setEntity(Entity entity);

    /**
     * Returns the linked Entity's UUID provided by the server.
     * 
     * @return the linked UUID to this entity.
     */
    public UUID getUniqueID();

    /**
     * Return the {@link org.bukkit.Location} of this being.
     * 
     * @return the location of the being.
     */
    public Location getLocation();

    /**
     * Gets a list of entities within a bounding box centered around this
     * IEntity.
     *
     * @param x Half the size of the box along the x axis
     * @param y Half the size of the box along the y axis
     * @param z Half the size of the box along the z axis
     * @return The List of entities nearby
     */
    public List<Entity> getNearbyEntities(double x, double y, double z);

    /**
     * Shortcut method for {@link #getLocation()#getWorld()}
     * 
     * @return the world of this being.
     */
    public World getWorld();

    /**
     * Check if this being is on solid ground (not flying or jumping) in the
     * air
     * 
     * @return true if the being is not in the air
     */
    public boolean isOnGround();

}

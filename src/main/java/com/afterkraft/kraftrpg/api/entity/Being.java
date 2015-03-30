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

import java.util.Collection;
import java.util.UUID;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.service.persistence.data.DataHolder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.entity.component.Component;

/**
 * Represents an {@link Entity} linked with KraftRPG. This is the base for all entities in
 * KraftRPG.
 */
public interface Being extends DataHolder {

    /**
     * Get the normalized name of this Entity
     *
     * @return The normalized name of this Entity
     */
    Text getName();

    /**
     * Get the cleansed name for this Entity. It may be a customized name.
     *
     * @return The customized display name for this Entity
     */
    Text getDisplayName();

    /**
     * Check if the attached entity is still valid and not null
     *
     * @return true if the entity is not null
     */
    boolean isValid();

    /**
     * Returns the Entity if the Entity {@link #isValid()}.
     *
     * @return the linked Entity if not null
     */
    Optional<? extends Entity> getEntity();

    /**
     * Gets the type of Entity this IEntity is wrapping.
     *
     * @return The type of entity this IEntity is wrapping
     */
    Optional<EntityType> getEntityType();

    /**
     * Returns the linked Entity's UUID provided by the server.
     *
     * @return the linked UUID to this entity.
     */
    UUID getUniqueID();

    /**
     * Return the {@link Location} of this being.
     *
     * @return the location of the being.
     */
    Location getLocation();

    /**
     * Teleports this being to the provided Location. This uses the provided location's yaw and
     * pitch and may be different than this being's current yaw and pitch.
     *
     * @param location the location to teleport to
     */
    void teleport(Location location);

    /**
     * Attempts to teleport this being to the provided Location while maintaining this being's
     * current yaw and pitch if possible.
     *
     * @param location        the location to teleport to
     * @param keepYawAndPitch whether to maintain the yaw and pitch
     */
    void teleport(Location location, boolean keepYawAndPitch);

    /**
     * Gets a collection of entities within a bounding box centered around this IEntity.
     *
     * @param x Half the size of the box along the x axis
     * @param y Half the size of the box along the y axis
     * @param z Half the size of the box along the z axis
     *
     * @return A collection of entities nearby
     */
    Collection<Entity> getNearbyEntities(double x, double y, double z);

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

    /**
     * Gets the desired component for this {@link Being}. Components are a way to wrap various
     * information without specifying that information as available in the implementing objects.
     *
     * <p>Examples of components may include health, effects, roles, mana, and other resources.</p>
     *
     * @param clazz The component class
     * @param <T>   The type of component class
     *
     * @return The component, if available
     */
    <T extends Component<T>> Optional<T> getComponent(Class<T> clazz);


}

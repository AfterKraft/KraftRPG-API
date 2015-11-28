/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

/**
 * Represents an {@link Entity} linked with RpgCommon. This is the base for all entities in
 * RpgCommon.
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
    Location<World> getLocation();

    /**
     * Gets a {@link Collection} of {@link Being}s within a bounding box centered around this {@link
     * Being}.
     *
     * @param x Half the size of the box along the x axis
     * @param y Half the size of the box along the y axis
     * @param z Half the size of the box along the z axis
     *
     * @return A collection of beings nearby
     */
    Collection<Being> getNearbyBeings(double x, double y, double z);

    /**
     * Gets a {@link Collection} of {@link Being}s within a bounding box
     * centered around this {@link Being}.
     *
     * @param distanceVector The vector distance
     *
     * @return A collection of beings nearby
     */
    Collection<Being> getNearbyBeings(Vector3d distanceVector);

    /**
     * Gets a {@link Collection} of {@link Being}s within a bounding box
     * centered aroudn this {@link Being} such that each one satisfies the
     * provided {@link Predicate}. If the {@link Predicate#test(Object)}
     * returns <code>true</code>, the {@link Being} is included.
     *
     * @param distanceVector The distance vector
     * @param predicate The predicate
     * @return The collection of nearby beings
     */
    Collection<Being> getNearbyBeings(Vector3d distanceVector, Predicate<Being> predicate);

    /**
     * Shortcut method for getting the entity and then the world.
     *
     * @return The world of this being.
     */
    World getWorld();

}

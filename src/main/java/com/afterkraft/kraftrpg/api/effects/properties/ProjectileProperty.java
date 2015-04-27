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
package com.afterkraft.kraftrpg.api.effects.properties;

import java.util.Set;

import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.world.Location;

import com.afterkraft.kraftrpg.api.effects.Effect;
import com.afterkraft.kraftrpg.api.effects.EffectOperation;
import com.afterkraft.kraftrpg.api.effects.EffectOperationResult;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents a property that has operations to perform when the {@link Insentient} is launching a
 * projectile, the projectile has landed, and/or when the projectile has damaged another {@link
 * Insentient} being.
 */
public interface ProjectileProperty {

    /**
     * Gets the number of shots remaining on this {@link ProjectileProperty} effect.
     *
     * @return The amount of shots left
     */
    int getShotsLeft();

    /**
     * Gets all applicable operations that are performed on the {@link Insentient} being with this
     * effect and the projectile being launched.
     *
     * <p>Operations may include periodic tasks to display particles from the projectile's current
     * location, change of velocity, and or removal from the world.</p>
     *
     * @return An unmodifiable set of operations to perform on launching the projectile
     */
    Set<ProjectileApplyOperation> getOperationsOnLaunch();

    /**
     * A simple callback method for when the imbued projectile has hit the ground or another
     * Entity.
     *
     * @return An unmodifiable set of operations to perform when the projectile alnds on an
     * insentient being
     */
    Set<ProjectileLandOperation> getLandOperations();

    /**
     * A simple callback method for when the imbued projectile has hit an {@link Insentient} being.
     * Typically this should be when the imbued projectile should add a new {@link Effect}.
     *
     * @return An unmodifiable set of operations to perform when the projectile lands on an
     * insentient being
     */
    Set<ProjectileDamageOperation> getDamageOperations();

    /**
     * Performs an operation on a projectil launched by an {@link Insentient} being with a {@link
     * ProjectileProperty} effect. Common operations may include: periodic particle display,
     * applicable effects applied to the shooter on launch, etc.
     */
    interface ProjectileApplyOperation extends EffectOperation {

        /**
         * Performs an operation on both the shooter and projectile at the moment the projectile is
         * launched. This assumes that the projectile has a predetermined velocity and position,
         * while the shooter has an active {@link ProjectileProperty} effect.
         *
         * @param shooter    The shooter shooting the projectile
         * @param projectile The projectile
         *
         * @return The operation result
         */
        EffectOperationResult apply(Insentient shooter, Projectile projectile);

    }

    /**
     * Represents an operation to be performed when a {@link Projectile}, shot by an {@link
     * Insentient} who was affected by a {@link ProjectileProperty} effect, has landed on another
     * {@link Insentient} being.
     */
    interface ProjectileDamageOperation extends EffectOperation {

        /**
         * Performs an operation when the {@link Projectile} has hit the specified {@link
         * Insentient} being. This operation does not get used when the projectile has landed
         * without hitting another insentient being, but rather when it has landed on an entity.
         *
         * @param victim     The victim that was hit by the projectile
         * @param projectile The projectile that was shot with a {@link ProjectileProperty} affected
         *                   insentient being
         *
         * @return The operation result
         */
        EffectOperationResult onDamage(Insentient victim, Projectile projectile);
    }

    /**
     * Represents an operation to be performed when a {@link Projectile}, shot by an {@link
     * Insentient} who was affected by a {@link ProjectileProperty} effect, has landed on ground.
     */
    interface ProjectileLandOperation extends EffectOperation {

        /**
         * Performs an operation when the {@link Projectile} has landed at the specified {@link
         * Location}. This operation does not get used when the projectile has damaged another
         * {@link Insentient} being, but rather when it has landed without harming any entity.
         *
         * @param projectile The projectile that was shot with a {@link ProjectileProperty} affected
         *                   insentient being
         * @param location   The landing location of the projectile
         *
         * @return The operation result
         */
        EffectOperationResult onLand(Projectile projectile, Location location);
    }
}

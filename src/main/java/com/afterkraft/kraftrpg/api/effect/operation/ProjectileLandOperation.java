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
package com.afterkraft.kraftrpg.api.effect.operation;

import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.world.Location;

import com.afterkraft.kraftrpg.api.effect.EffectOperation;
import com.afterkraft.kraftrpg.api.effect.EffectOperationResult;
import com.afterkraft.kraftrpg.api.effect.property.ProjectileProperty;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents an operation to be performed when a {@link Projectile}, shot by an {@link
 * Insentient} who was affected by a {@link ProjectileProperty} effect, has landed on ground.
 */
@FunctionalInterface
public interface ProjectileLandOperation extends EffectOperation {

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

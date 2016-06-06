/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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

import com.afterkraft.kraftrpg.api.effect.EffectOperation;
import com.afterkraft.kraftrpg.api.effect.EffectOperationResult;
import com.afterkraft.kraftrpg.api.effect.property.ProjectileProperty;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Performs an operation on a projectil launched by an {@link Insentient} being with a {@link
 * ProjectileProperty} effect. Common operations may include: periodic particle display,
 * applicable effects applied to the shooter on launch, etc.
 */
@FunctionalInterface
public interface ProjectileApplyOperation extends EffectOperation {

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

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
package com.afterkraft.kraftrpg.api.effect.property;

import java.util.Set;

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.api.effect.operation.ProjectileApplyOperation;
import com.afterkraft.kraftrpg.api.effect.operation.ProjectileDamageOperation;
import com.afterkraft.kraftrpg.api.effect.operation.ProjectileLandOperation;
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

}

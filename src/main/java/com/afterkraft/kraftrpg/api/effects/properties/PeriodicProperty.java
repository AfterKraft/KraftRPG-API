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

import com.afterkraft.kraftrpg.api.effects.Effect;
import com.afterkraft.kraftrpg.api.effects.EffectOperation;
import com.afterkraft.kraftrpg.api.effects.EffectOperationResult;
import com.afterkraft.kraftrpg.api.effects.EffectProperty;
import com.afterkraft.kraftrpg.api.entity.Insentient;

public interface PeriodicProperty extends EffectProperty<PeriodicProperty> {

    /**
     * Returns the last time the effect ticked.
     *
     * @return The time in milliseconds the insentient being was ticked on
     */
    long getLastTickTime();

    /**
     * Gets the period of time between "ticks" on an insentient being. After the {@link
     * #getLastTickTime} increased by the defined period has exceeded the current time, all {@link
     * TickEffectOperation}s as defined by {@link #getTickOperations} will execute on the {@link
     * Insentient} being this effect is applied to.
     *
     * @return The period of time between "ticks"
     */
    long getPeriod();

    /**
     * Returns whether the effect is ready for ticking
     *
     * @return - The ready state of this effect. DO NOT CALL IF THIS IS FALSE
     */
    boolean isReady();

    /**
     * Gets an unmodifiable set of operations to perform after a period of time has passed on an
     * {@link Insentient} being.
     *
     * @return An unmodifiable set of operations to perform on an insentient being periodically
     */
    Set<TickEffectOperation> getTickOperations();

    /**
     * Represents a ticked operation performed on an {@link Insentient} being for a recurring
     * effect. The operation may do whatever is necessary.
     */
    interface TickEffectOperation extends EffectOperation {

        /**
         * Ticks on the insentient being. This will perform an action on the insentient being after
         * the parent {@link Effect} effect has reached a period to "tick" this operation.
         *
         * <p>If for any reason the operation {@link EffectOperationResult#FAIL}s, the operation
         * will attempt to tick after the next period. If the operation result is {@link
         * EffectOperationResult#INVALID}, the effect may be forcibly removed.</p>
         *
         * @param being The insentient being that is being acted upon
         *
         * @return The result of the operation.
         */
        EffectOperationResult tick(Insentient being);
    }
}

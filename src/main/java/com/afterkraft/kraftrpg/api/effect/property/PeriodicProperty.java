/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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

import com.afterkraft.kraftrpg.api.effect.EffectProperty;
import com.afterkraft.kraftrpg.api.effect.operation.TickEffectOperation;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents an {@link EffectProperty} that has a {@link TickEffectOperation} that periodically
 * ticks with the frequency of {@link PeriodicProperty#getPeriod()} ticks.
 */
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

}

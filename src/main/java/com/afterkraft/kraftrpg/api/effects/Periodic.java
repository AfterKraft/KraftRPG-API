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
package com.afterkraft.kraftrpg.api.effects;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents an effect that will perform some action on the {@link com.afterkraft.kraftrpg.api.entity.Insentient}
 * per tick as defined by {@link #tick(com.afterkraft.kraftrpg.api.entity.Insentient)}
 */
public interface Periodic extends IEffect, Timed {

    /**
     * Returns the last time the effect ticked
     *
     * @return the time in milliseconds this effect was last ticked
     */
    public long getLastTickTime();

    /**
     * @return the duration of this effect
     */
    public long getPeriod();

    /**
     * Returns whether the effect is ready for ticking
     *
     * @return - The ready state of this effect. DO NOT CALL IF THIS IS FALSE
     */
    public boolean isReady();

    /**
     * Ticks this effect on the specified entity.
     *
     * @param being - The being this effect is being applied to
     *
     * @throws IllegalArgumentException If the being is null
     */
    public void tick(Insentient being);

}

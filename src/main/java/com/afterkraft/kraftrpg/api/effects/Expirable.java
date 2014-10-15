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
 * An effect that will expire after a certain amount of time passing by.
 */
public interface Expirable extends IEffect, Timed {

    /**
     * Fetch the duration of this Expirable Effect.
     *
     * @return the duration in milliseconds
     */
    public long getDuration();

    /**
     * Fetch the estimated Expire time in milliseconds
     *
     * @return the expire time in milliseconds.
     */
    public long getExpiry();

    /**
     * Fetch the estimated remaining time of this Effect
     *
     * @return the estimated remaining time in milliseconds
     */
    public long getRemainingTime();

    /**
     * Check if this Effect is expired
     *
     * @return true if this Effect is expired
     */
    public boolean isExpired();

    /**
     * Manually expire this Effect. This will set the expire time to the current System time.
     */
    public void expire();

    /**
     * Get the {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} applying this expirable
     * effect.
     *
     * @return the SkillCaster applying this effect
     */
    public Insentient getApplier();

    /**
     * A utility method to clear the applier of this effect.
     */
    public void clean();

}

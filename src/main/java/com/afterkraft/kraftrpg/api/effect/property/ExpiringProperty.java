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
package com.afterkraft.kraftrpg.api.effect.property;

/**
 * Represents a {@link TimedProperty} that will expire after the {@link #getDuration()} has passed
 * since the application time.
 */
public interface ExpiringProperty extends TimedProperty<ExpiringProperty> {

    /**
     * Fetch the duration of this Expirable Effect.
     *
     * @return the duration in milliseconds
     */
    long getDuration();

    /**
     * Fetch the estimated Expire time in milliseconds
     *
     * @return the expire time in milliseconds.
     */
    long getExpiry();

    /**
     * Fetch the estimated remaining time of this Effect
     *
     * @return the estimated remaining time in milliseconds
     */
    long getRemainingTime();

    /**
     * Check if this Effect is expired
     *
     * @return true if this Effect is expired
     */
    boolean isExpired();

    /**
     * Manually expire this Effect. This will set the expire time to the current System time.
     */
    void expire();

}

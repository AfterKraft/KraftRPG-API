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
package com.afterkraft.kraftrpg.api.effect;

/**
 * Represents the result of an operation from an effect applying onto an insentient being, effect
 * being removed, or simply an effect ticking on an entity.
 */
public enum EffectOperationResult {

    /**
     * If the application succeeded. The effect will continue to persist on the entity until death
     * or manual removal.
     */
    SUCCESS,

    /**
     * If the application is cancelled for expected reasons. The effect will continue to persist,
     * however any operations are considered "cancelled". Any counters that are affected by a
     * {@link #SUCCESS} are not affected unless required to do so.
     */
    CANCELLED,

    /**
     * If the application failed for any reason. If the application failed, the effect is removed
     * from the insentient being and will not persist.
     */
    FAIL,

    /**
     * Something went terribly wrong. The effect probably threw an error or is not applicable.
     */
    INVALID
}

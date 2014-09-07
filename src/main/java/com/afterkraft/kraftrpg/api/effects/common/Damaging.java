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
package com.afterkraft.kraftrpg.api.effects.common;

import com.afterkraft.kraftrpg.api.effects.Periodic;

/**
 * A damaging effect that will damage the
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being.
 */
public interface Damaging extends Periodic {

    /**
     * Return the damage per tick for this effect.
     *
     * @return damage per tick
     */
    public double getTickDamage();

    /**
     * Set the damage per tick for this effect.
     *
     * @param tickDamage for this effect.
     * @throws IllegalArgumentException If the damage is negative or zero
     */
    public void setTickDamage(double tickDamage);

    /**
     * Return whether this effect knocks back the affected
     *
     * @return true if this effect will knock back on damage
     */
    public boolean isKnockback();

}
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

import org.bukkit.Location;
import org.bukkit.entity.Projectile;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents a basic imbuing effect that has specific behaviors to apply to a
 * projectile launched by the affected {@link Insentient} being.
 */
public interface ProjectileShot extends Imbuing {

    public int getShotsLeft();

    /**
     * Applies the desired effect to the given Arrow.
     *
     * @param projectile@return True if the arrow application was successful
     * @throws IllegalArgumentException If the arrow is null or not valid
     */
    public boolean applyToProjectile(Projectile projectile);

    /**
     * A simple callback method for when the imbued projectile has hit the
     * ground or another Entity.
     *
     * @param projectile The projectile that is landing
     * @param location   The location of the projectile's landing point
     */
    public void onProjectileLand(Projectile projectile, Location location);

    /**
     * A simple callback method for when the imbued projectile has hit an
     * {@link Insentient} being. Typically this should be when the imbued
     * projectile should add a new {@link com.afterkraft.kraftrpg.api.effects.IEffect}.
     *
     * @param projectile The projectile that is damaging the victim
     * @param victim     The victim being damaged
     */
    public void onProjectileDamage(Projectile projectile, Insentient victim);
}

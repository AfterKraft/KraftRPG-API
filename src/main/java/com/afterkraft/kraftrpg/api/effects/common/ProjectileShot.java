/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

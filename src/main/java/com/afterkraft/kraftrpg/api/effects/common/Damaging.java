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

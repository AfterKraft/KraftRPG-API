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

package com.afterkraft.kraftrpg.api.entity.component;

import org.spongepowered.api.entity.living.Living;

public interface HealthComponent extends Component<HealthComponent> {

    /**
     * Return the health of the being.
     *
     * @return health
     */
    double getHealth();

    /**
     * Attempts to set the health of the being. If the being is dead, the health will not be
     * updated.
     *
     * @param health to which the being should have.
     *
     * @throws IllegalArgumentException If health is negative
     */
    void setHealth(double health);

    /**
     * Return the max health for this being.
     *
     * @return maximum health
     */
    double getMaxHealth();

    /**
     * Add a fixed health bonus that does not get reset when {@link #recalculateMaxHealth()} is
     * called. The health bonus, unless provided specifically by KraftRPG, does not survive between
     * reloads, restarts, relogging and other times when the entity is stored and loaded back into
     * memory.
     *
     * @param key   id based on string to apply the max health
     * @param value of health to add as the maximum
     *
     * @return true if successful, if the key did not exist before.
     * @throws IllegalArgumentException If the key is null or empty
     */
    boolean addMaxHealth(String key, double value);

    /**
     * Removes an additional health modifier from the calculations for the {@link
     * Living#getMaxHealth()}. Removing KraftRPG specific mappings may have unknown side-effects.
     *
     * @param key linking to the additional health bonus for this being
     *
     * @return true if successful, false if the mapping didn't exist.
     * @throws IllegalArgumentException If the key is null
     */
    boolean removeMaxHealth(String key);

    /**
     * Forcefully recalculates the total max health the linked {@link Living} should have and
     * applies it. This will also apply the current percentage of total health the entity has
     * compared to their current max health.
     *
     * @return the newly recalculated max health
     */
    double recalculateMaxHealth();

    /**
     * Attempts to heal the {@link Living} the defined amount of health. This will not allow healing
     * past the current max health
     *
     * @param amount to heal
     */
    void heal(double amount);

    /**
     * Clears out all health bonuses to the max health except the necessary provided by the default
     * implementation of KraftRPG.
     */
    void clearHealthBonuses();
}

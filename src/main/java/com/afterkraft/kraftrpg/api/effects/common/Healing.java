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
 * Represents a healing effect that heals the affected
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient}
 */
public interface Healing extends Periodic {

    /**
     * Return the amount of health being healed to the insentient being this
     * effect is applied to
     *
     * @return the health to heal per tick
     */
    public double getTickHealth();

    /**
     * Set the health to heal per tick
     *
     * @param tickHealth health to heal per tick
     * @throws IllegalArgumentException If the health is negative
     */
    public void setTickHealth(double tickHealth);
}

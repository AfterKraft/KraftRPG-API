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
package com.afterkraft.kraftrpg.api.entity;

import org.bukkit.Location;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Represents a customized
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being that has the
 * ability to deal damage to other
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} beings. A monster is
 * guaranteed to have modified damage.
 */
public interface Monster extends Insentient {

    public Location getSpawnLocation();

    public double getBaseDamage();

    public double getModifiedDamage();

    /**
     * Set the modified damage this Monster will deal to other Insentient
     * beings when attacking.
     *
     * @param damage To deal when attacking
     * @throws IllegalArgumentException If the damage is less than zero
     */
    public void setModifiedDamage(double damage);

    public CreatureSpawnEvent.SpawnReason getSpawnReason();
}

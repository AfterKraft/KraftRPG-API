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

import org.bukkit.entity.LivingEntity;

/**
 * Represents a living entity in KraftRPG. This is the base entity for
 * KraftRPG.
 */
public interface IEntity {

    /**
     * Get the Bukkit name of this Entity
     *
     * @return the Bukkit name of this Entity
     */
    public String getName();

    /**
     * Check if the attached {@link org.bukkit.entity.LivingEntity} is still
     * valid and not null
     *
     * @return true if the LivingEntity is not null
     */
    public boolean isValid();

    /**
     * Check if the {@link org.bukkit.entity.LivingEntity#isValid()}. This also
     * checks if the reference of the entity {@link #isValid()}.
     *
     * @return true if the LivingEntity is alive and valid
     */
    public boolean isEntityValid();

    /**
     * Returns the LivingEntity if the LivingEntity {@link #isEntityValid()}.
     *
     * @return the linked LivingEntity if not null
     */
    public LivingEntity getEntity();

    /**
     * Reset the linked LivingEntity to the provided {@link
     * org.bukkit.entity.LivingEntity} if the {@link java.util.UUID} match for
     * the old reference and the provided reference
     *
     * @param entity the LivingEntity to re-attach this IEntity to
     * @return true if successful, false if UUID did not match
     */
    public boolean setEntity(LivingEntity entity);

    public boolean addMaxHealth(String key, double value);

    public boolean removeMaxHealth(String key);

    public double recalculateMaxHealth();

    public void heal(double amount);

    public void clearHealthBonuses();

}

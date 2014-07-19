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
package com.afterkraft.kraftrpg.api.entity.effects;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.ISkill;

/**
 * The base of KraftPRG's Effect system. An effect can perform various
 * operations on an {@link com.afterkraft.kraftrpg.api.entity.Insentient}
 * being. While it is recommended to utilize the
 * {@link com.afterkraft.kraftrpg.api.entity.effects.IEffect} as the standard
 * implementation, various other interfaces have different implementations.
 */
public interface IEffect {

    /**
     * Returns the associated
     * {@link com.afterkraft.kraftrpg.api.skills.ISkill} that created this
     * effect.
     * 
     * @return the Skill that created this effect
     */
    public ISkill getSkill();

    /**
     * Returns this individual Effect's name. (Should be as unique and
     * recognizable as possible).
     * 
     * @return the name of this effect.
     */
    public String getName();

    /**
     * Check if this Effect is of a certain EffectType
     * 
     * @param queryType The type of effect to query
     * @return True if this Effect is of the queried EffectType
     * @throws IllegalArgumentException If the query type is null
     */
    public boolean isType(EffectType queryType);

    /**
     * Check if this Effect is persistent. A Persistent effect will never
     * expire until the Effect is removed.
     * 
     * @return true if this Effect is persistent
     */
    public boolean isPersistent();

    /**
     * Set this effect to be a persisting effect. Persistent effects will
     * never expire until removed by a Skill or plugin.
     * 
     * @param persistent set this Effect to be persistent.
     */
    public void setPersistent(boolean persistent);

    /**
     * Add a Bukkit {@link org.bukkit.potion.PotionEffect} to this Effect
     * 
     * @param pEffect The PotionEffect to add to this Effect
     * @throws IllegalArgumentException If the potion effect is null
     */
    public void addPotionEffect(PotionEffect pEffect);

    // ----
    // Application Methods
    // ----

    /**
     * @return the applyTime
     */
    public long getApplyTime();

    /**
     * Attempts to apply this effect to the provided
     * {@link com.afterkraft.kraftrpg.api.entity.Insentient}.
     * 
     * @param being this effect is being applied on to.
     * @throws IllegalArgumentException If the being is null or invalid
     */
    public void apply(Insentient being);

    /**
     * Attempts to remove this effect from the given Insentient being
     *
     * @param being this effect is being removed by.
     * @throws IllegalArgumentException If the being is null or invalid
     */
    public void remove(Insentient being);
}

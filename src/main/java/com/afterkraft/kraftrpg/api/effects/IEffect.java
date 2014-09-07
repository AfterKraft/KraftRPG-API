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
package com.afterkraft.kraftrpg.api.effects;

import java.util.Set;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.ISkill;

/**
 * The base of KraftPRG's Effect system. An effect can perform various
 * operations on an {@link com.afterkraft.kraftrpg.api.entity.Insentient}
 * being. While it is recommended to utilize the
 * {@link com.afterkraft.kraftrpg.api.effects.IEffect} as the standard
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
     * Gets an immutable set of the potion effects that are applied when
     * {@link #apply(Insentient)} is called.
     *
     * @return An immutable set of potion effects
     */
    public Set<PotionEffect> getPotionEffects();

    /**
     * Check if this Effect is persistent. A Persistent effect will never
     * expire until the Effect is removed.
     *
     * @return true if this Effect is persistent
     */
    public boolean isPersistent();

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

    /**
     * Get the message that should be sent to players when this effect is
     * applied
     *
     * @return the message when this effect is applied
     */
    String getApplyText();

    /**
     * Get the message that should be sent to players when this effect expires
     *
     * @return the message when this effect expires
     */
    String getExpireText();
}
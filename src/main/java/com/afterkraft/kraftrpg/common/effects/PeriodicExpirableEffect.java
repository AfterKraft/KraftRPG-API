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
package com.afterkraft.kraftrpg.common.effects;

import java.util.Collection;
import java.util.Set;

import org.spongepowered.api.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.Expirable;
import com.afterkraft.kraftrpg.api.effects.Periodic;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.common.skills.Skill;

/**
 * Standard implementation of a {@link com.afterkraft.kraftrpg.api.effects.Periodic} and {@link
 * Expirable}. This effect will expire after the designated time by {@link #getExpiry()}
 */
public abstract class PeriodicExpirableEffect extends ExpirableEffect implements
        Periodic {

    private final long period;
    protected long lastTickTime = 0;

    protected PeriodicExpirableEffect(Skill skill, Insentient applier, String name,
                                      long duration, Collection<EffectType> types, long period) {
        super(skill, applier, name, duration, types);
        this.period = period;
    }

    protected PeriodicExpirableEffect(Skill skill, Insentient applier, String name, long duration,
                                      String applyText, String expireText,
                                      Collection<EffectType> types, long period) {
        super(skill, applier, name, duration, applyText, expireText, types);
        this.period = period;
    }

    protected PeriodicExpirableEffect(Skill skill, Insentient applier, String name,
                                      Set<PotionEffect> potionEffects, boolean persistent,
                                      Collection<EffectType> types, String applyText,
                                      String expireText, long duration, long period) {
        super(skill, applier, name, potionEffects, persistent, types, applyText,
                expireText, duration);
        this.period = period;
    }

    @Override
    public long getLastTickTime() {
        return this.lastTickTime;
    }

    @Override
    public long getPeriod() {
        return this.period;
    }

    @Override
    public boolean isReady() {
        return System.currentTimeMillis() >= (this.lastTickTime + this.period);
    }
}

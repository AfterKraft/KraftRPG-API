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
package com.afterkraft.kraftrpg.api.effects;

import java.util.Collection;
import java.util.Set;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Standard implementation of a
 * {@link com.afterkraft.kraftrpg.api.effects.Periodic} and
 * {@link Expirable}. This effect
 * will expire after the designated time by {@link #getExpiry()}
 */
public abstract class PeriodicExpirableEffect extends ExpirableEffect implements Periodic {

    private final long period;
    protected long lastTickTime = 0;

    protected PeriodicExpirableEffect(Skill skill, Insentient applier, String name, long duration, Collection<EffectType> types, long period) {
        super(skill, applier, name, duration, types);
        this.period = period;
    }

    protected PeriodicExpirableEffect(Skill skill, Insentient applier, String name, long duration, String applyText, String expireText, Collection<EffectType> types, long period) {
        super(skill, applier, name, duration, applyText, expireText, types);
        this.period = period;
    }

    protected PeriodicExpirableEffect(Skill skill, Insentient applier, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types, String applyText, String expireText, long duration, long period) {
        super(skill, applier, name, potionEffects, persistent, types, applyText, expireText, duration);
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

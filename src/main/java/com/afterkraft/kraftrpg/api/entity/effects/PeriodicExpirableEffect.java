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

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Standard implementation of a
 * {@link com.afterkraft.kraftrpg.api.entity.effects.Periodic} and
 * {@link Expirable}. This effect
 * will expire after the designated time by {@link #getExpiry()}
 */
public abstract class PeriodicExpirableEffect extends ExpirableEffect implements Periodic {

    private final long period;
    protected long lastTickTime = 0;

    public PeriodicExpirableEffect(Skill skill, SkillCaster applier, String name, long period, long duration, EffectType... types) {
        this(skill, skill.plugin, applier, name, period, duration, null, null, types);
    }

    public PeriodicExpirableEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long period, long duration, EffectType... types) {
        this(skill, plugin, applier, name, period, duration, null, null, types);
    }

    public PeriodicExpirableEffect(Skill skill, SkillCaster applier, String name, long period, long duration, String applyText, String expireText, EffectType... types) {
        this(skill, skill.plugin, applier, name, period, duration, applyText, expireText, types);
    }

    public PeriodicExpirableEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long period, long duration, String applyText, String expireText, EffectType... types) {
        super(skill, plugin, applier, name, duration, applyText, expireText, types);
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

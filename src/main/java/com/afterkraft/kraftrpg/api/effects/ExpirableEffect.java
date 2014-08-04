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
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.Validate;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Implementation of an
 * {@link Expirable} based on
 * {@link com.afterkraft.kraftrpg.api.effects.Effect}
 */
public class ExpirableEffect extends Effect implements Expirable {

    private final long duration;
    protected final Insentient applier;
    private long expireTime;

    public ExpirableEffect(Skill skill, Insentient applier, String name, long duration, Collection<EffectType> types) {
        this(skill, applier, name, duration, null, null, types);
    }

    public ExpirableEffect(Skill skill, Insentient applier, String name, long duration, String applyText, String expireText, Collection<EffectType> types) {
        this(skill, applier, name, null, false, types, applyText, expireText, duration);
    }

    public ExpirableEffect(Skill skill, Insentient applier, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types, String applyText, String expireText, long duration) {
        super(skill, name, potionEffects, persistent, types, applyText, expireText);
        Validate.notNull(applier, "Cannot create an ExpirableEffect with a null applier!");
        Validate.isTrue(duration > 0, "Cannot have a negative Effect duration!");
        this.applier = applier;
        this.duration = duration;
    }

    @Override
    public void apply(Insentient being) {
        super.apply(being);
        this.expireTime = this.applyTime + this.duration;
    }

    @Override
    public long getDuration() {
        return this.duration;
    }

    @Override
    public long getExpiry() {
        return this.expireTime;
    }

    @Override
    public long getRemainingTime() {
        return this.expireTime - System.currentTimeMillis();
    }

    @Override
    public boolean isExpired() {
        return !this.isPersistent() && System.currentTimeMillis() >= this.getExpiry();
    }

    @Override
    public void expire() {
        this.expireTime = System.currentTimeMillis();
    }

    @Override
    public Insentient getApplier() {
        return this.applier;
    }

    @Override
    public int compareTo(Delayed other) {
        long d = (getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS));
        return ((d == 0) ? 0 : ((d < 0) ? -1 : 1));
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
}

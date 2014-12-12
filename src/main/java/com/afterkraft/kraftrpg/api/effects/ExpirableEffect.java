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

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

import org.spongepowered.api.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Implementation of an {@link Expirable} based on {@link com.afterkraft.kraftrpg.api.effects.Effect}
 */
public class ExpirableEffect extends Effect implements Expirable {

    private final long duration;
    protected Insentient applier;
    private long expireTime;

    public ExpirableEffect(Skill skill, Insentient applier, String name, long duration,
                           Collection<EffectType> types) {
        this(skill, applier, name, duration, null, null, types);
    }

    public ExpirableEffect(Skill skill, Insentient applier, String name, long duration,
                           String applyText, String expireText, Collection<EffectType> types) {
        this(skill, applier, name, null, false, types, applyText, expireText, duration);
    }

    public ExpirableEffect(Skill skill, Insentient applier, String name,
                           Set<PotionEffect> potionEffects, boolean persistent,
                           Collection<EffectType> types, String applyText, String expireText,
                           long duration) {
        super(skill, name, potionEffects, persistent, types, applyText, expireText);
        checkArgument(applier != null, "Cannot create an ExpirableEffect with a null applier!");
        checkArgument(duration > 0, "Cannot have a negative Effect duration!");
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
    public void clean() {
        this.applier = null;
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

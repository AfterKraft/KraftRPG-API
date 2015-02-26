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

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.Messages;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.Expirable;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.common.skills.Skill;

/**
 * Implementation of an {@link Expirable} based on {@link Effect}
 * <p>Utilizing an ExpirableEffect alone will not have any custom behaviors.
 * If a custom behavior is desired, extending this class is recommended and
 * customized to perform custom behaviors.
 * </p>
 */
public class ExpirableEffect extends Effect implements Expirable {

    private final long duration;
    @Nullable
    protected Insentient applier;
    private long expireTime;

    /**
     * Creates a new expiring effect.
     *
     * @param skill The skill granting this effect
     * @param applier The being that this effect is being applied to
     * @param name The name of this effect
     * @param duration The duration in milliseconds
     * @param types The types of this effect
     */
    public ExpirableEffect(Skill skill, Insentient applier, String name, long duration,
                           Collection<EffectType> types) {
        this(skill, applier, name, duration, Messages.of(""), Messages.of(""), types);
    }

    /**
     * Creates a new expiring effect.
     *
     * @param skill The skill granting this effect
     * @param applier The being that this effect is being applied to
     * @param name The name of this effect
     * @param duration The duration in milliseconds
     * @param applyText The text to display when this effect is applied
     * @param expireText The text to display when this effect expires
     * @param types The types of this effect
     */
    public ExpirableEffect(Skill skill, Insentient applier, String name, long duration,
                           Message applyText, Message expireText, Collection<EffectType> types) {
        this(skill, applier, name, Sets.<PotionEffect>newHashSet(), false, types,
             applyText,
             expireText, duration);
    }

    /**
     * Creates a new expiring effect.
     *
     * @param skill The skill granting this effect
     * @param applier The being that this effect is being applied to
     * @param name The name of this effect
     * @param potionEffects The potion effects to add to the being
     * @param persistent Whether this effect persists
     * @param duration The duration in milliseconds
     * @param applyText The text to display when this effect is applied
     * @param expireText The text to display when this effect expires
     * @param types The types of this effect
     */
    public ExpirableEffect(Skill skill, Insentient applier, String name,
                           Set<PotionEffect> potionEffects, boolean persistent,
                           Collection<EffectType> types, Message applyText, Message expireText,
                           long duration) {
        super(skill, name, potionEffects, persistent, types, applyText, expireText);
        checkArgument(applier.isEntityValid(), "Cannot create an ExpirableEffect with a "
                + "null applier!");
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
    public Optional<Insentient> getApplier() {
        return Optional.fromNullable(this.applier);
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

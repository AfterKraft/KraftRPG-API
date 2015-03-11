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
package com.afterkraft.kraftrpg.common.effects.common;

import java.util.EnumSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.Messages;

import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.common.Healing;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.common.effects.PeriodicExpirableEffect;
import com.afterkraft.kraftrpg.common.skills.AbstractSkill;

/**
 * Standard implementation of a {@link com.afterkraft.kraftrpg.api.effects.Periodic} and {@link
 * Healing}. Consider that this effect will heal the {@link com.afterkraft.kraftrpg.api.entity.Insentient}
 * being every tick based on {@link #getTickHealth()}
 */
public class HealingEffect extends PeriodicExpirableEffect implements Healing {
    private double tickHealth;

    public HealingEffect(AbstractSkill skill, Insentient applier, String name,
                         long duration,
                         EnumSet<EffectType> types, long period,
                         double tickHealth) {
        this(skill, applier, name, Sets.<PotionEffect>newHashSet(), false,
             types, Messages.of(""), Messages.of(""),
             duration, period, tickHealth);
    }

    public HealingEffect(AbstractSkill skill, Insentient applier, String name,
                         Set<PotionEffect> potionEffects, boolean persistent,
                         EnumSet<EffectType> types, Message applyText,
                         Message expireText,
                         long duration, long period, double tickHealth) {
        super(skill, applier, name, potionEffects, persistent, types, applyText,
              expireText,
              duration, period);
        this.tickHealth = tickHealth;
    }

    public HealingEffect(AbstractSkill skill, Insentient applier, String name,
                         long duration,
                         Message applyText, Message expireText,
                         EnumSet<EffectType> types,
                         long period, double tickHealth) {
        this(skill, applier, name, Sets.<PotionEffect>newHashSet(), false,
             types, applyText,
             expireText, duration, period, tickHealth);
    }

    @Override
    public double getTickHealth() {
        return this.tickHealth;
    }

    @Override
    public void setTickHealth(double tickHealth) {
        checkArgument(tickHealth > 0,
                      "Cannot set a negative/zero health per tick!");
        this.tickHealth = tickHealth;
    }

    /**
     * {@inheritDoc} This will heal the {@link com.afterkraft.kraftrpg.api.entity.Insentient} being
     * the prescribed health from {@link #getTickHealth()} ()}
     *
     * @param being - The being this effect is being applied to.
     */
    @Override
    public void tick(Insentient being) {
        if (!being.isEntityValid() || !getApplier().isPresent()) {
            return;
        }
        /*
        InsentientRegainHealthEvent event;
        if (being instanceof Champion) {
            event = new ChampionRegainHealthEvent((Champion) being, this.tickHealth,
                    this.skill, getApplier());

        } else {
            event = new InsentientRegainHealthEvent(being, this.tickHealth,
                    this.skill, getApplier());
        }
        this.skill.plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        being.heal(event.getAmount());
        */
    }
}

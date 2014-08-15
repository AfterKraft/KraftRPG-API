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
package com.afterkraft.kraftrpg.api.effects.common;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.PeriodicExpirableEffect;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.events.entity.InsentientRegainHealthEvent;
import com.afterkraft.kraftrpg.api.events.entity.champion.ChampionRegainHealthEvent;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Standard implementation of a
 * {@link com.afterkraft.kraftrpg.api.effects.Periodic} and
 * {@link Healing}. Consider that this
 * effect will heal the {@link com.afterkraft.kraftrpg.api.entity.Insentient}
 * being every tick based on {@link #getTickHealth()}
 */
public class HealingEffect extends PeriodicExpirableEffect implements Healing {
    private double tickHealth;

    public HealingEffect(Skill skill, Insentient applier, String name, long duration, EnumSet<EffectType> types, long period, double tickHealth) {
        this(skill, applier, name, null, false, types, "", "", duration, period, tickHealth);
    }

    public HealingEffect(Skill skill, Insentient applier, String name, long duration, String applyText, String expireText, EnumSet<EffectType> types, long period, double tickHealth) {
        this(skill, applier, name, null, false, types, applyText, expireText, duration, period, tickHealth);
    }

    public HealingEffect(Skill skill, Insentient applier, String name, Set<PotionEffect> potionEffects, boolean persistent, EnumSet<EffectType> types, String applyText, String expireText, long duration, long period, double tickHealth) {
        super(skill, applier, name, potionEffects, persistent, types, applyText, expireText, duration, period);
        this.tickHealth = tickHealth;
    }

    @Override
    public double getTickHealth() {
        return this.tickHealth;
    }

    @Override
    public void setTickHealth(double tickHealth) {
        Validate.isTrue(tickHealth > 0, "Cannot set a negative/zero health per tick!");
        this.tickHealth = tickHealth;
    }

    /**
     * {@inheritDoc} This will heal the
     * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being the
     * prescribed health from {@link #getTickHealth()} ()}
     *
     * @param being - The being this effect is being applied to.
     */
    @Override
    public void tick(Insentient being) {
        Validate.notNull(being, "Cannot tick on a null Insentient being!");
        if (!being.isEntityValid() || !getApplier().isEntityValid()) {
            return;
        }
        InsentientRegainHealthEvent event;
        if (being instanceof Champion) {
            event = new ChampionRegainHealthEvent((Champion) being, this.tickHealth, this.skill, getApplier());

        } else {
            event = new InsentientRegainHealthEvent(being, this.tickHealth, this.skill, getApplier());
        }
        this.skill.plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        being.heal(event.getAmount());
    }
}

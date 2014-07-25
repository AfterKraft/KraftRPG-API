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

import org.apache.commons.lang.Validate;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.entity.InsentientRegainHealthEvent;
import com.afterkraft.kraftrpg.api.events.entity.champion.ChampionRegainHealthEvent;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Standard implementation of a
 * {@link com.afterkraft.kraftrpg.api.entity.effects.Periodic} and
 * {@link com.afterkraft.kraftrpg.api.entity.effects.Heal}. Consider that this
 * effect will heal the {@link com.afterkraft.kraftrpg.api.entity.Insentient}
 * being every tick based on {@link #getTickHealth()}
 */
public class PeriodicHealingEffect extends PeriodicExpirableEffect implements Heal {
    private double tickHealth;

    public PeriodicHealingEffect(Skill skill, Champion applier, String name, long period, long duration, double tickHealth, EffectType... types) {
        this(skill, skill.plugin, applier, name, period, duration, tickHealth, "", "", types);
    }

    public PeriodicHealingEffect(Skill skill, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickHealth, String applyText, String expireText, EffectType... types) {
        super(skill, plugin, applier, name, period, duration, types);

        this.types.add(EffectType.BENEFICIAL);
        this.types.add(EffectType.HEALING);

        this.tickHealth = tickHealth;
    }

    public PeriodicHealingEffect(Skill skill, Champion applier, String name, long period, long duration, double tickHealth, String applyText, String expireText, EffectType... types) {
        this(skill, skill.plugin, applier, name, period, duration, tickHealth, applyText, expireText, types);
    }

    public PeriodicHealingEffect(Skill skill, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickHealth, EffectType... types) {
        this(skill, plugin, applier, name, period, duration, tickHealth, "", "", types);
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
        this.plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        being.heal(event.getAmount());
    }
}

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
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.entity.InsentientRegainHealthEvent;
import com.afterkraft.kraftrpg.api.events.entity.champion.ChampionRegainHealthEvent;
import com.afterkraft.kraftrpg.api.skills.Skill;


public class PeriodicHealingEffect extends PeriodicExpirableEffect implements Heal {
    private double tickHealth;

    public PeriodicHealingEffect(Skill skill, Champion applier, String name, long period, long duration, double tickHealth) {
        this(skill, null, applier, name, period, duration, tickHealth, null, null);
    }

    public PeriodicHealingEffect(Skill skill, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickHealth, String applyText, String expireText) {
        super(skill, plugin, applier, name, period, duration);

        types.add(EffectType.BENEFICIAL);
        types.add(EffectType.HEALING);

        this.tickHealth = tickHealth;
    }

    public PeriodicHealingEffect(Skill skill, Champion applier, String name, long period, long duration, double tickHealth, String applyText, String expireText) {
        this(skill, null, applier, name, period, duration, tickHealth, applyText, expireText);
    }

    public PeriodicHealingEffect(Skill skill, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickHealth) {
        this(skill, plugin, applier, name, period, duration, tickHealth, null, null);
    }

    @Override
    public double getTickHealth() {
        return tickHealth;
    }

    @Override
    public void setTickHealth(double tickHealth) {
        this.tickHealth = tickHealth;
    }

    @Override
    public void tick(Insentient being) {
        if (!being.isEntityValid() || !getApplier().isEntityValid()) {
            return;
        }
        InsentientRegainHealthEvent event;
        if (being instanceof Champion) {
            event = new ChampionRegainHealthEvent((Champion) being, tickHealth, skill, getApplier());

        } else {
            event = new InsentientRegainHealthEvent(being, tickHealth, skill, getApplier());
        }
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        being.heal(event.getAmount());
    }
}

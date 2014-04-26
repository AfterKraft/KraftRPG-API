/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed change in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Monster;
import com.afterkraft.kraftrpg.api.spells.Spell;

/**
 * @author gabizou
 */
public abstract class PeriodicExpirableEffect extends ExpirableEffect implements Periodic {

    private final long period;
    protected long lastTickTime = 0;

    public PeriodicExpirableEffect(Spell spell, Champion applier, String name, long period, long duration) {
        super(spell, applier, name, duration);
        this.period = period;
    }

    public PeriodicExpirableEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration) {
        super(spell, plugin, applier, name, duration);
        this.period = period;
    }

    public PeriodicExpirableEffect(Spell spell, Champion applier, String name, long period, long duration, String applyText, String expireText) {
        super(spell, applier, name, duration, applyText, expireText);
        this.period = period;
    }

    public PeriodicExpirableEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, String applyText, String expireText) {
        super(spell, plugin, applier, name, duration, applyText, expireText);
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

    @Override
    public void tick(IEntity character) {
        this.lastTickTime = System.currentTimeMillis();
        if (character instanceof Champion) {
            this.tickChampion((Champion) character);
        } else if (character instanceof Monster) {
            this.tickMonster((Monster) character);
        }
    }
}
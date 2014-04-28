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
import com.afterkraft.kraftrpg.api.skills.Skill;


public abstract class PeriodicExpirableEffect extends ExpirableEffect implements Periodic {

    private final long period;
    protected long lastTickTime = 0;

    public PeriodicExpirableEffect(Skill skill, Champion applier, String name, long period, long duration) {
        super(skill, applier, name, duration);
        this.period = period;
    }

    public PeriodicExpirableEffect(Skill skill, RPGPlugin plugin, Champion applier, String name, long period, long duration) {
        super(skill, plugin, applier, name, duration);
        this.period = period;
    }

    public PeriodicExpirableEffect(Skill skill, Champion applier, String name, long period, long duration, String applyText, String expireText) {
        super(skill, applier, name, duration, applyText, expireText);
        this.period = period;
    }

    public PeriodicExpirableEffect(Skill skill, RPGPlugin plugin, Champion applier, String name, long period, long duration, String applyText, String expireText) {
        super(skill, plugin, applier, name, duration, applyText, expireText);
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

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
package com.afterkraft.kraftrpg.api.skills;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;


public class StalledSkill<T extends SkillArgument> implements Stalled<T> {

    private final T argument;
    private final long startTime;
    private final SkillCaster user;
    private final Active<T> skill;
    private final long warmup;
    private final long delay;


    public StalledSkill(Active<T> skill, T args, SkillCaster caster, long warmup) {
        this(skill, args, caster, System.currentTimeMillis(), warmup);
    }

    public StalledSkill(Active<T> skill, T args, SkillCaster caster, long startTime, long warmup) {
        this.argument = args;
        this.startTime = startTime;
        this.skill = skill;
        this.user = caster;
        this.warmup = warmup;
        this.delay = startTime + warmup;
    }

    @Override
    public boolean isReady() {
        return System.currentTimeMillis() > (this.startTime + this.warmup);
    }

    @Override
    public long startTime() {
        return this.startTime;
    }

    @Override
    public T getArgument() {
        return this.argument;
    }

    @Override
    public Active<T> getActiveSkill() {
        return this.skill;
    }

    @Override
    public SkillCaster getCaster() {
        return this.user;
    }

    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        return ((d == 0) ? 0 : ((d < 0) ? -1 : 1));
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
}

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
package com.afterkraft.kraftrpg.api.skills;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;


public class StalledSkill implements Stalled {

    private final String[] argument;
    private final long startTime;
    private final SkillCaster user;
    private final Active skill;
    private final long warmup;
    private final long delay;


    public StalledSkill(Active skill, String[] args, SkillCaster caster, long warmup) {
        this(skill, args, caster, System.currentTimeMillis(), warmup);
    }

    public StalledSkill(Active skill, String[] args, SkillCaster caster, long startTime, long warmup) {
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
    public String[] getArguments() {
        return this.argument;
    }

    @Override
    public Active getActiveSkill() {
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
        return unit.convert(this.delay - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
}

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

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Implementation of an
 * {@link com.afterkraft.kraftrpg.api.entity.effects.Expirable} based on
 * {@link com.afterkraft.kraftrpg.api.entity.effects.Effect}
 */
public class ExpirableEffect extends Effect implements Expirable {

    private final long duration;
    protected SkillCaster applier;
    private String expireText;
    private String applyText;
    private long expireTime;

    public ExpirableEffect(Skill skill, SkillCaster applier, String name, long duration) {
        this(skill, skill.plugin, applier, name, duration, null, null);
    }

    public ExpirableEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long duration, String applyText, String expireText) {
        super(plugin, skill, name);
        this.duration = duration;
        this.applier = applier;
        this.expireText = expireText;
        this.applyText = applyText;
    }

    public ExpirableEffect(Skill skill, SkillCaster applier, String name, long duration, String applyText, String expireText) {
        this(skill, skill.plugin, applier, name, duration, applyText, expireText);
    }

    public ExpirableEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long duration) {
        this(skill, plugin, applier, name, duration, null, null);
    }

    @Override
    public long getApplyTime() {
        return this.applyTime;
    }

    @Override
    public void apply(Insentient being) {
        super.apply(being);
        this.expireTime = this.applyTime + this.duration;
    }

    @Override
    public String getApplyText() {
        return this.applyText;
    }

    @Override
    public void setApplyText(String text) {
        this.applyText = text;
    }

    @Override
    public String getExpireText() {
        return this.expireText;
    }

    @Override
    public void setExpireText(String text) {
        this.expireText = text;
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
    public SkillCaster getApplier() {
        return this.applier;
    }

    @Override
    public void setApplier(SkillCaster champion) {
        this.applier = champion;
    }

    @Override
    public int compareTo(Delayed other) {
        long d = (getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS));
        return ((d == 0) ? 0 : ((d < 0) ? -1 : 1));
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
}

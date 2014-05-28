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

import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.Skill;
import com.afterkraft.kraftrpg.api.skills.SkillType;

/**
 * Standard implementation of a
 * {@link com.afterkraft.kraftrpg.api.entity.effects.Periodic} and
 * {@link com.afterkraft.kraftrpg.api.entity.effects.Damage}. Consider that
 * this effect will damage the
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being every tick
 * based on {@link #getTickDamage()}
 */
public class PeriodicDamageEffect extends PeriodicExpirableEffect implements Damage {

    private final boolean knockback;
    protected double tickDamage;

    public PeriodicDamageEffect(Skill skill, String name, SkillCaster applier, long period, long duration, double tickDamage) {
        this(skill, name, applier, period, duration, tickDamage, false, null, null);
    }

    public PeriodicDamageEffect(Skill skill, String name, SkillCaster applier, long period, long duration, double tickDamage, boolean knockback, String applyText, String expireText) {
        this(skill, skill.plugin, applier, name, period, duration, tickDamage, knockback, applyText, expireText);
    }

    public PeriodicDamageEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long period, long duration, double tickDamage, boolean knockback, String applyText, String expireText) {
        super(skill, plugin, applier, name, period, duration, applyText, expireText);

        types.add(EffectType.HARMFUL);

        this.tickDamage = tickDamage;
        this.knockback = knockback;
    }

    public PeriodicDamageEffect(Skill skill, String name, SkillCaster applier, long period, long duration, double tickDamage, boolean knockback) {
        this(skill, name, applier, period, duration, tickDamage, knockback, null, null);
    }

    public PeriodicDamageEffect(Skill skill, String name, SkillCaster applier, long period, long duration, double tickDamage, String applyText, String expireText) {
        this(skill, name, applier, period, duration, tickDamage, false, applyText, expireText);
    }

    public PeriodicDamageEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long period, long duration, double tickDamage) {
        this(skill, plugin, applier, name, period, duration, tickDamage, false, null, null);
    }

    public PeriodicDamageEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long period, long duration, double tickDamage, boolean knockback) {
        this(skill, plugin, applier, name, period, duration, tickDamage, knockback, null, null);
    }

    public PeriodicDamageEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long period, long duration, double tickDamage, String applyText, String expireText) {
        this(skill, plugin, applier, name, period, duration, tickDamage, false, applyText, expireText);
    }

    @Override
    public double getTickDamage() {
        return 0;
    }

    @Override
    public void setTickDamage(double tickDamage) {

    }

    @Override
    public boolean isKnockback() {
        return this.knockback;
    }

    /**
     * {@inheritDoc} This will damage the
     * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being the
     * prescribed damage from {@link #getTickDamage()}
     * 
     * @param being - The being this effect is being applied to.
     */
    @Override
    public void tick(Insentient being) {
        if (being.isEntityValid() && (getApplier() != null) && getApplier().isEntityValid()) {
            if (!skill.damageCheck(being, getApplier().getEntity())) {
                return;
            }
            if (being instanceof SkillCaster) {
                SkillCaster caster = (SkillCaster) being;
                skill.addSkillTarget(being.getEntity(), caster);
                Skill.damageEntity(being.getEntity(), getApplier().getEntity(), tickDamage, skill.isType(SkillType.ABILITY_PROPERTY_PHYSICAL) ? EntityDamageEvent.DamageCause.ENTITY_ATTACK : EntityDamageEvent.DamageCause.MAGIC, knockback);
            }
        }
    }
}

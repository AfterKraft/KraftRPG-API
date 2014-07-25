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

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

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

    public PeriodicDamageEffect(Skill skill, String name, SkillCaster applier, long period, long duration, double tickDamage, EffectType... types) {
        this(skill, name, applier, period, duration, tickDamage, false, null, null, types);
    }

    public PeriodicDamageEffect(Skill skill, String name, SkillCaster applier, long period, long duration, double tickDamage, boolean knockback, String applyText, String expireText, EffectType... types) {
        this(skill, skill.plugin, applier, name, period, duration, tickDamage, knockback, applyText, expireText, types);
    }

    public PeriodicDamageEffect(Skill skill, RPGPlugin plugin, SkillCaster applier, String name, long period, long duration, double tickDamage, boolean knockback, String applyText, String expireText, EffectType... types) {
        super(skill, plugin, applier, name, period, duration, applyText, expireText, types);
        this.types.add(EffectType.HARMFUL);

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
        return this.tickDamage;
    }

    @Override
    public void setTickDamage(double tickDamage) {
        Validate.isTrue(tickDamage > 0, "Cannot set a negative or zero tick damage!");
        this.tickDamage = tickDamage;
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
        Validate.notNull(being, "Cannot tick on a null Insentient being!");
        if (being.isEntityValid() && (getApplier() != null) && getApplier().isEntityValid()) {
            if (!this.skill.damageCheck(being, getApplier().getEntity())) {
                return;
            }
            if (being instanceof SkillCaster) {
                SkillCaster caster = (SkillCaster) being;
                this.skill.addSkillTarget(being.getEntity(), caster);
                Skill.damageEntity(being.getEntity(), getApplier().getEntity(), this.tickDamage, this.skill.isType(SkillType.ABILITY_PROPERTY_PHYSICAL) ? DamageCause.ENTITY_ATTACK : DamageCause.MAGIC, this.knockback);
            }
        }
    }
}

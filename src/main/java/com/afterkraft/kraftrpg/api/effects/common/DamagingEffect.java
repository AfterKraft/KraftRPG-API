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

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.PeriodicExpirableEffect;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.Skill;
import com.afterkraft.kraftrpg.api.skills.SkillType;

/**
 * Standard implementation of a
 * {@link com.afterkraft.kraftrpg.api.effects.Periodic} and
 * {@link Damaging}. Consider that
 * this effect will damage the
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being every tick
 * based on {@link #getTickDamage()}
 */
public class DamagingEffect extends PeriodicExpirableEffect implements Damaging {

    private final boolean knockback;
    protected double tickDamage;

    public DamagingEffect(Skill skill, Insentient applier, String name, long duration, EnumSet<EffectType> types, long period, double tickDamage) {
        this(skill, applier, name, null, false, types, "", "", duration, period, false, tickDamage);
    }

    public DamagingEffect(Skill skill, Insentient applier, String name, long duration, EnumSet<EffectType> types, long period, boolean knockback, double tickDamage) {
        this(skill, applier, name, null, false, types, "", "", duration, period, knockback, tickDamage);
    }

    public DamagingEffect(Skill skill, Insentient applier, String name, long duration, String applyText, String expireText, Collection<EffectType> types, long period, double tickDamage) {
        this(skill, applier, name, null, false, types, applyText, expireText, duration, period, false, tickDamage);
    }

    public DamagingEffect(Skill skill, Insentient applier, String name, long duration, String applyText, String expireText, Collection<EffectType> types, long period, boolean knockback, double tickDamage) {
        this(skill, applier, name, null, false, types, applyText, expireText, duration, period, knockback, tickDamage);
    }

    public DamagingEffect(Skill skill, Insentient applier, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types, String applyText, String expireText, long duration, long period, double tickDamage) {
        this(skill, applier, name, potionEffects, persistent, types, applyText, expireText, duration, period, false, tickDamage);
    }

    public DamagingEffect(Skill skill, Insentient applier, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types, String applyText, String expireText, long duration, long period, boolean knockback, double tickDamage) {
        super(skill, applier, name, potionEffects, persistent, types, applyText, expireText, duration, period);
        this.knockback = knockback;
        this.tickDamage = tickDamage;
        this.types.add(EffectType.HARMFUL);
        this.types.add(EffectType.DAMAGING);
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
            if (!Skill.damageCheck(being, getApplier().getEntity())) {
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

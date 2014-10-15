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
package com.afterkraft.kraftrpg.api.effects.common;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.PeriodicExpirableEffect;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.events.entity.damage.InsentientDamageEvent.DamageType;
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
        checkArgument(tickDamage > 0, "Cannot set a negative or zero tick damage!");
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
        checkArgument(being != null, "Cannot tick on a null Insentient being!");
        if (being.isEntityValid() && (getApplier() != null) && getApplier().isEntityValid()) {
            if (!Skill.damageCheck(being, getApplier().getEntity())) {
                return;
            }
            if (being instanceof SkillCaster) {
                SkillCaster caster = (SkillCaster) being;
                this.skill.addSkillTarget(being.getEntity(), caster);
                boolean isMagic = this.skill.isType(SkillType.ABILITY_PROPERTY_PHYSICAL);
                Map<DamageType, Double> modifiers = new HashMap<DamageType, Double>();
                if (isMagic) {
                    modifiers.put(DamageType.MAGICAL, this.tickDamage);
                } else {
                    modifiers.put(DamageType.PHYSICAL, this.tickDamage);
                }
                Skill.damageEntity(being, (SkillCaster) getApplier(), this.skill, modifiers, this.skill.isType(SkillType.ABILITY_PROPERTY_PHYSICAL) ? DamageCause.ENTITY_ATTACK : DamageCause.MAGIC, this.knockback);
            }
        }
    }
}

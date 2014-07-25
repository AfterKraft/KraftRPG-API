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

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Standard implementation of an
 * {@link com.afterkraft.kraftrpg.api.entity.effects.IEffect}.
 */
public abstract class Effect implements IEffect {

    public final Set<EffectType> types = EnumSet.noneOf(EffectType.class);
    protected final String name;
    protected final RPGPlugin plugin;
    protected final Skill skill;
    private final Set<PotionEffect> potionEffects = new HashSet<PotionEffect>();
    protected long applyTime;
    private boolean persistent = false;

    public Effect(Skill skill, String name) {
        this(skill.plugin, skill, name);
    }

    public Effect(RPGPlugin plugin, Skill skill, String name, EffectType... types) {
        Validate.notNull(plugin, "Cannot create an effect with a null RPGPlugin!");
        Validate.notNull(skill, "Cannot create an effect with a null Skill!");
        Validate.notNull(name, "Cannot create an effect with a null name!");
        Validate.isTrue(!name.isEmpty(), "Cannot create an effect with an empty name!");
        this.name = name;
        this.skill = skill;
        this.plugin = plugin;

        if (types != null) {
            this.types.addAll(Arrays.asList(types));
        }
    }

    public Effect(Skill skill, String name, EffectType... types) {
        this(skill.plugin, skill, name, types);
    }

    @Override
    public final Skill getSkill() {
        return this.skill;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public boolean isType(EffectType queryType) {
        Validate.notNull(queryType, "Cannot check a null EffectType!");
        return this.types.contains(queryType);
    }

    @Override
    public boolean isPersistent() {
        return this.persistent;
    }

    @Override
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    @Override
    public void addPotionEffect(PotionEffect pEffect) {
        Validate.notNull(pEffect, "Cannot add a null PotionEffect!");
        this.potionEffects.add(pEffect);
    }

    @Override
    public long getApplyTime() {
        return this.applyTime;
    }

    @Override
    public void apply(Insentient being) {
        Validate.notNull(being, "Cannot apply an effect to a null Insentient being!");
        this.applyTime = System.currentTimeMillis();
        if (!this.potionEffects.isEmpty()) {
            for (final PotionEffect pEffect : this.potionEffects) {
                being.addPotionEffect(pEffect);
            }
        }
    }

    @Override
    public void remove(Insentient being) {
        Validate.notNull(being, "Cannot remove an effect to a null Insentient being!");
        if (!this.potionEffects.isEmpty()) {
            for (final PotionEffect pEffect : this.potionEffects) {
                being.removePotionEffect(pEffect.getType());
            }
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Effect)) {
            return false;
        }
        final Effect other = (Effect) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}

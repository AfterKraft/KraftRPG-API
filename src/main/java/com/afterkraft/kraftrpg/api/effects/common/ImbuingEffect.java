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
import java.util.Set;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.effects.Effect;
import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.IEffect;
import com.afterkraft.kraftrpg.api.skills.Skill;

public class ImbuingEffect extends Effect implements Imbuing {

    protected final Insentient applier;

    public ImbuingEffect(Skill skill, Insentient applier, String name, Collection<EffectType> types) {
        this(skill, applier, name, "", "", types);
    }

    public ImbuingEffect(Skill skill, Insentient applier, String name, String applyText, String expireText, Collection<EffectType> types) {
        this(skill, applier, name, null, false, types, applyText, expireText);
    }

    public ImbuingEffect(Skill skill, Insentient applier, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types, String applyText, String expireText) {
        super(skill, name, potionEffects, persistent, types, applyText, expireText);
        super.types.add(EffectType.IMBUE);
        this.applier = applier;
    }

    @Override
    public Insentient getApplier() {
        return this.applier;
    }

    @Override
    public void apply(Insentient being) {
        super.apply(being);
        for (IEffect effect : being.getEffects()) {
            if (effect.equals(this)) {
                continue;
            }
            if (effect.isType(EffectType.IMBUE)) {
                being.removeEffect(effect);
            }
        }
    }
}

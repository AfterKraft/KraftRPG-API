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
package com.afterkraft.kraftrpg.common.effects.common;

import java.util.Collection;
import java.util.Set;

import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.Messages;

import com.afterkraft.kraftrpg.api.effects.common.Imbuing;
import com.afterkraft.kraftrpg.common.effects.Effect;
import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.IEffect;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.common.skills.Skill;

/**
 * A standard effect that focuses on behaviors for applying to a weapon or object related to the
 * owning Insentient being. Standard usages are: applying an effect to Insentient beings harmed by a
 * weapon wielded by the attacking Insentient being, applying an effect to the Insentient being
 * while a certain weapon is equipped, etc.
 */
public class ImbuingEffect extends Effect implements Imbuing {

    protected final Insentient applier;

    public ImbuingEffect(Skill skill, Insentient applier, String name,
                         Collection<EffectType> types) {
        this(skill, applier, name, Messages.of(""), Messages.of(""), types);
    }

    public ImbuingEffect(Skill skill, Insentient applier, String name, Message applyText,
                         Message expireText, Collection<EffectType> types) {
        this(skill, applier, name, null, false, types, applyText, expireText);
    }

    public ImbuingEffect(Skill skill, Insentient applier, String name,
                         Set<PotionEffect> potionEffects, boolean persistent,
                         Collection<EffectType> types, Message applyText, Message expireText) {
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

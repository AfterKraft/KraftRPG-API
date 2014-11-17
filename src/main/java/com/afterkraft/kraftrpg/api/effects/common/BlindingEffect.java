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

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.ExpirableEffect;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Standard blinding effect that applies a Blinding potion effect to the victim. This is usually
 * client-visible only.
 */
public class BlindingEffect extends ExpirableEffect {

    public static final String DEFAULT_APPLY_TEXT = "You've become invisible to the world.";
    public static final String DEFAULT_EXPIRE_TEXT = "You are now visible to the world.";
    public static final Set<EffectType> DEFAULT_Blinding_EFFECTTYPES =
            EnumSet.of(EffectType.BLIND, EffectType.HARMFUL);

    private static final Set<UUID> invisiblePlayers = new HashSet<>();

    public BlindingEffect(Skill skill, Insentient applier, String name, long duration) {
        this(skill, applier, name, duration, DEFAULT_APPLY_TEXT, DEFAULT_EXPIRE_TEXT);
    }

    public BlindingEffect(Skill skill, Insentient applier, String name, long duration,
                          String applyText, String expireText) {
        super(skill, applier, name, duration, applyText, expireText, DEFAULT_Blinding_EFFECTTYPES);
        addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                (int) ((duration / 1000) * 20), 0, false));
    }

}

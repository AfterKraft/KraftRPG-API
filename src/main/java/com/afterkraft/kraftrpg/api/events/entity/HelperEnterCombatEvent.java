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
package com.afterkraft.kraftrpg.api.events.entity;

import java.util.WeakHashMap;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.EnterCombatReason;
import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * This event is called when a SkillCaster enters combat due to buffing or healing a Sentient
 * already in combat with the 'other'.
 */
public class HelperEnterCombatEvent extends EnterCombatEvent {
    private Sentient buffed;

    public HelperEnterCombatEvent(SkillCaster being, Sentient buffed, Sentient other,
                                  WeakHashMap<LivingEntity, EnterCombatReason> combatMap,
                                  EnterCombatReason reason) {
        super(being, other, combatMap, reason);
    }

    public final Sentient getBuffedBeing() {
        return this.buffed;
    }
}

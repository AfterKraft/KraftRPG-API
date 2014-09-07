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

import java.util.Collection;
import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.Skill;

public class WeaponImbuingEffect extends ImbuingEffect implements WeaponImbuing {
    private final int maxHits;
    private int totalHits;

    public WeaponImbuingEffect(Skill skill, Insentient applier, String name) {
        this(skill, applier, name, 1);
    }

    public WeaponImbuingEffect(Skill skill, Insentient applier, String name, int maxHits) {
        this(skill, applier, name, null, maxHits);
    }

    public WeaponImbuingEffect(Skill skill, Insentient applier, String name, Collection<EffectType> types) {
        this(skill, applier, name, types, 1);
    }

    public WeaponImbuingEffect(Skill skill, Insentient applier, String name, Collection<EffectType> types, int maxHits) {
        this(skill, applier, name, types, "", "", maxHits);
    }

    public WeaponImbuingEffect(Skill skill, Insentient applier, String name, Collection<EffectType> types, String applyText, String expireText, int maxHits) {
        this(skill, applier, name, types, null, false, applyText, expireText, maxHits);
    }

    public WeaponImbuingEffect(Skill skill, Insentient applier, String name, Collection<EffectType> types, Set<PotionEffect> potionEffects, boolean persistent, String applyText, String expireText, int maxHits) {
        super(skill, applier, name, potionEffects, persistent, types, applyText, expireText);
        this.maxHits = maxHits;
        this.types.add(EffectType.PHYSICAL);
    }

    @Override
    public boolean applyToWeapon(ItemStack itemStack) {
        return true;
    }

    @Override
    public void onEntityHit(Insentient victim, Insentient attacker, ItemStack weapon) {

    }

    @Override
    public int getHitsLeft() {
        return this.maxHits - this.totalHits;
    }

    public void onEntityHit(Insentient victim, ItemStack weapon) {
        if (getHitsLeft() > 0) {
            this.totalHits++;
        } else {
            this.applier.removeEffect(this);
        }
    }
}

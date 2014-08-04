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

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.effects.EffectType;
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

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

import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.skills.Skill;

public class ProjectileShotEffect extends ImbuingEffect implements ProjectileShot {

    private final int maxShots;
    private int currentShots;

    public ProjectileShotEffect(Skill skill, SkillCaster applier, String name) {
        this(skill, applier, name, 1);
    }

    public ProjectileShotEffect(Skill skill, SkillCaster applier, String name, int maxShots) {
        this(skill, applier, name, null, maxShots);
    }

    public ProjectileShotEffect(Skill skill, SkillCaster applier, String name, Collection<EffectType> types) {
        this(skill, applier, name, null, false, types, "", "");
    }

    public ProjectileShotEffect(Skill skill, SkillCaster applier, String name, Collection<EffectType> types, int maxShots) {
        this(skill, applier, name, null, false, types, "", "", maxShots);
    }

    public ProjectileShotEffect(Skill skill, SkillCaster applier, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types, String applyText, String expireText) {
        this(skill, applier, name, potionEffects, persistent, types, applyText, expireText, 1);
    }

    public ProjectileShotEffect(Skill skill, SkillCaster applier, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types, String applyText, String expireText, int maxShots) {
        super(skill, applier, name, potionEffects, persistent, types, applyText, expireText);
        super.types.add(EffectType.BENEFICIAL);
        super.types.add(EffectType.PHYSICAL);
        super.types.add(EffectType.IMBUE);
        this.maxShots = maxShots;
    }

    @Override
    public int getShotsLeft() {
        return this.maxShots - this.currentShots;
    }

    @Override
    public boolean applyToProjectile(Projectile projectile) {
        if (getShotsLeft() > 0) {
            this.currentShots++;
            return true;
        } else {
            this.applier.removeEffect(this);
            return false;
        }
    }

    @Override
    public void onProjectileLand(Projectile projectile, Location location) {

    }

    @Override
    public void onProjectileDamage(Projectile projectile, Insentient victim) {

    }
}

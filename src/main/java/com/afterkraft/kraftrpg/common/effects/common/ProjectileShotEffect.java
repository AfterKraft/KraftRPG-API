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

import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.world.Location;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.common.ProjectileShot;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.common.skills.Skill;

/**
 * Standard implementation of the {@link com.afterkraft.kraftrpg.api.effects.common.ProjectileShot}.
 * This is intended to be extended for further customization and simplicity.
 */
public class ProjectileShotEffect extends ImbuingEffect implements
        ProjectileShot {

    private final int maxShots;
    private int currentShots;

    public ProjectileShotEffect(Skill skill, Insentient applier, String name) {
        this(skill, applier, name, 1);
    }

    public ProjectileShotEffect(Skill skill, Insentient applier, String name, int maxShots) {
        this(skill, applier, name, null, maxShots);
    }

    public ProjectileShotEffect(Skill skill, Insentient applier, String name,
                                Collection<EffectType> types, int maxShots) {
        this(skill, applier, name, null, false, types, "", "", maxShots);
    }

    public ProjectileShotEffect(Skill skill, Insentient applier, String name,
                                Set<PotionEffect> potionEffects, boolean persistent,
                                Collection<EffectType> types, String applyText,
                                String expireText, int maxShots) {
        super(skill, applier, name, potionEffects, persistent, types, applyText, expireText);
        super.types.add(EffectType.BENEFICIAL);
        super.types.add(EffectType.PHYSICAL);
        super.types.add(EffectType.IMBUE);
        this.maxShots = maxShots;
    }

    public ProjectileShotEffect(Skill skill, Insentient applier, String name,
                                Collection<EffectType> types) {
        this(skill, applier, name, null, false, types, "", "");
    }

    public ProjectileShotEffect(Skill skill, Insentient applier, String name,
                                Set<PotionEffect> potionEffects, boolean persistent,
                                Collection<EffectType> types, String applyText,
                                String expireText) {
        this(skill, applier, name, potionEffects, persistent, types, applyText, expireText, 1);
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

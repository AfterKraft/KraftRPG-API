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
package com.afterkraft.kraftrpg.api.listeners;

import java.lang.ref.WeakReference;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Standard wrapper for attack damage dealt with an ItemStack
 */
public class AttackDamageWrapper extends DamageWrapper {
    private final WeakReference<Insentient> attackingIEntity;
    private final ItemStack weaponUsed;

    public AttackDamageWrapper(Insentient attackingIEntity, DamageCause originalCause,
                               double originalDamage, double modifiedDamage,
                               DamageCause modifiedCause) {
        super(originalCause, originalDamage, modifiedDamage, modifiedCause);
        this.attackingIEntity = new WeakReference<>(attackingIEntity);
        this.weaponUsed = new ItemStack(attackingIEntity.getItemInHand());
    }

    public Insentient getAttackingIEntity() {
        return this.attackingIEntity.get();
    }

    public final ItemStack getWeaponUsed() {
        return this.weaponUsed.clone();
    }
}

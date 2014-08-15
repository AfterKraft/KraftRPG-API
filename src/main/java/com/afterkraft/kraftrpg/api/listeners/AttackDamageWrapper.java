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
package com.afterkraft.kraftrpg.api.listeners;

import java.lang.ref.WeakReference;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.entity.Insentient;

public class AttackDamageWrapper extends DamageWrapper {
    private final WeakReference<Insentient> attackingIEntity;
    private final ItemStack weaponUsed;

    public AttackDamageWrapper(Insentient attackingIEntity, DamageCause originalCause, double originalDamage, double modifiedDamage, DamageCause modifiedCause) {
        super(originalCause, originalDamage, modifiedDamage, modifiedCause);
        this.attackingIEntity = new WeakReference<Insentient>(attackingIEntity);
        this.weaponUsed = new ItemStack(attackingIEntity.getItemInHand());
    }

    public Insentient getAttackingIEntity() {
        return this.attackingIEntity.get();
    }

    public final ItemStack getWeaponUsed() {
        return this.weaponUsed.clone();
    }
}

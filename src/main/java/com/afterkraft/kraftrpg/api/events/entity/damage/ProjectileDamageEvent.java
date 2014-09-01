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
package com.afterkraft.kraftrpg.api.events.entity.damage;

import java.util.Map;

import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.entity.Insentient;

public class ProjectileDamageEvent extends WeaponDamageEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Projectile projectile;

    public ProjectileDamageEvent(final Insentient attacker, final Insentient defender, final Projectile projectile, final EntityDamageByEntityEvent event, final ItemStack weapon, final Map<DamageType, Double> modifiers, boolean isVaryingEnabled) {
        super(attacker, defender, event, weapon, modifiers, isVaryingEnabled);
        this.projectile = projectile;
    }

    public final Projectile getProjectile() {
        return this.projectile;
    }

}

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

import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * A traditional catch all damage event to be handled by KraftRPG for any
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being damaged by an
 * attacking Insentient with a weapon. This specific event does always have an
 * attacker, but it is handled with the Insentient in the event there is an
 * Insentient being damaged for any reason (including instances of
 * CommandBlocks or {@link org.bukkit.entity.Entity}).
 * 
 * This is the KraftRPG counterpart to
 * {@link org.bukkit.event.entity.EntityDamageByEntityEvent} after special
 * handling and calculations being made on the damage being dealt.
 * 
 * This specific event is called when the {@link #getAttacker()} is dealing
 * damage via weapon as understood by
 * {@link com.afterkraft.kraftrpg.api.util.Utilities#isStandardWeapon(org.bukkit.Material)}
 * 
 * 
 * It is guaranteed that the {@link #getDefender()} will not be null, but it
 * is not guaranteed the linked {@link org.bukkit.entity.LivingEntity} is not
 * a real entity. It is also guaranteed the {@link #getAttacker()} is not
 * null, but it is also not guaranteed that the linked
 * {@link org.bukkit.entity.LivingEntity} is not a real entity.
 * 
 * Therefore the methods provided from
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} should be the only
 * ones considered "safe" to use.
 * 
 * This is written due to the possibility of customizing entities through
 * {@link com.afterkraft.kraftrpg.api.entity.EntityManager#addEntity(com.afterkraft.kraftrpg.api.entity.IEntity)}
 * that may not be considered {@link org.bukkit.entity.LivingEntity}.
 */
public class WeaponDamageEvent extends InsentientDamageInsentientEvent {

    private static final HandlerList handlers = new HandlerList();

    private ItemStack weapon;

    public WeaponDamageEvent(Insentient attacker, Insentient defender, EntityDamageEvent event, ItemStack weapon, double defaultDamage, boolean isVaryingEnabled) {
        super(attacker, defender, event, defaultDamage, isVaryingEnabled);
        this.weapon = weapon;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Get the weapon used to attack the {@link #getDefender()}. It may or may
     * not have customized damage, all of which is already handled by the
     * {@link com.afterkraft.kraftrpg.api.util.DamageManager#getHighestItemDamage(com.afterkraft.kraftrpg.api.entity.Insentient, com.afterkraft.kraftrpg.api.entity.Insentient, double)}
     * 
     * @return the Item used to attack the defending Insentient being.
     */
    public ItemStack getWeaponUsed() {
        return this.weapon;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}

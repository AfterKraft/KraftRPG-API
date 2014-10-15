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
package com.afterkraft.kraftrpg.api.events.entity.damage;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * A traditional catch all damage event to be handled by KraftRPG for any {@link
 * com.afterkraft.kraftrpg.api.entity.Insentient} being damaged by an attacking Insentient with a
 * weapon. This specific event does always have an attacker, but it is handled with the Insentient
 * in the event there is an Insentient being damaged for any reason (including instances of
 * CommandBlocks or {@link org.bukkit.entity.Entity}).
 * <p/>
 * This is the KraftRPG counterpart to {@link org.bukkit.event.entity.EntityDamageByEntityEvent}
 * after special handling and calculations being made on the damage being dealt.
 * <p/>
 * This specific event is called when the {@link #getAttacker()} is dealing damage via weapon as
 * understood by {@link com.afterkraft.kraftrpg.api.util.Utilities#isStandardWeapon(org.bukkit.Material)}
 * <p/>
 * <p/>
 * It is guaranteed that the {@link #getDefender()} will not be null, but it is not guaranteed the
 * linked {@link org.bukkit.entity.LivingEntity} is not a real entity. It is also guaranteed the
 * {@link #getAttacker()} is not null, but it is also not guaranteed that the linked {@link
 * org.bukkit.entity.LivingEntity} is not a real entity.
 * <p/>
 * Therefore the methods provided from {@link com.afterkraft.kraftrpg.api.entity.Insentient} should
 * be the only ones considered "safe" to use.
 */
public class WeaponDamageEvent extends InsentientDamageInsentientEvent {

    private final ItemStack weapon;

    public WeaponDamageEvent(final Insentient attacker, final Insentient defender,
                             final EntityDamageByEntityEvent event, final ItemStack weapon,
                             final Map<DamageType, Double> modifiers,
                             final boolean isVaryingEnabled) {
        super(attacker, defender, event, modifiers, isVaryingEnabled);
        checkArgument(weapon != null, "Cannot have a null weapon!");
        this.weapon = new ItemStack(weapon);
    }

    /**
     * Get the weapon used to attack the {@link #getDefender()}. It may or may not have customized
     * damage, all of which is already handled by the {@link com.afterkraft.kraftrpg.api.util.DamageManager#getHighestItemDamage(com.afterkraft.kraftrpg.api.entity.Insentient,
     * com.afterkraft.kraftrpg.api.entity.Insentient, double)}
     *
     * @return the Item used to attack the defending Insentient being.
     */
    public final ItemStack getWeaponUsed() {
        return this.weapon.clone();
    }
}

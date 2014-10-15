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

import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * A traditional catch all damage event to be handled by KraftRPG for any {@link
 * com.afterkraft.kraftrpg.api.entity.Insentient} being damaged for any reason. This specific event
 * does always have an attacker, but it is handled with the Insentient in the event there is an
 * Insentient being damaged for any reason (including instances of CommandBlocks or {@link
 * org.bukkit.entity.Entity}).
 * <p/>
 * This is the KraftRPG counterpart to {@link org.bukkit.event.entity.EntityDamageByEntityEvent}
 * after special handling and calculations being made on the damage being dealt. It is guaranteed
 * that the {@link #getDefender()} will not be null, but it is not guaranteed the linked {@link
 * org.bukkit.entity.LivingEntity} is not a real entity. It is also guaranteed the {@link
 * #getAttacker()} is not null, but it is also not guaranteed that the linked {@link
 * org.bukkit.entity.LivingEntity} is not a real entity.
 * <p/>
 * Therefore the methods provided from {@link com.afterkraft.kraftrpg.api.entity.Insentient} should
 * be the only ones considered "safe" to use.
 * <p/>
 */
public class InsentientDamageInsentientEvent extends InsentientDamageEvent {
    private final Insentient attacker;

    public InsentientDamageInsentientEvent(final Insentient attacker, final Insentient defender,
                                           final EntityDamageByEntityEvent event,
                                           final Map<DamageType, Double> modifiers,
                                           final boolean isVarying) {
        super(defender, event, modifiers, isVarying);
        this.attacker = attacker;
    }

    /**
     * Return the attacking {@link com.afterkraft.kraftrpg.api.entity.Insentient}. It is necessary
     * to understand that the attacking Insentient may not always link to a {@link
     * org.bukkit.entity.LivingEntity} and therefor must consider to use only the methods provided
     * from {@link com.afterkraft.kraftrpg.api.entity.Insentient}
     *
     * @return the attacking Insentient being
     */
    public Insentient getAttacker() {
        return this.attacker;
    }
}

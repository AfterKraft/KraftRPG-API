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

import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * A traditional catch all damage event to be handled by KraftRPG for any
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being damaged for any
 * reason. This specific event does always have an attacker, but it is handled
 * with the Insentient in the event there is an Insentient being damaged for
 * any reason (including instances of CommandBlocks or
 * {@link org.bukkit.entity.Entity}).
 * <p/>
 * This is the KraftRPG counterpart to
 * {@link org.bukkit.event.entity.EntityDamageByEntityEvent} after special
 * handling and calculations being made on the damage being dealt. It is
 * guaranteed that the {@link #getDefender()} will not be null, but it is not
 * guaranteed the linked {@link org.bukkit.entity.LivingEntity} is not a real
 * entity. It is also guaranteed the {@link #getAttacker()} is not null, but
 * it is also not guaranteed that the linked
 * {@link org.bukkit.entity.LivingEntity} is not a real entity.
 * <p/>
 * Therefore the methods provided from
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} should be the only
 * ones considered "safe" to use.
 * <p/>
 * This is written due to the possibility of customizing entities through
 * {@link com.afterkraft.kraftrpg.api.entity.EntityManager#addEntity(com.afterkraft.kraftrpg.api.entity.IEntity)}
 * that may not be considered {@link org.bukkit.entity.LivingEntity}.
 */
public class InsentientDamageInsentientEvent extends InsentientDamageEvent {
    private final Insentient attacker;

    public InsentientDamageInsentientEvent(final Insentient attacker, final Insentient defender, final EntityDamageByEntityEvent event, final Map<DamageType, Double> modifiers, final boolean isVarying) {
        super(defender, event, modifiers, isVarying);
        this.attacker = attacker;
    }

    /**
     * Return the attacking
     * {@link com.afterkraft.kraftrpg.api.entity.Insentient}. It is necessary
     * to understand that the attacking Insentient may not always link to a
     * {@link org.bukkit.entity.LivingEntity} and therefor must consider to
     * use only the methods provided from
     * {@link com.afterkraft.kraftrpg.api.entity.Insentient}
     *
     * @return the attacking Insentient being
     */
    public Insentient getAttacker() {
        return this.attacker;
    }
}

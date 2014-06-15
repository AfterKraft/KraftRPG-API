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

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.entity.IEntityEvent;

/**
 * A traditional catch all damage event to be handled by KraftRPG for any
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} being damaged for any
 * reason. This specific event does not always have an attacker, but it is
 * handled with the Insentient in the event there is an Insentient being
 * damaged for any reason (including instances of CommandBlocks or
 * {@link org.bukkit.entity.Entity}).
 * 
 * This is the KraftRPG counterpart to
 * {@link org.bukkit.event.entity.EntityDamageEvent} after special handling
 * and calculations being made on the damage being dealt. It is guaranteed
 * that the {@link #getDefender()} will not be null, but it is not guaranteed
 * the linked {@link org.bukkit.entity.LivingEntity} is not a real entity.
 * Therefore the methods provided from
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} should be the only
 * ones considered "safe" to use.
 * 
 * This is written due to the possibility of customizing entities through
 * {@link com.afterkraft.kraftrpg.api.entity.EntityManager#addEntity(com.afterkraft.kraftrpg.api.entity.IEntity)}
 * that may not be considered {@link org.bukkit.entity.LivingEntity}.
 */
public class InsentientDamageEvent extends IEntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final EntityDamageEvent.DamageCause cause;
    private Insentient defender;
    private double defaultDamage, finalDamage;
    private boolean isVaryingDamageEnabled;
    private boolean cancelled;

    public InsentientDamageEvent(Insentient defender, EntityDamageEvent.DamageCause cause, double defaultDamage, double finalDamage, boolean isVaryingEnabled) {
        super(defender);
        this.defender = defender;
        this.defaultDamage = defaultDamage;
        this.finalDamage = finalDamage;
        this.isVaryingDamageEnabled = isVaryingEnabled;
        this.cause = cause;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Get the defending {@link com.afterkraft.kraftrpg.api.entity.Insentient}
     * that is being damaged.
     * 
     * @return the defending Insentient being
     */
    public Insentient getDefender() {
        return defender;
    }

    /**
     * Get the default damage (unmodified) of this event as calculated by the
     * {@link com.afterkraft.kraftrpg.api.util.DamageManager}
     * 
     * @return the default calculated damage
     */
    public double getDefaultDamage() {
        return defaultDamage;
    }

    /**
     * Get the final damage after other listeners have handled this event.
     * This is usually to handle for specific customizations depending on the
     * {@link org.bukkit.event.entity.EntityDamageEvent.DamageCause}
     * 
     * @return the final damage to be inflicted on the defending Insentient
     */
    public double getFinalDamage() {
        return finalDamage;
    }

    /**
     * Set the final damage to deal on the defending Insentient being.
     * 
     * @param finalDamage to deal to the insentient being.
     */
    public void setFinalDamage(double finalDamage) {
        this.finalDamage = finalDamage;
    }

    /**
     * Check if this damage was varied, if true, the default damage from
     * {@link #getDefaultDamage()} is that calculated varied damage. If not,
     * the damage is a precise number.
     * 
     * @return true if KraftRPG was configured to have varied damage
     */
    public boolean isVaryingDamageEnabled() {
        return isVaryingDamageEnabled;
    }

    /**
     * Return the damage cause for this event. The cause is pulled from
     * {@link org.bukkit.event.entity.EntityDamageEvent} and further processed
     * from KraftRPG in the event a
     * {@link com.afterkraft.kraftrpg.api.skills.ISkill} dealt damage.
     * 
     * @return the damage cause for this event.
     */
    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}

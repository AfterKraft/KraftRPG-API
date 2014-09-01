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
package com.afterkraft.kraftrpg.api.events.entity;

import java.util.WeakHashMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.EnterCombatReason;
import com.afterkraft.kraftrpg.api.entity.Sentient;

public class EnterCombatEvent extends InsentientEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    protected final Sentient with;
    protected final EnterCombatReason reason;
    private final WeakHashMap<LivingEntity, EnterCombatReason> combatMap;
    private boolean cancelled;

    public EnterCombatEvent(Sentient being, Sentient with, WeakHashMap<LivingEntity, EnterCombatReason> combatMap, EnterCombatReason reason) {
        super(being);
        this.with = with;
        this.combatMap = combatMap;
        this.reason = reason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    /**
     * Get the Sentient that the primary one is entering combat with.
     *
     * @return sentient
     */
    public final Sentient getOtherBeing() {
        return this.with;
    }

    public final WeakHashMap<LivingEntity, EnterCombatReason> getCombatMap() {
        return this.combatMap;
    }

    public final EnterCombatReason getReason() {
        return this.reason;
    }

    @Override
    public final boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public final void setCancelled(boolean cancel) {
        this.cancelled = cancel;

    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}

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
import com.afterkraft.kraftrpg.api.entity.Insentient;

public class EnterCombatEvent extends Event implements Cancellable {

    protected static final HandlerList handlers = new HandlerList();
    private Insentient being;
    private WeakHashMap<LivingEntity, EnterCombatReason> combatMap;
    private EnterCombatReason reason;
    private boolean cancelled;

    public EnterCombatEvent(Insentient being, WeakHashMap<LivingEntity, EnterCombatReason> combatMap, EnterCombatReason reason) {
        this.being = being;
        this.combatMap = combatMap;
        this.reason = reason;
    }

    public Insentient getBeing() {
        return being;
    }

    public WeakHashMap<LivingEntity, EnterCombatReason> getCombatMap() {
        return combatMap;
    }

    public EnterCombatReason getReason() {
        return reason;
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
        this.cancelled = cancel;

    }
}

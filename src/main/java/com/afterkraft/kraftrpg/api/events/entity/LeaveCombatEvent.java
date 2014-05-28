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
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.EnterCombatReason;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.LeaveCombatReason;

public class LeaveCombatEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Insentient being;
    private WeakHashMap<LivingEntity, EnterCombatReason> combatMap;
    private LeaveCombatReason reason;

    public LeaveCombatEvent(Insentient being, WeakHashMap<LivingEntity, EnterCombatReason> combatMap, LeaveCombatReason reason) {
        this.being = being;
        this.combatMap = combatMap;
        this.reason = reason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Insentient getBeing() {
        return being;
    }

    public WeakHashMap<LivingEntity, EnterCombatReason> getCombatMap() {
        return combatMap;
    }

    public LeaveCombatReason getReason() {
        return reason;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}

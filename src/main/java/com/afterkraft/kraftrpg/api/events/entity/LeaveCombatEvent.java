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
package com.afterkraft.kraftrpg.api.events.entity;

import java.util.WeakHashMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.EnterCombatReason;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.LeaveCombatReason;

/**
 * When an Insentient being leaves combat. The event provides the WeakHashMap of all existing
 * LivingEntities that the Insentient being was in combat with and is no longer in combat.
 */
public class LeaveCombatEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Insentient being;
    private WeakHashMap<LivingEntity, EnterCombatReason> combatMap;
    private LeaveCombatReason reason;

    public LeaveCombatEvent(Insentient being,
                            WeakHashMap<LivingEntity, EnterCombatReason> combatMap,
                            LeaveCombatReason reason) {
        this.being = being;
        this.combatMap = combatMap;
        this.reason = reason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Insentient getBeing() {
        return this.being;
    }

    public WeakHashMap<LivingEntity, EnterCombatReason> getCombatMap() {
        return this.combatMap;
    }

    public LeaveCombatReason getReason() {
        return this.reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}

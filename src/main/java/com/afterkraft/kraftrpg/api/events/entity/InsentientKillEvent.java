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

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Insentient;


public class InsentientKillEvent extends IEntityEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Insentient victim;

    public InsentientKillEvent(Insentient attacker, Insentient victim) {
        super(attacker);
        this.victim = victim;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Insentient getKiller() {
        return this.getEntity();
    }

    public Insentient getVictim() {
        return this.victim;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}

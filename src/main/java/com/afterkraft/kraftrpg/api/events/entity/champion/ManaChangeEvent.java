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
package com.afterkraft.kraftrpg.api.events.entity.champion;

import org.apache.commons.lang.Validate;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;


public class ManaChangeEvent extends ChampionEvent {

    private static final HandlerList handlers = new HandlerList();
    private final int fromMana;
    private final int toMana;
    private final ManaChangeReason reason;

    public ManaChangeEvent(Champion player, int fromMana, int toMana, ManaChangeReason reason) {
        super(player);
        Validate.notNull(reason, "Cannot create an event with a null ManaChangeReason!");
        this.fromMana = fromMana;
        this.toMana = toMana;
        this.reason = reason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public int getFromMana() {
        return this.fromMana;
    }

    public int getToMana() {
        return this.toMana;
    }

    public ManaChangeReason getReason() {
        return this.reason;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public enum ManaChangeReason {
        SKILL_USAGE,
        SKILL_RESULT,
        MANA_REGAIN,
        EXTERNAL
    }
}

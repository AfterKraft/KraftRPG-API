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


import static com.google.common.base.Preconditions.checkArgument;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * An event when an Insentient being's mana changes.
 */
public class ManaChangeEvent extends InsentientEvent {

    private static final HandlerList handlers = new HandlerList();
    private final int fromMana;
    private final int toMana;
    private final ManaChangeReason reason;

    public ManaChangeEvent(Insentient being, int fromMana, int toMana, ManaChangeReason reason) {
        super(being);
        checkArgument(reason != null, "Cannot create an event with a null ManaChangeReason!");
        checkArgument(toMana >= 0, "Cannot handle a Mana event where the mana is less than zero!");
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

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Standard reasons for why mana has changed.
     */
    public enum ManaChangeReason {
        /**
         * Mana is being changed due to casting a skill. The associated Insentient being may be a
         * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}
         */
        SKILL_USAGE,
        /**
         * Mana is being changed due to the result of a skill.
         */
        SKILL_RESULT,
        /**
         * Mana is being changed due to the natural mana regeneration of KraftRPG
         */
        MANA_REGAIN,
        /**
         * An external source is modifying mana.
         */
        EXTERNAL
    }
}

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
package com.afterkraft.kraftrpg.api.events.roles;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.roles.Role;

/**
 * An event thrown when a Sentient being has a level change in a specific role.
 */
public class RoleLevelChangeEvent extends RoleEvent {

    private static final HandlerList handlers = new HandlerList();
    private final int fromLevel;
    private final int toLevel;
    private final boolean isMastering;

    public RoleLevelChangeEvent(Sentient being, Role rpgRole, int from, int to) {
        this(being, rpgRole, from, to, false);
    }

    public RoleLevelChangeEvent(Sentient being, Role rpgRole, int from, int to,
                                boolean isMastering) {
        super(being, rpgRole);
        this.fromLevel = from;
        this.toLevel = to;
        this.isMastering = true;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public int getFromLevel() {
        return this.fromLevel;
    }

    public int getToLevel() {
        return this.toLevel;
    }

    public boolean isMastering() {
        return this.isMastering;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}

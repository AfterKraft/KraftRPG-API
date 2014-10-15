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

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.roles.Role;

/**
 * An event representing when a Sentient being changes roles for any reason.
 */
public class RoleChangeEvent extends RoleEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Role toRPGRole;
    private final boolean canCancel;
    private boolean cancelled;

    public RoleChangeEvent(Champion player, Role fromRPGClass, Role toRPGRole) {
        this(player, fromRPGClass, toRPGRole, true);
    }

    public RoleChangeEvent(Champion player, Role fromRPGClass, Role toRPGRole, boolean canCancel) {
        super(player, fromRPGClass);
        this.toRPGRole = toRPGRole;
        this.canCancel = canCancel;
        this.cancelled = false;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Role getToRPGRole() {
        return this.toRPGRole;
    }

    public Role getFromRPGRole() {
        return this.getRole();
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled && this.canCancel;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}

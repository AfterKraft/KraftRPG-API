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

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.roles.Role;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * When a Sentient being gains experience in a specific Role. The event contains knowledge to the
 * location the experience was gained, the role that is gaining the experience, and the change in
 * experience.
 */
public class ExperienceChangeEvent extends RoleEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Location location;
    private final FixedPoint original;
    private FixedPoint change;
    private boolean cancelled = false;

    public ExperienceChangeEvent(Sentient insentient, Location location, Role role,
                                 FixedPoint original, FixedPoint change) {
        super(insentient, role);
        this.location = location;

        this.original = original;
        this.change = change;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Location getLocation() {
        return this.location;
    }

    public FixedPoint getFromExperience() {
        return this.original;
    }

    public FixedPoint getChange() {
        return this.change;
    }

    public void setChange(FixedPoint experience) {
        this.change = experience.clone();
    }

    public FixedPoint getFinalExperience() {
        return this.original.add(this.change);
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}

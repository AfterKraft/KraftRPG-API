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

import org.apache.commons.lang.Validate;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.ISkill;


public class InsentientRegainHealthEvent extends InsentientEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Insentient healer;
    private final ISkill skill;
    private double amount;
    private boolean cancelled = false;

    public InsentientRegainHealthEvent(Insentient beneficiary, double healAmount, ISkill skill) {
        this(beneficiary, healAmount, skill, null);
    }

    public InsentientRegainHealthEvent(Insentient beneficiary, double healAmount, ISkill skill, Insentient healer) {
        super(beneficiary);
        Validate.notNull(skill, "Cannot create an event with a null Skill!");
        this.amount = healAmount;
        this.skill = skill;
        this.healer = healer;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ISkill getSkill() {
        return this.skill;
    }

    public Insentient getHealer() {
        return this.healer;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}

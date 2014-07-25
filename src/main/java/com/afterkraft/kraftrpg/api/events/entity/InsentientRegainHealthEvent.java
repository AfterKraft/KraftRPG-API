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

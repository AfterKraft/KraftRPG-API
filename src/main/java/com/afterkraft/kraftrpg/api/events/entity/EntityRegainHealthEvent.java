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

import org.bukkit.event.Cancellable;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.skills.ISkill;


public class EntityRegainHealthEvent extends IEntityEvent implements Cancellable {

    private final IEntity healer;
    private final ISkill skill;
    private double amount;
    private boolean cancelled = false;

    public EntityRegainHealthEvent(IEntity beneficiary, double healAmount, ISkill skill) {
        this(beneficiary, healAmount, skill, null);
    }

    public EntityRegainHealthEvent(IEntity beneficiary, double healAmount, ISkill skill, IEntity healer) {
        super(beneficiary);
        this.amount = healAmount;
        this.skill = skill;
        this.healer = healer;
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

    public IEntity getHealer() {
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

}

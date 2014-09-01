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
package com.afterkraft.kraftrpg.api.events.skills;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import org.apache.commons.lang.Validate;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.events.entity.InsentientEvent;
import com.afterkraft.kraftrpg.api.skills.ISkill;


public class SkillCastEvent extends InsentientEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final ISkill skill;
    private double manaCost;
    private double healthCost;
    private double exhaustionCost;
    private ItemStack reagent;
    private boolean cancelled;


    public SkillCastEvent(SkillCaster caster, ISkill skill, double manaCost, double healthCost, double exhaustionCost, ItemStack reagentCost) {
        super(caster);
        Validate.notNull(skill, "Cannot have a null skill!");
        this.skill = skill;
        this.manaCost = manaCost;
        this.healthCost = healthCost;
        this.exhaustionCost = exhaustionCost;
        if (reagentCost != null)
        this.reagent = new ItemStack(reagentCost);
        else
        this.reagent = null;
        this.cancelled = false;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public SkillCaster getCaster() {
        return (SkillCaster) this.getEntity();
    }

    public ISkill getSkill() {
        return this.skill;
    }

    public double getManaCost() {
        return this.manaCost;
    }

    public void setManaCost(double manaCost) {
        this.manaCost = manaCost;
    }

    public double getHealthCost() {
        return this.healthCost;
    }

    public void setHealthCost(double healthCost) {
        this.healthCost = healthCost;
    }

    public double getStaminaCostAsExhaustion() {
        return this.exhaustionCost;
    }

    public void setStaminaCostAsExhaustion(double staminaCost) {
        this.exhaustionCost = staminaCost;
    }

    public double getStaminaCostAsFoodBar() {
        return this.exhaustionCost / 4;
    }

    public void setStaminaCostAsFoodBar(double staminaCost) {
        this.exhaustionCost = staminaCost * 4;
    }

    public ItemStack getItemCost() {
        return this.reagent == null ? null : this.reagent.clone();
    }

    public void setItemCost(ItemStack item) {
        this.reagent = new ItemStack(item);
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

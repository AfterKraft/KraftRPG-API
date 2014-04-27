package com.afterkraft.kraftrpg.api.events.spells;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.SpellCaster;
import com.afterkraft.kraftrpg.api.spells.ISpell;
import com.afterkraft.kraftrpg.api.util.SpellRequirement;

/**
 * @author gabizou
 */
public class SpellCastEvent extends Event implements Cancellable {

    protected static final HandlerList handlers = new HandlerList();

    private final SpellCaster entity;
    private final ISpell spell;
    private int manaCost;
    private double healthCost;
    private SpellRequirement requirement;
    private boolean cancelled;

    public SpellCastEvent(SpellCaster caster, ISpell spell, int manaCost, double healthCost, SpellRequirement requirements) {
        this.entity = caster;
        this.spell = spell;
        this.manaCost = manaCost;
        this.healthCost = healthCost;
        this.requirement = requirements;
        this.cancelled = false;
    }


    public SpellCaster getCaster() {
        return this.entity;
    }

    public ISpell getSpell() {
        return this.spell;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public double getHealthCost() {
        return this.healthCost;
    }

    public void setHealthCost(double healthCost) {
        this.healthCost = healthCost;
    }

    public SpellRequirement getRequirement() {
        return requirement;
    }

    public void setRequirement(SpellRequirement requirement) {
        this.requirement = requirement;
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

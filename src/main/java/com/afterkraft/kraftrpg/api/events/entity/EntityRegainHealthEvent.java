package com.afterkraft.kraftrpg.api.events.entity;

import org.bukkit.event.Cancellable;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.spells.ISpell;

/**
 * @author gabizou
 */
public class EntityRegainHealthEvent extends IEntityEvent implements Cancellable {

    private final IEntity healer;
    private final ISpell spell;
    private double amount;
    private boolean cancelled = false;

    public EntityRegainHealthEvent(IEntity beneficiary, double healAmount, ISpell spell) {
        this(beneficiary, healAmount, spell, null);
    }

    public EntityRegainHealthEvent(IEntity beneficiary, double healAmount, ISpell spell, IEntity healer) {
        super(beneficiary);
        this.amount = healAmount;
        this.spell = spell;
        this.healer = healer;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ISpell getSpell() {
        return this.spell;
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

package com.afterkraft.kraftrpg.api.events.entity.effects;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.effects.IEffect;

/**
 * @author gabizou
 */
public class EffectAddEvent extends Event implements Cancellable {

    private final IEntity entity;
    private boolean isCancelled = false;
    private IEffect effect;

    public EffectAddEvent(IEntity entity, IEffect effect) {
        this.entity = entity;
        this.effect = effect;
    }

    public IEffect getEffect() {
        return this.effect;
    }

    public IEntity getEntity() {
        return this.entity;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }
}

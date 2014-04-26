package com.afterkraft.kraftrpg.api.events.entity.effects;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.effects.IEffect;

/**
 * @author gabizou
 */
public class EffectRemoveEvent extends Event implements Cancellable {

    private boolean isCancelled = false;
    private final IEntity entity;
    private IEffect IEffect;

    public EffectRemoveEvent(IEntity entity, IEffect IEffect) {
        this.entity = entity;
        this.IEffect = IEffect;
    }

    public IEffect getEffect() {
        return this.IEffect;
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

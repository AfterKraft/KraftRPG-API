package com.afterkraft.kraftrpg.api.events.entity.effects;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.effects.Effect;

/**
 * @author gabizou
 */
public class EffectAddEvent extends Event implements Cancellable {

    private boolean isCancelled = false;
    private final IEntity entity;
    private Effect effect;

    public EffectAddEvent(IEntity entity, Effect effect) {
        this.entity = entity;
        this.effect = effect;
    }

    public Effect getEffect() {
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

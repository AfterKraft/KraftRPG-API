package com.afterkraft.kraftrpg.api.events.entity.effects;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.RPGEntity;
import com.afterkraft.kraftrpg.api.entity.effects.RPGEffect;

/**
 * Author: gabizou
 */
public class EffectAddEvent extends Event implements Cancellable {

    private boolean isCancelled = false;

    public EffectAddEvent(RPGEntity entity, RPGEffect RPGEffect) {

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

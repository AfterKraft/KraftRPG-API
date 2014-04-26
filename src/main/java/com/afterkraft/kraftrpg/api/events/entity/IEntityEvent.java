package com.afterkraft.kraftrpg.api.events.entity;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.IEntity;

/**
 * @author gabizou
 */
public class IEntityEvent extends Event {

    protected static final HandlerList handlers = new HandlerList();
    private final IEntity entity;

    public IEntityEvent(IEntity entity) {
        this.entity = entity;
    }

    public IEntity getEntity() {
        return this.entity;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public final HandlerList getHandlers() {
        return handlers;
    }
}

package com.afterkraft.kraftrpg.api.events.entity.champion;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.events.entity.IEntityEvent;

/**
 * @author gabizou
 */
public class ChampionEvent extends IEntityEvent {
    protected static final HandlerList handlers = new HandlerList();

    public ChampionEvent(Champion player) {
        super(player);
    }

    @Override
    public final Champion getEntity() {
        return (Champion) super.getEntity();
    }

    public final Champion getChampion() {
        return this.getEntity();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

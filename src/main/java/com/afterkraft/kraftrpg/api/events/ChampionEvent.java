package com.afterkraft.kraftrpg.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * @author gabizou
 */
public class ChampionEvent extends Event {
    protected static final HandlerList handlers = new HandlerList();
    private final Champion player;

    public ChampionEvent(Champion player) {
        this.player = player;
    }

    public final Champion getChampion() {
        return this.player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public final HandlerList getHandlers() {
        return handlers;
    }
}

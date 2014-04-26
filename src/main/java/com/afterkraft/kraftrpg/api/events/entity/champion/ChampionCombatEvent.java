package com.afterkraft.kraftrpg.api.events.entity.champion;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * @author gabizou
 */
public class ChampionCombatEvent extends ChampionEvent {

    public ChampionCombatEvent(Champion player) {
        super(player);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

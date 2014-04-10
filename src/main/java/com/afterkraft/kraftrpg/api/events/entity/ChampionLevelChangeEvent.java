package com.afterkraft.kraftrpg.api.events.entity;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;

/**
 * @author gabizou
 */
public class ChampionLevelChangeEvent extends ChampionRoleEvent {

    private final int fromLevel;
    private final int toLevel;
    private final boolean isMastering;

    public ChampionLevelChangeEvent(Champion player, Role rpgRole, int from, int to) {
        this(player, rpgRole, from, to, false);
    }

    public ChampionLevelChangeEvent(Champion player, Role rpgRole, int from, int to, boolean isMastering) {
        super(player, rpgRole);
        this.fromLevel = from;
        this.toLevel = to;
        this.isMastering = true;
    }

    public int getFromLevel() {
        return this.fromLevel;
    }

    public int getToLevel() {
        return this.toLevel;
    }

    public boolean isMastering() {
        return this.isMastering;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

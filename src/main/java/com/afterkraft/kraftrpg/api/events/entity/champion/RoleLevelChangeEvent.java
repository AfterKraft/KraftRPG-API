package com.afterkraft.kraftrpg.api.events.entity.champion;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.events.RoleEvent;

/**
 * @author gabizou
 */
public class RoleLevelChangeEvent extends RoleEvent {

    private final int fromLevel;
    private final int toLevel;
    private final boolean isMastering;

    public RoleLevelChangeEvent(Champion player, Role rpgRole, int from, int to) {
        this(player, rpgRole, from, to, false);
    }

    public RoleLevelChangeEvent(Champion player, Role rpgRole, int from, int to, boolean isMastering) {
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

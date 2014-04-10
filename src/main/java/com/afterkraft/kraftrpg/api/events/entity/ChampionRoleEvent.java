package com.afterkraft.kraftrpg.api.events.entity;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;

/**
 * @author gabizou
 */
public class ChampionRoleEvent extends ChampionEvent {

    private final Role rpgRole;

    public ChampionRoleEvent(Champion player, Role rpgRole) {
        super(player);
        this.rpgRole = rpgRole;
    }

    public final Role getRPGRole() {
        return this.rpgRole;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

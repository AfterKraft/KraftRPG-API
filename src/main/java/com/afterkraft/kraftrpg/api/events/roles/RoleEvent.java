package com.afterkraft.kraftrpg.api.events.roles;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.events.entity.champion.ChampionEvent;

/**
 * @author gabizou
 */
public class RoleEvent extends ChampionEvent {

    private final Role rpgRole;

    public RoleEvent(Champion player, Role rpgRole) {
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

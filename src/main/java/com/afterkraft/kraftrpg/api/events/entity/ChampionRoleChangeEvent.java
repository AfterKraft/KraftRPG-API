package com.afterkraft.kraftrpg.api.events.entity;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;

/**
 * @author gabizou
 */
public class ChampionRoleChangeEvent extends ChampionRoleEvent implements Cancellable {

    private final Role toRPGRole;
    private final boolean canCancel;
    private boolean cancelled;

    public ChampionRoleChangeEvent(Champion player, Role fromRPGClass, Role toRPGRole) {
        this(player, fromRPGClass, toRPGRole, true);
    }

    public ChampionRoleChangeEvent(Champion player, Role fromRPGClass, Role toRPGRole, boolean canCancel) {
        super(player, fromRPGClass);
        this.toRPGRole = toRPGRole;
        this.canCancel = canCancel;
        this.cancelled = false;
    }

    public Role getToRPGRole() {
        return this.toRPGRole;
    }

    public Role getFromRPGRole() {
        return this.getRPGRole();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled && this.canCancel;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}

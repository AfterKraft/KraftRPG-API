package com.afterkraft.kraftrpg.api.events.roles;

import org.bukkit.event.Cancellable;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * @author gabizou
 */
public class ExperienceChangeEvent extends RoleEvent implements Cancellable {

    private final FixedPoint original;
    private FixedPoint change;
    private boolean cancelled = false;

    public ExperienceChangeEvent(Champion player, Role role, FixedPoint original, FixedPoint change) {
        super(player, role);
        this.original = original;
        this.change = change;
    }

    public FixedPoint getFromExperience() {
        return this.original;
    }

    public FixedPoint getChange() {
        return this.change;
    }

    public void setChange(FixedPoint experience) {
        this.change = experience;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}

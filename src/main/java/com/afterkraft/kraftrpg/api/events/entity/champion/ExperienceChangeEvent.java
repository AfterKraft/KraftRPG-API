package com.afterkraft.kraftrpg.api.events.entity.champion;

import org.bukkit.event.Cancellable;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.events.RoleEvent;

/**
 * @author gabizou
 */
public class ExperienceChangeEvent extends RoleEvent implements Cancellable {

    private final double from;
    private double to;
    private boolean cancelled = false;

    public ExperienceChangeEvent(Champion player, Role role, double from, double to) {
        super(player, role);
        this.from = from;
        this.to = to;
    }

    public double getFromExperience() {
        return this.from;
    }

    public double getToExperience() {
        return this.to;
    }

    public void setToExperience(double experience) {
        this.to = experience;
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

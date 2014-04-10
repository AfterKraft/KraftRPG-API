package com.afterkraft.kraftrpg.api.entity;

import org.bukkit.Location;

/**
 * @author gabizou
 */
public interface Monster extends IEntity {

    public Location getSpawnLocation();

    public double getBaseDamage();

    public double getModifiedDamage();

    public void setModifiedDamage(double damage);
}

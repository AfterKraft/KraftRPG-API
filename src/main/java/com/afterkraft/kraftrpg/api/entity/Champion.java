package com.afterkraft.kraftrpg.api.entity;

import org.bukkit.entity.Player;

/**
 * @author gabizou
 */
public interface Champion extends IEntity, SpellCaster {

    /**
     * Return the Bukkit {@link Player} object if it is still valid otherwise
     * null
     *
     * @return the bukkit Player object if not null
     */
    public Player getPlayer();

    /**
     * Set the Bukkit {@link Player} object for this Champion. This should
     * automatically call {@link #setEntity(org.bukkit.entity.LivingEntity)} as
     * long as the original UUID matches the new Player's UUID.
     *
     * @param player the Bukkit Player for this Champion to attach to
     */
    public void setPlayer(Player player);

}

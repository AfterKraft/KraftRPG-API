package com.afterkraft.kraftrpg.api.entity;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.entity.party.Party;
import com.afterkraft.kraftrpg.api.entity.roles.ExperienceType;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.spells.ISpell;
import com.afterkraft.kraftrpg.api.util.FixedPoint;
import com.afterkraft.kraftrpg.api.util.SpellRequirement;

/**
 * @author gabizou
 */
public interface Champion extends IEntity, SpellCaster {

    /**
     * Return the Bukkit {@link Player} object if it is still valid otherwise null
     * @return the bukkit Player object if not null
     */
    public Player getPlayer();

    /**
     * Set the Bukkit {@link Player} object for this Champion. This should automatically call
     * {@link #setEntity(org.bukkit.entity.LivingEntity)} as long as the original UUID matches the new Player's UUID.
     * @param player the Bukkit Player for this Champion to attach to
     */
    public void setPlayer(Player player);

}

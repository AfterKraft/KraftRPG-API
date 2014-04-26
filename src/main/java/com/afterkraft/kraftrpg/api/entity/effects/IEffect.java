package com.afterkraft.kraftrpg.api.entity.effects;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Monster;
import com.afterkraft.kraftrpg.api.spells.ISpell;

/**
 * @author gabizou
 */
public interface IEffect {

    /**
     * Returns the associated {@link com.afterkraft.kraftrpg.api.spells.ISpell} that created this effect.
     *
     * @return the Spell that created this effect
     */
    public ISpell getSpell();

    /**
     * Returns this individual Effect's name. (Should be as unique and recognizable as possible).
     * @return the name of this effect.
     */
    public String getName();

    /**
     * Check if this Effect is of a certain EffectType
     *
     * @param queryType the type of effect to query
     * @return true if this Effect is of the queried EffectType
     */
    public boolean isType(EffectType queryType);

    /**
     * Check if this Effect is persistent. A Persistent effect will never expire until the Effect is removed.
     *
     * @return true if this Effect is persistent
     */
    public boolean isPersistent();

    /**
     * Set this effect to be a persisting effect. Persistent effects will never expire until removed by a Spell or
     * plugin.
     *
     * @param persistent set this Effect to be persistent.
     */
    public void setPersistent(boolean persistent);

    /**
     * Add a Bukkit {@link org.bukkit.potion.PotionEffect} to this Effect
     * @param pEffect the PotionEffect to add to this Effect
     * @param faked whether this PotionEffect will produce ambient visual effects on application
     */
    public void addPotionEffect(PotionEffect pEffect, boolean faked);

    /**
     * Add a custom Minecraft Server MobEffect to this Effect. WARNING: This method has different implementations
     * depending on the server version. Please use {@link #addPotionEffect(org.bukkit.potion.PotionEffect, boolean)}
     * in it's stead.
     *
     * @param type the PotionEffectType to add (Used for finding the MobEffect id
     * @param duration the custom duration of the Effect
     * @param strength the level of the Effect
     * @param faked whether this Effect will produce ambient visual effects if applied to a LivingEntity
     */
    public void addMobEffect(PotionEffectType type, int duration, int strength, boolean faked);

    // ----
    // Application Methods
    // ----

    /**
     * @return the applyTime
     */
    public long getApplyTime();

    /**
     * Attempts to apply this effect to the provided IEntity.
     * @param entity this effect is being applied on to.
     */
    public void apply(IEntity entity);

    /**
     * Version specific implementation for applying this effect to an RPGChampion
     * @param player this effect is being applied to.
     */
    public void applyToPlayer(Champion player);

    /**
     * Version specific implementation for applying this effect to an RPGMonster
     *
     * @param monster this effect is being applied to.
     */
    public void applyToMonster(Monster monster);

    /**
     * Attempts to remove this effect from the given IEntity
     * <p>
     * @param entity this effect is being removed by.
     */
    public void remove(IEntity entity);

    /**
     * Version specific implementation for removing this effect from an RPGChampion
     *
     * @param player this effect is being removed from.
     */
    public void removeFromPlayer(Champion player);

    /**
     * Version specific implementation for removing this effect from an RPGMonster
     * @param monster this effect is being removed from.
     */
    public void removeFromMonster(Monster monster);
}

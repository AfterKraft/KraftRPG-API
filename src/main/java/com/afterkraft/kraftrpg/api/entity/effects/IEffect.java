package com.afterkraft.kraftrpg.api.entity.effects;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Mage;
import com.afterkraft.kraftrpg.api.spells.ISpell;

/**
 * @author gabizou
 */
public interface IEffect {

    /**
     * Returns the associated {@link com.afterkraft.kraftrpg.api.spells.ISpell}
     * that created this effect.
     *
     * @return the Spell that created this effect
     */
    public ISpell getSpell();

    /**
     * Returns this individual Effect's name. (Should be as unique and
     * recognizable as possible).
     *
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
     * Check if this Effect is persistent. A Persistent effect will never expire
     * until the Effect is removed.
     *
     * @return true if this Effect is persistent
     */
    public boolean isPersistent();

    /**
     * Set this effect to be a persisting effect. Persistent effects will never
     * expire until removed by a Spell or plugin.
     *
     * @param persistent set this Effect to be persistent.
     */
    public void setPersistent(boolean persistent);

    /**
     * Add a Bukkit {@link org.bukkit.potion.PotionEffect} to this Effect
     *
     * @param pEffect the PotionEffect to add to this Effect
     */
    public void addPotionEffect(PotionEffect pEffect);

    // ----
    // Application Methods
    // ----

    /**
     * @return the applyTime
     */
    public long getApplyTime();

    /**
     * Attempts to apply this effect to the provided {@link
     * com.afterkraft.kraftrpg.api.entity.Mage}.
     *
     * @param mage this effect is being applied on to.
     */
    public void apply(Mage mage);

    /**
     * Attempts to remove this effect from the given IEntity
     * <p/>
     *
     * @param entity this effect is being removed by.
     */
    public void remove(Mage entity);
}

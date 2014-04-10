package com.afterkraft.kraftrpg.api.entity.effects;

import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Monster;
import com.afterkraft.kraftrpg.api.spells.Spell;
import com.afterkraft.kraftrpg.api.spells.SpellArgument;

/**
 * @author gabizou
 */
public interface Effect {

    public Spell<? extends SpellArgument> getSpell();

    /**
     * Returns this individual Effect's name. (Should be as unique and recognizable as possible).
     * @return the name of this effect.
     */
    public String getName();

    public boolean isType(EffectType queryType);

    public boolean isPersistent();

    public void setPersistent(boolean persistent);

    public void addPotionEffect(int id, int duration, int strength, boolean faked);

    public void addPotionEffect(PotionEffect pEffect, boolean faked);

    public void addMobEffect(int id, int duration, int strength, boolean faked);

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

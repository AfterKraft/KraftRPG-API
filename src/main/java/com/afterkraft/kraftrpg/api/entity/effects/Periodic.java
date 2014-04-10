package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Monster;

public interface Periodic {

    /**
     * Returns the last time the effect ticked
     * 
     * @return the time in milliseconds this effect was last ticked
     */
    public long getLastTickTime();

    /**
     * @return the duration of this effect.
     */
    public long getPeriod();

    /**
     * Returns whether the effect is ready for ticking
     * 
     * @return - The ready state of this effect. DO NOT CALL IF THIS IS FALSE
     */
    public boolean isReady();

    /**
     * Ticks this effect on the specified entity.
     * This should be implemented via tickMonster or tickChampion.
     * 
     * @param entity  - The entity this effect is being applied to.
     */
    public void tick(IEntity entity);
    
    /**
     * Ticks the effect on the specified RPGMonster
     * 
     * @param monster - Monster this effect is being applied to
     */
    public void tickMonster(Monster monster);

    /**
     * Ticks the effect on the specified RPGMonster
     * 
     * @param player - Player this effect is being applied to
     */
    public void tickChampion(Champion player);
}

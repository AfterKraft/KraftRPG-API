package com.afterkraft.kraftrpg.api.entity.effects;


import com.afterkraft.kraftrpg.api.entity.RPGEntity;
import com.afterkraft.kraftrpg.api.entity.RPGMonster;
import com.afterkraft.kraftrpg.api.entity.RPGPlayer;

public interface Periodic {

    /**
     * Returns the last time the effect ticked
     * 
     * @return the time in milliseconds this RPGEffect was last ticked
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
     * This should be implemented via tickMonster or tickPlayer.
     * 
     * @param entity  - The entity this effect is being applied to.
     */
    public void tick(RPGEntity entity);
    
    /**
     * Ticks the effect on the specified RPGMonster
     * 
     * @param monster - Monster this effect is being applied to
     */
    public void tickMonster(RPGMonster monster);

    /**
     * Ticks the effect on the specified RPGMonster
     * 
     * @param player - Player this effect is being applied to
     */
    public void tickPlayer(RPGPlayer player);
}

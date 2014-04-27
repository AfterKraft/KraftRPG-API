package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.Mage;

public interface Periodic extends IEffect {

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
     * Ticks this effect on the specified entity. This should be implemented via
     * tickMonster or tickChampion.
     *
     * @param mage - The entity this effect is being applied to.
     */
    public void tick(Mage mage);

}

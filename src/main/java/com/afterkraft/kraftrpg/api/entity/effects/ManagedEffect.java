package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.IEntity;

/**
 * @author gabizou
 */
public interface ManagedEffect {

    /**
     * Return the instance of the {@link com.afterkraft.kraftrpg.api.entity.effects.Effect}
     * @return the Effect
     */
    public Effect getEffect();

    /**
     * Return the IEntity attached to this ManagedEffect
     * @return the IEntity
     */
    public IEntity getEntity();
}

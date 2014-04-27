package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.IEntity;

/**
 * @author gabizou
 */
public interface Managed {

    /**
     * Return the instance of the {@link IEffect}
     *
     * @return the Effect
     */
    public IEffect getEffect();

    /**
     * Return the IEntity attached to this Managed
     *
     * @return the IEntity
     */
    public IEntity getEntity();
}

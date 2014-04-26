package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.entity.IEntity;

/**
 * @author gabizou
 */
public interface Delayed<T extends SpellArgument> {

    public boolean isReady();

    public long startTime();

    public T getArgument();

    public Active<T> getActiveSpell();

    public IEntity getCaster();

}

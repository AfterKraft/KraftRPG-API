package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.entity.IEntity;

/**
 * @author gabizou
 */
public class DelayedSpell<T extends SpellArgument> implements Delayed<T> {

    private final T argument;
    private final long startTime;
    private final IEntity user;
    private final Active<T> spell;
    private final long warmup;

    public DelayedSpell(Active<T> spell, T args, IEntity user, long startTime, long warmup) {
        this.argument = args;
        this.startTime = startTime;
        this.spell = spell;
        this.user = user;
        this.warmup = warmup;
    }

    @Override
    public boolean isReady() {
        return System.currentTimeMillis() > (this.startTime + this.warmup);
    }

    @Override
    public long startTime() {
        return this.startTime;
    }

    @Override
    public T getArgument() {
        return this.argument;
    }

    @Override
    public Active<T> getActiveSpell() {
        return this.spell;
    }

    @Override
    public IEntity getCaster() {
        return this.user;
    }
}

package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.SpellCaster;

/**
 * @author gabizou
 */
public class DelayedSpell<T extends SpellArgument> implements Delayed<T> {

    private final T argument;
    private final long startTime;
    private final SpellCaster user;
    private final Active<T> spell;
    private final long warmup;

    public DelayedSpell(Active<T> spell, T args, SpellCaster caster, long startTime, long warmup) {
        this.argument = args;
        this.startTime = startTime;
        this.spell = spell;
        this.user = caster;
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
    public SpellCaster getCaster() {
        return this.user;
    }
}

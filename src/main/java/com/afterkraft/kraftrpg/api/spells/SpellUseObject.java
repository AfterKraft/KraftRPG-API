package com.afterkraft.kraftrpg.api.spells;

import java.lang.ref.WeakReference;

import com.afterkraft.kraftrpg.api.entity.IEntity;

/**
 * @author gabizou
 */
public final class SpellUseObject<T extends SpellArgument> {

    private final WeakReference<IEntity> entity;
    private final WeakReference<Spell<T>> spell;
    private final WeakReference<T> argument;


    public SpellUseObject(IEntity entity, Spell<T> spell, T argument) {
        this.entity = new WeakReference<IEntity>(entity);
        this.spell = new WeakReference<Spell<T>>(spell);
        this.argument = new WeakReference<T>(argument);
    }

    public IEntity getEntity() {
        return this.entity.get();
    }

    public Spell<T> getSpell() {
        return this.spell.get();
    }

    public T getArgument() {
        return this.argument.get();
    }
}

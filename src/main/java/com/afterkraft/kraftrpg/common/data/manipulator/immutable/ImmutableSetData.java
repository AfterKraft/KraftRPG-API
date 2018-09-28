package com.afterkraft.kraftrpg.common.data.manipulator.immutable;

import com.afterkraft.kraftrpg.common.data.manipulator.mutable.AbstractSetData;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.BaseValue;

import java.util.Set;

public abstract class ImmutableSetData<E, I extends ImmutableSetData<E, I, M>, M extends AbstractSetData<E, M, I>> extends AbstractImmutableSingleData<Set<E>, I, M> {

    protected ImmutableSetData(Set<E> value, Key<? extends BaseValue<Set<E>>> usedKey) {
        super(value, usedKey);
    }

}

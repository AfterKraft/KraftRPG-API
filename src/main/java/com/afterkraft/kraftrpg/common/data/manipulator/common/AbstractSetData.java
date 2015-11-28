package com.afterkraft.kraftrpg.common.data.manipulator.common;

import java.lang.reflect.Modifier;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.SetValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.util.ReflectionUtil;

public abstract class AbstractSetData<E, M extends DataManipulator<M, I>, I extends
        ImmutableDataManipulator<I, M>>
        extends AbstractSingleData<Set<E>, M, I> {

    private final Class<? extends I> immutableClass;

    public AbstractSetData(Class<M> manipulatorClass, Set<E> value,
                          Key<? extends BaseValue<Set<E>>> usedKey,
                          Class<? extends I> immutableClass) {
        super(manipulatorClass, Sets.newHashSet(value), usedKey);
        checkArgument(!Modifier.isAbstract(immutableClass.getModifiers()),
                      "The immutable class cannot be abstract!");
        checkArgument(!Modifier.isInterface(immutableClass.getModifiers()),
                      "The immutable class cannot be an interface!");
        this.immutableClass = immutableClass;
    }

    @Override
    public Set<E> getValue() {
        return Sets.newHashSet(super.getValue());
    }

    @Override
    public M setValue(Set<E> value) {
        return super.setValue(Sets.newHashSet(value));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Value<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory()
                .createSetValue((Key<SetValue<E>>) (Key) this.usedKey, this.getValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public M copy() {
        return (M) ReflectionUtil.createInstance(this.getClass(), getValue());
    }

    @Override
    public I asImmutable() {
        return ReflectionUtil.createInstance(this.immutableClass, getValue());
    }
}


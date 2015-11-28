package com.afterkraft.kraftrpg.common.data.manipulator.common;

import java.lang.reflect.Modifier;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.ListValue;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.afterkraft.kraftrpg.api.util.ReflectionUtil;

public abstract class AbstractListData<E, M extends DataManipulator<M, I>, I extends
        ImmutableDataManipulator<I, M>>
        extends AbstractSingleData<List<E>, M, I> {

    private final Class<? extends I> immutableClass;

    public AbstractListData(Class<M> manipulatorClass, List<E> value,
                                  Key<? extends BaseValue<List<E>>> usedKey,
                                  Class<? extends I> immutableClass) {
        super(manipulatorClass, Lists.newArrayList(value), usedKey);
        checkArgument(!Modifier.isAbstract(immutableClass.getModifiers()), "The immutable class cannot be abstract!");
        checkArgument(!Modifier.isInterface(immutableClass.getModifiers()), "The immutable class cannot be an interface!");
        this.immutableClass = immutableClass;
    }

    @Override
    public List<E> getValue() {
        return Lists.newArrayList(super.getValue());
    }

    @Override
    public M setValue(List<E> value) {
        checkNotNull(value).forEach(Preconditions::checkNotNull);
        return super.setValue(Lists.newArrayList(value));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ListValue<E> getValueGetter() {
        return Sponge.getRegistry().getValueFactory()
                .createListValue((Key<ListValue<E>>) (Key) this.usedKey, getValue());
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

    @Override
    public int compareTo(M o) {
        final List<E> list = o.get(this.usedKey).get();
        return Boolean.compare(list.containsAll(this.getValue()), this.getValue().containsAll(list));
    }
}

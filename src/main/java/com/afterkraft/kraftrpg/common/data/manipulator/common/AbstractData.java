/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.common.data.manipulator.common;


import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

/**
 * Basically, this is the default implementation that automatically delegates <b>ALL</b>
 *
 * @param <T_Manipulator> The generic of the DataManipulator from the API
 * @param <T_Immutable>   The type of the ImmutableDatAManipulator from the API
 */
@SuppressWarnings("unchecked")
public abstract class AbstractData<T_Manipulator extends
        DataManipulator<T_Manipulator, T_Immutable>, T_Immutable
        extends ImmutableDataManipulator<T_Immutable, T_Manipulator>>
        implements DataManipulator<T_Manipulator, T_Immutable> {

    // We need this field for referencing to retrieve the processors as needed. This can never be null
    private final Class<T_Manipulator> manipulatorClass;

    // Ok, so, you're probably asking "Why the hell are you doing this type of hackery?"
    // Answer: Because I'd rather have these abstract functions (read method references)
    // to get and set field values according to the key, and get values based on key
    // instead of using a Value/Data Processor. The advantage of course is that
    // in Java 8, this would all be done with lambda expressions, but since we
    // target Java 6, we can't quite do that... so, this is the compromise.
    //
    // There was a possibility for using annotations, but I (gabizou) decided against
    // it since there's a lot of magic that goes on with it, and there is very little
    // customization when it comes to the method calls for setting/getting the values.
    // The largest issue was implementation. Since most fields are simple to get and
    // set, other values, such as ItemStacks require a bit of finer tuning.
    //
    private final Map<Key<?>, Supplier<Value<?>>> keyValueMap = Maps.newHashMap();
    private final Map<Key<?>, Supplier<?>> keyFieldGetterMap = Maps.newHashMap();
    private final Map<Key<?>, Consumer<Object>> keyFieldSetterMap = Maps.newHashMap();

    protected AbstractData(Class<T_Manipulator> manipulatorClass) {
        this.manipulatorClass = checkNotNull(manipulatorClass);
    }

    /**
     * Simple registration method for the keys to value return methods.
     *
     * <p>Note that this is still going to be usable, but will be made simpler when Java 8 is used,
     * as lambda expressions can reference methods. The update won't actually change these
     * registration methods, but the {@link DataManipulator}s calling these registration methods
     * will become single line simplifications.</p>
     *
     * @param key      The key for the value return type
     * @param function The function for getting the value
     */
    protected final void registerKeyValue(Key<?> key, Supplier<Value<?>> function) {
        this.keyValueMap.put(checkNotNull(key), checkNotNull(function));
    }

    /**
     * Simple registration method for the keys to field getter methods.
     *
     * <p>Note that this is still going to be usable, but will be made simpler when Java 8 is used,
     * as lambda expressions can reference methods. The update won't actually change these
     * registration methods, but the {@link DataManipulator}s calling these registration methods
     * will become single line simplifications.</p>
     *
     * @param key      The key for the value return type
     * @param function The function for getting the field
     */
    protected final void registerFieldGetter(Key<?> key, Supplier<?> function) {
        this.keyFieldGetterMap.put(checkNotNull(key, "The key cannot be null"),
                                   checkNotNull(function, "The function cannot be null"));
    }

    /**
     * Simple registration method for the keys to field setter methods.
     *
     * <p>Note that this is still going to be usable, but will be made simpler when Java 8 is used,
     * as lambda expressions can reference methods. The update won't actually change these
     * registration methods, but the {@link DataManipulator}s calling these registration methods
     * will become single line simplifications.</p>
     *
     * @param key      The key for the value return type
     * @param function The function for setting the field
     */
    @SuppressWarnings("rawtypes")
    protected final <E> void registerFieldSetter(Key<? extends BaseValue<E>> key,
                                                 Consumer<E> function) {
        this.keyFieldSetterMap.put(checkNotNull(key), checkNotNull((Consumer) function));
    }

    protected abstract void registerGettersAndSetters();

    // Beyond this point is all implementation with the getter/setter functions!

    @Override
    public <E> T_Manipulator set(Key<? extends BaseValue<E>> key, E value) {
        checkArgument(supports(key),
                      "This data manipulator doesn't support the following key: " + key.toString());
        this.keyFieldSetterMap.get(key).accept(value);
        return (T_Manipulator) this;
    }

    @Override
    public <E> T_Manipulator transform(Key<? extends BaseValue<E>> key, Function<E, E> function) {
        checkArgument(supports(key));
        this.keyFieldSetterMap.get(key)
                .accept(checkNotNull(function.apply((E) this.keyFieldGetterMap.get(key).get())));
        return (T_Manipulator) this;
    }

    @Override
    public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
        if (!supports(key)) {
            return Optional.empty();
        }
        return Optional.of((E) this.keyFieldGetterMap.get(key).get());
    }

    @Override
    public <E, V extends BaseValue<E>> Optional<V> getValue(Key<V> key) {
        if (!this.keyValueMap.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of((V) checkNotNull(this.keyValueMap.get(key).get()));
    }

    @Override
    public boolean supports(Key<?> key) {
        return this.keyFieldSetterMap.containsKey(checkNotNull(key));
    }

    @Override
    public Set<Key<?>> getKeys() {
        return ImmutableSet.copyOf(this.keyFieldSetterMap.keySet());
    }

    @Override
    public Set<ImmutableValue<?>> getValues() {
        ImmutableSet.Builder<ImmutableValue<?>> builder = ImmutableSet.builder();
        for (Supplier<Value<?>> function : this.keyValueMap.values()) {
            builder.add(checkNotNull(function.get()).asImmutable());
        }
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.manipulatorClass);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AbstractData other = (AbstractData) obj;
        return Objects.equal(this.manipulatorClass, other.manipulatorClass);
    }

}
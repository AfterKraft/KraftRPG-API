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
package com.afterkraft.kraftrpg.api.effect;

import javax.annotation.Nullable;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

public class ImmutableEffectData implements ImmutableDataManipulator<ImmutableEffectData, EffectData> {

    @Override
    public Optional<ImmutableEffectData> fill(DataHolder dataHolder) {
        return null;
    }

    @Override
    public Optional<ImmutableEffectData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return null;
    }

    @Override
    public Optional<ImmutableEffectData> from(DataContainer container) {
        return null;
    }

    @Override
    public <E> Optional<ImmutableEffectData> with(Key<? extends BaseValue<E>> key, E value) {
        return null;
    }

    @Override
    public Optional<ImmutableEffectData> with(BaseValue<?> value) {
        return null;
    }

    @Override
    public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
        return null;
    }

    @Nullable
    @Override
    public <E> E getOrNull(Key<? extends BaseValue<E>> key) {
        return null;
    }

    @Override
    public <E> E getOrElse(Key<? extends BaseValue<E>> key, E defaultValue) {
        return null;
    }

    @Override
    public <E, V extends BaseValue<E>> Optional<V> getValue(Key<V> key) {
        return null;
    }

    @Override
    public boolean supports(Key<?> key) {
        return false;
    }

    @Override
    public boolean supports(BaseValue<?> baseValue) {
        return false;
    }

    @Override
    public ImmutableEffectData copy() {
        return null;
    }

    @Override
    public ImmutableSet<Key<?>> getKeys() {
        return null;
    }

    @Override
    public ImmutableSet<ImmutableValue<?>> getValues() {
        return null;
    }

    @Override
    public EffectData asMutable() {
        return null;
    }

    @Override
    public int compareTo(ImmutableEffectData o) {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return null;
    }
}

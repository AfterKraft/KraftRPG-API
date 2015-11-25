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

import java.util.Set;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.RpgKeys;

public class EffectData implements DataManipulator<EffectData, ImmutableEffectData> {

    private Set<Effect> effects;

    public EffectData(Set<Effect> effects) {
        this.effects = Sets.newHashSet(effects);
    }

    public EffectData() {
        this.effects = Sets.newHashSet();
    }

    @Override
    public Optional<EffectData> fill(DataHolder dataHolder) {
        return null;
    }

    @Override
    public Optional<EffectData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return null;
    }

    @Override
    public Optional<EffectData> from(DataContainer container) {
        return null;
    }

    @Override
    public <E> EffectData set(Key<? extends BaseValue<E>> key, E value) {
        return null;
    }

    @Override
    public EffectData set(BaseValue<?> value) {
        return null;
    }

    @Override
    public EffectData set(BaseValue<?>... values) {
        return null;
    }

    @Override
    public EffectData set(Iterable<? extends BaseValue<?>> values) {
        return null;
    }

    @Override
    public <E> EffectData transform(Key<? extends BaseValue<E>> key, Function<E, E> function) {
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
        return key.equals(RpgKeys.RPG_EFFECTS);
    }

    @Override
    public boolean supports(BaseValue<?> baseValue) {
        return baseValue.getKey().equals(RpgKeys.RPG_EFFECTS);
    }

    @Override
    public EffectData copy() {
        return new EffectData(this.effects);
    }

    @Override
    public ImmutableSet<Key<?>> getKeys() {
        return ImmutableSet.<Key<?>>of(RpgKeys.RPG_EFFECTS);
    }

    @Override
    public ImmutableSet<ImmutableValue<?>> getValues() {
        return null;
    }

    @Override
    public ImmutableEffectData asImmutable() {
        return null;
    }

    @Override
    public int compareTo(EffectData o) {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return null;
    }
}

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
package com.afterkraft.kraftrpg.common.data.manipulator.mutable;

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.SetValue;

import com.google.common.collect.ComparisonChain;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.common.data.manipulator.common.AbstractSetData;
import com.afterkraft.kraftrpg.common.data.manipulator.immutable.ImmutableEffectData;

public class EffectData extends AbstractSetData<Effect, EffectData, ImmutableEffectData> {


    public EffectData(Set<Effect> value) {
        super(EffectData.class, value, RpgKeys.RPG_EFFECTS, ImmutableEffectData.class);
    }

    public SetValue<Effect> effects() {
        return Sponge.getRegistry().getValueFactory()
                .createSetValue(RpgKeys.RPG_EFFECTS, this.getValue());
    }

    @Override
    public int compareTo(EffectData o) {
        return ComparisonChain.start()
                .compare(o.effects().containsAll(this.getValue()),
                         this.getValue().containsAll(o.effects().getAll())).result();
    }

    @Override
    public Optional<EffectData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return Optional.empty();
    }

    @Override
    public Optional<EffectData> from(DataContainer container) {
        return Optional.empty();
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(RpgKeys.RPG_EFFECTS, getValue());
    }
}

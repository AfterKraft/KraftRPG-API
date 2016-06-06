/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.common.data.manipulator.immutable;

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.collection.AbstractImmutableSingleSetData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableSetValue;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.EffectData;

public final class ImmutableEffectData extends AbstractImmutableSingleSetData<Effect,
        ImmutableEffectData, EffectData> {

    public ImmutableEffectData(Set<Effect> value) {
        super(value, RpgKeys.RPG_EFFECTS);
    }

    public ImmutableSetValue<Effect> effects() {
        return Sponge.getRegistry()
                .getValueFactory()
                .createSetValue(RpgKeys.RPG_EFFECTS, this.value)
                .asImmutable();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Optional<ImmutableEffectData> with(Key<? extends BaseValue<E>> key, E value) {
        if (key == RpgKeys.RPG_EFFECTS) {
            return Optional.of(new ImmutableEffectData((Set<Effect>) value));
        }
        return Optional.empty();
    }

    @Override
    public EffectData asMutable() {
        return new EffectData(this.value);
    }

}

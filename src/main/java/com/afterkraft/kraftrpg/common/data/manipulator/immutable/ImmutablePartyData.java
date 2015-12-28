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
package com.afterkraft.kraftrpg.common.data.manipulator.immutable;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.api.entity.party.Party;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.PartyData;

public final class ImmutablePartyData extends AbstractImmutableSingleData<Party, ImmutablePartyData,
        PartyData> {

    public ImmutablePartyData(Party value) {
        super(value, RpgKeys.PARTY);
    }

    public ImmutableValue<Party> party() {
        return Sponge.getRegistry().getValueFactory()
                .createValue(RpgKeys.PARTY, getValue())
                .asImmutable();
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return party();
    }

    @Override
    public <E> Optional<ImmutablePartyData> with(Key<? extends BaseValue<E>> key, E value) {
        if (key == RpgKeys.PARTY) {
            return Optional.of(new ImmutablePartyData((Party) value));
        }
        return Optional.empty();
    }

    @Override
    public PartyData asMutable() {
        return new PartyData(getValue());
    }

    @Override
    public int compareTo(ImmutablePartyData o) {
        return o.party().get().getUniqueId().compareTo(getValue().getUniqueId());
    }
}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBoundedComparableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableBoundedValue;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.SummonData;

public class ImmutableSummonData extends AbstractImmutableBoundedComparableData<Long,
        ImmutableSummonData, SummonData> {

    public ImmutableSummonData(Long value, Long lowerBound,
                               Long upperBound, Long defaultValue) {
        super(value, RpgKeys.SUMMON_DURATION, Long::compare, lowerBound, upperBound, defaultValue);
    }

    /**
     * Gets the time in milliseconds this summon has remaining to live in the world.
     *
     * @return The time left remaining in the world
     */
    public ImmutableBoundedValue<Long> getTimeLeftAlive() {
        return Sponge.getRegistry().getValueFactory().createBoundedValueBuilder(RpgKeys.SUMMON_DURATION)
                .minimum(this.lowerBound)
                .maximum(this.upperBound)
                .defaultValue(this.defaultValue)
                .actualValue(this.getValue())
                .build()
                .asImmutable();
    }

    @Override
    public <E> Optional<ImmutableSummonData> with(Key<? extends BaseValue<E>> key, E value) {
        return null;
    }

    @Override
    public SummonData asMutable() {
        return new SummonData(this.value, this.lowerBound, this.upperBound, this.defaultValue);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }
}

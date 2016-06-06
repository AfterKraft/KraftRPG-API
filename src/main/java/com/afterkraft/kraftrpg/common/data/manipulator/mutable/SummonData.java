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
package com.afterkraft.kraftrpg.common.data.manipulator.mutable;

import java.util.Optional;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBoundedComparableData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.common.data.manipulator.immutable.ImmutableSummonData;

public class SummonData extends AbstractBoundedComparableData<Long, SummonData,
        ImmutableSummonData> {

    public SummonData(Long value, Long lowerBound, Long upperBound,
                      Long defaultValue) {
        super(value, RpgKeys.SUMMON_DURATION, Long::compare, lowerBound, upperBound, defaultValue);
    }

    /**
     * Gets the time in milliseconds this summon has remaining to live in the world.
     *
     * @return The time left remaining in the world
     */
    public MutableBoundedValue<Long> getTimeLeftAlive() {
        return null;
    }

    @Override
    public Optional<SummonData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return null;
    }

    @Override
    public Optional<SummonData> from(DataContainer container) {
        return null;
    }

    @Override
    public SummonData copy() {
        return new SummonData(this.getValue(), this.lowerBound, this.upperBound, this.defaultValue);
    }

    @Override
    public ImmutableSummonData asImmutable() {
        return new ImmutableSummonData(this.getValue(), this.lowerBound, this.upperBound,
                                       this.defaultValue);
    }
}

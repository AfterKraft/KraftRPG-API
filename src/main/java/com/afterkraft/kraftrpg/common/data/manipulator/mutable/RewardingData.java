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
package com.afterkraft.kraftrpg.common.data.manipulator.mutable;

import java.util.Optional;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.api.util.FixedPoint;
import com.afterkraft.kraftrpg.common.data.manipulator.immutable.ImmutableRewardingData;

public class RewardingData extends AbstractSingleData<FixedPoint, RewardingData, ImmutableRewardingData> {

    public RewardingData() {
        this(FixedPoint.ZERO);
    }

    public RewardingData(FixedPoint point) {
        super(point, RpgKeys.REWARDING_EXPERIENCE);
    }

    @Override
    protected Value<?> getValueGetter() {
        return null;
    }

    @Override
    public Optional<RewardingData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return null;
    }

    @Override
    public Optional<RewardingData> from(DataContainer container) {
        return null;
    }

    @Override
    public RewardingData copy() {
        return new RewardingData(this.getValue());
    }

    @Override
    public ImmutableRewardingData asImmutable() {
        return null;
    }


    public int compareTo(RewardingData o) {
        return Long.compare(o.getValue().rawValue(), this.getValue().rawValue());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(RpgKeys.REWARDING_EXPERIENCE, this.getValue());
    }
}

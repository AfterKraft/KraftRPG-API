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

    @Override
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

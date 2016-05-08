package com.afterkraft.kraftrpg.common.data.manipulator.immutable;

import java.util.Optional;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.api.util.FixedPoint;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.RewardingData;

public class ImmutableRewardingData extends AbstractImmutableSingleData<FixedPoint,
        ImmutableRewardingData, RewardingData> {

    public ImmutableRewardingData(FixedPoint value) {
        super(value, RpgKeys.REWARDING_EXPERIENCE);
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return null;
    }

    @Override
    public <E> Optional<ImmutableRewardingData> with(Key<? extends BaseValue<E>> key, E value) {
        return null;
    }

    @Override
    public RewardingData asMutable() {
        return null;
    }

    @Override
    public int compareTo(ImmutableRewardingData o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 0;
    }
}

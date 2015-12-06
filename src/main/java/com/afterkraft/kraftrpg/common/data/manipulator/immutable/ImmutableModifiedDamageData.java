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
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.google.common.collect.ComparisonChain;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.ModifiedDamageData;

/**
 * A component that handles base damage not modified by held items, weapons, armor, buffs, etc.
 *
 * This can be considered as "fist" damage.
 */
public class ImmutableModifiedDamageData
        extends AbstractImmutableData<ImmutableModifiedDamageData, ModifiedDamageData> {

    private final double baseDamage;
    private final double modifiedDamage;
    private final ImmutableValue<Double> baseValue;
    private final ImmutableValue<Double> modifiedValue;

    public ImmutableModifiedDamageData(double baseDamage, double modifiedDamage) {
        this.baseDamage = baseDamage;
        this.modifiedDamage = modifiedDamage;
        this.baseValue = Sponge.getGame().getRegistry()
                .getValueFactory()
                .createValue(RpgKeys.BASE_DAMAGE, this.baseDamage)
                .asImmutable();
        this.modifiedValue = Sponge.getGame().getRegistry()
                .getValueFactory()
                .createValue(RpgKeys.DAMAGE_MODIFIER, this.modifiedDamage)
                .asImmutable();
        registerGetters();
    }

    public ImmutableValue<Double> baseDamge() {
        return this.baseValue;
    }

    public ImmutableValue<Double> modifiedDamage() {
        return this.modifiedValue;
    }

    private double getBaseDamage() {
        return this.baseDamage;
    }

    private double getModifiedDamage() {
        return this.modifiedDamage;
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(RpgKeys.BASE_DAMAGE, this::getBaseDamage);
        registerKeyValue(RpgKeys.BASE_DAMAGE, this::baseDamge);
        registerFieldGetter(RpgKeys.DAMAGE_MODIFIER, this::getModifiedDamage);
        registerKeyValue(RpgKeys.DAMAGE_MODIFIER, this::modifiedDamage);
    }

    @Override
    public <E> Optional<ImmutableModifiedDamageData> with(Key<? extends BaseValue<E>> key, E value) {
        if (key == RpgKeys.BASE_DAMAGE) {
            return Optional.of(new ImmutableModifiedDamageData((Double) value, this.modifiedDamage));
        } else if (key == RpgKeys.DAMAGE_MODIFIER) {
            return Optional.of(new ImmutableModifiedDamageData(this.baseDamage, (Double) value));
        }
        return Optional.empty();
    }

    @Override
    public ModifiedDamageData asMutable() {
        return new ModifiedDamageData(this.baseDamage, this.modifiedDamage);
    }

    @Override
    public int compareTo(ImmutableModifiedDamageData o) {
        return ComparisonChain.start()
                .compare(o.baseDamage, this.baseDamage)
                .compare(o.modifiedDamage, this.modifiedDamage)
                .result();
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(RpgKeys.BASE_DAMAGE, this.baseDamage)
                .set(RpgKeys.DAMAGE_MODIFIER, this.modifiedDamage);
    }
}

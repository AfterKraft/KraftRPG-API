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
package com.afterkraft.kraftrpg.api.entity.component;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.collect.ComparisonChain;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.common.data.manipulator.immutable.ImmutableModifiedDamageData;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.AbstractDataManipulator;

/**
 * A component that handles base damage not modified by held items, weapons, armor, buffs, etc.
 *
 * This can be considered as "fist" damage.
 */
public class ModifiedDamageData extends AbstractDataManipulator<ModifiedDamageData, ImmutableModifiedDamageData> {

    private double modified;
    private double base;

    public ModifiedDamageData(double base, double modified) {
        super(ModifiedDamageData.class);
        this.modified = modified;
        this.base = base;
    }

    public Value<Double> baseDamage() {
        return Sponge.getRegistry().getValueFactory().createValue(RpgKeys.BASE_DAMAGE, this.base);
    }

    public Value<Double> modifiedDamage() {
        return Sponge.getRegistry().getValueFactory().createValue(RpgKeys.DAMAGE_MODIFIER, this
                .modified);
    }

    private double getModified() {
        return this.modified;
    }

    private void setModified(double modified) {
        this.modified = modified;
    }

    private double getBase() {
        return this.base;
    }

    private void setBase(double base) {
        this.base = base;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(RpgKeys.BASE_DAMAGE, this::getBase);
        registerFieldSetter(RpgKeys.BASE_DAMAGE, this::setBase);
        registerKeyValue(RpgKeys.BASE_DAMAGE, this::baseDamage);
        registerFieldGetter(RpgKeys.DAMAGE_MODIFIER, this::getModified);
        registerFieldSetter(RpgKeys.DAMAGE_MODIFIER, this::setModified);
        registerKeyValue(RpgKeys.DAMAGE_MODIFIER, this::modifiedDamage);
    }

    @Override
    public Optional<ModifiedDamageData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return null;
    }

    @Override
    public Optional<ModifiedDamageData> from(DataContainer container) {
        return null;
    }

    @Override
    public ModifiedDamageData copy() {
        return new ModifiedDamageData(this.base, this.modified);
    }

    @Override
    public ImmutableModifiedDamageData asImmutable() {
        return new ImmutableModifiedDamageData(this.base, this.modified);
    }

    @Override
    public int compareTo(ModifiedDamageData o) {
        return ComparisonChain.start()
                .compare(o.base, this.base)
                .compare(o.modified, this.modified)
                .result();
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(RpgKeys.BASE_DAMAGE, this.base)
                .set(RpgKeys.DAMAGE_MODIFIER, this.modified);
    }
}

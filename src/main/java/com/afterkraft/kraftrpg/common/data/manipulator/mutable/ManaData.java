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

import javax.annotation.Nullable;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.collect.ComparisonChain;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.common.data.manipulator.immutable.ImmutableManaData;

public final class ManaData extends AbstractData<ManaData, ImmutableManaData> {

    private int mana;
    private int maxMana;

    public ManaData() {
        this(100, 100);
    }

    public ManaData(int mana, int maxMana) {
        this.mana = mana;
        this.maxMana = maxMana;
        registerGettersAndSetters();
    }

    public Value<Integer> mana() {
        return Sponge.getRegistry().getValueFactory().createValue(RpgKeys.MANA, this.mana);
    }

    public Value<Integer> maxMana() {
        return Sponge.getRegistry().getValueFactory().createValue(RpgKeys.MAX_MANA, this.maxMana);
    }

    private int getMana() {
        return this.mana;
    }

    private void setMana(Integer mana) {
        this.mana = mana;
    }

    private int getMaxMana() {
        return this.maxMana;
    }

    private void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(RpgKeys.MANA, this::getMana);
        registerFieldSetter(RpgKeys.MANA, this::setMana);
        registerKeyValue(RpgKeys.MANA, this::mana);

        registerFieldGetter(RpgKeys.MAX_MANA, this::getMaxMana);
        registerFieldSetter(RpgKeys.MAX_MANA, this::setMaxMana);
        registerKeyValue(RpgKeys.MAX_MANA, this::maxMana);
    }

    @Override
    public Optional<ManaData> fill(DataHolder dataHolder, MergeFunction overlap) {
        @Nullable ManaData manaData = dataHolder.get(ManaData.class).orElse(null);
        final ManaData newData = overlap.merge(this, manaData);
        setMana(newData.mana);
        setMaxMana(newData.maxMana);
        return Optional.of(this);
    }

    @Override
    public Optional<ManaData> from(DataContainer container) {
        if (container.contains(RpgKeys.MAX_MANA.getQuery(), RpgKeys.MANA.getQuery())) {
            setMana(container.getInt(RpgKeys.MANA.getQuery()).get());
            setMaxMana(container.getInt(RpgKeys.MAX_MANA.getQuery()).get());
            return Optional.of(this);
        }
        return Optional.empty();
    }

    @Override
    public ManaData copy() {
        return new ManaData(this.mana, this.maxMana);
    }

    @Override
    public ImmutableManaData asImmutable() {
        return new ImmutableManaData(this.mana, this.maxMana);
    }


    public int compareTo(ManaData o) {
        return ComparisonChain.start()
                .compare(o.mana, this.mana)
                .compare(o.maxMana, this.maxMana)
                .result();
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(RpgKeys.MANA, this.mana)
                .set(RpgKeys.MAX_MANA, this.maxMana);
    }
}

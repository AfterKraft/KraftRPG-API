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
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.google.common.base.Objects;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.ManaData;

public final class ImmutableManaData extends AbstractImmutableData<ImmutableManaData, ManaData> {

    private final int mana;
    private final int maxMana;
    private final ImmutableValue<Integer> manaValue;
    private final ImmutableValue<Integer> maxManaValue;

    public ImmutableManaData(int mana, int maxMana) {
        super();
        this.mana = mana;
        this.maxMana = maxMana;
        this.manaValue = Sponge.getRegistry().getValueFactory()
                .createValue(RpgKeys.MANA, this.mana)
                .asImmutable();
        this.maxManaValue = Sponge.getRegistry().getValueFactory()
                .createValue(RpgKeys.MAX_MANA, this.maxMana)
                .asImmutable();
        registerGetters();
    }

    private int getMana() {
        return this.mana;
    }

    private int getMaxMana() {
        return this.maxMana;
    }

    public ImmutableValue<Integer> mana() {
        return this.manaValue;
    }

    public ImmutableValue<Integer> maxMana() {
        return this.maxManaValue;
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(RpgKeys.MANA, this::getMana);
        registerKeyValue(RpgKeys.MANA, this::mana);

        registerFieldGetter(RpgKeys.MAX_MANA, this::getMaxMana);
        registerKeyValue(RpgKeys.MAX_MANA, this::maxMana);
    }

    @Override
    public <E> Optional<ImmutableManaData> with(Key<? extends BaseValue<E>> key, E value) {
        if (key == RpgKeys.MANA) {
            return Optional.of(new ImmutableManaData((Integer) value, this.maxMana));
        } else if (key == RpgKeys.MAX_MANA) {
            return Optional.of(new ImmutableManaData(this.mana, (Integer) value));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("mana", this.mana)
                .add("maxMana", this.maxMana)
                .add("manaValue", this.manaValue)
                .add("maxManaValue", this.maxManaValue)
                .toString();
    }

    @Override
    public ManaData asMutable() {
        return new ManaData(this.mana, this.maxMana);
    }

    @Override
    public int compareTo(ImmutableManaData o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(RpgKeys.MAX_MANA, this.maxMana)
                .set(RpgKeys.MANA, this.mana);
    }

}

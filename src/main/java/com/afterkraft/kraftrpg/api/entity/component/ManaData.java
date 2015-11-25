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

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.merge.MergeFunction;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.AbstractDataManipulator;

public class ManaData extends AbstractDataManipulator<ManaData, ImmutableManaData> {

    private int mana;
    private int maxMana;

    public ManaData() {
        this(100, 100);
    }

    public ManaData(int mana, int maxMana) {
        super(ManaData.class);
        this.mana = mana;
        this.maxMana = maxMana;
        registerGettersAndSetters();
    }

    private int getMana() {
        return this.mana;
    }

    private void setMana(int mana) {
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
        registerFieldGetter(RpgKeys);
    }

    @Override
    public Optional<ManaData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return null;
    }

    @Override
    public Optional<ManaData> from(DataContainer container) {
        return null;
    }

    @Override
    public ManaData copy() {
        return null;
    }

    @Override
    public ImmutableManaData asImmutable() {
        return null;
    }

    @Override
    public int compareTo(ManaData o) {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return null;
    }
}

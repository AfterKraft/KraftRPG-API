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

import static com.google.common.base.Preconditions.checkNotNull;

import com.afterkraft.kraftrpg.common.data.manipulator.immutable.ImmutableSetData;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractListData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.SetValue;

import java.util.Optional;
import java.util.Set;

@SuppressWarnings("unchecked")
public abstract class AbstractSetData<E, M extends AbstractSetData<E, M, I>, I extends ImmutableSetData<E, I, M>>
    extends AbstractSingleData<Set<E>, M, I>
{

    protected AbstractSetData(Set<E> value, Key<? extends BaseValue<Set<E>>> usedKey) {
        super(value, usedKey);
    }

    @Override
    protected SetValue<E> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createSetValue((Key < SetValue<E>>) this.usedKey, this.getValue());
    }

    @Override
    public <V> Optional<V> get(Key<? extends BaseValue<V>> key) {
        // we can delegate this since we have a direct value check as this is
        // a Single value.
        return key == this.usedKey ? Optional.of((V) this.getValue()) : super.get(key);
    }

    @Override
    public boolean supports(Key<?> key) {
        return checkNotNull(key) == this.usedKey;
    }

    // We have to have this abstract to properly override for generics.
    @Override
    public abstract I asImmutable();

    @Override
    protected Set<E> getValue() {
        return Sets.newHashSet(super.getValue());
    }

    @Override
    protected M setValue(Set<E> value) {
        return super.setValue(Sets.newHashSet(value));
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hashCode(this.getValue());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final AbstractSetData other = (AbstractSetData) obj;
        return Objects.equal(this.getValue(), other.getValue());
    }

}

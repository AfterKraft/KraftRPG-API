/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
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

package com.afterkraft.kraftrpg.common.key;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;

/**
 * Represents a common implementation of a {@link Key} for use within
 * KraftRPG.
 *
 * @param <E> The type of element
 * @param <V> The type of value
 */
public class RpgKey<E, V extends BaseValue<E>> implements Key<V> {

    private final Class<V> valueClass;
    private final DataQuery query;

    /**
     * Constructs a new {@link RpgKey}.
     *
     * @param elementClass The element class
     * @param valueClass The value class
     * @param query The query
     */
    public RpgKey(Class<E> elementClass, Class<V> valueClass, DataQuery query) {
        this.valueClass = checkNotNull(valueClass);
        checkArgument(!query.getParts().isEmpty());
        this.query = query;
    }

    @Override
    public Class<V> getValueClass() {
        return this.valueClass;
    }

    @Override
    public DataQuery getQuery() {
        return this.query;
    }
}

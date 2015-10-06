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

package com.afterkraft.kraftrpg.api;

import java.util.Map;
import java.util.Set;

import static org.spongepowered.api.data.DataQuery.of;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.SetValue;

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.common.key.RpgKey;

@SuppressWarnings("unchecked")
public final class RpgKeys {
    private RpgKeys() {}

    public static final Key<SetValue<Effect>> RPG_EFFECTS = makeSetKey(of("KraftRPG", "Effects"));
    public static final Key<MapValue<String, Effect>> DEMO = makeMapKey(of("Herpington"));


    private static <E> Key<SetValue<E>> makeSetKey(DataQuery query) {
        return new RpgKey<>((Class<Set<E>>) (Class) Set.class,
                            (Class<SetValue<E>>) (Class) SetValue.class, query);
    }

    private static <K, V> Key<MapValue<K, V>> makeMapKey(DataQuery query) {
        return new RpgKey<>((Class<Map<K, V>>) (Class) Map.class,
                            (Class<MapValue<K, V>>) (Class) MapValue.class,
                            query);
    }

}

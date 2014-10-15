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
package com.afterkraft.kraftrpg.api.util;

import java.util.*;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class SerializationUtil {

    public static Map<String, Object> fullySerialize(ConfigurationSerializable obj) {
        Map<String, Object> ret = new HashMap<String, Object>(obj.serialize());

        for (Map.Entry<String, Object> entry : ret.entrySet()) {
            if (entry.getValue() instanceof ConfigurationSerializable) {
                entry.setValue(fullySerialize((ConfigurationSerializable) entry.getValue()));
            }
        }

        ret.put(ConfigurationSerialization.SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(obj.getClass()));

        return ret;
    }

    @SuppressWarnings("unchecked")
    public static Object fullyDeserializeInternal(Object o) {
        if (o instanceof Map) {
            Map<?, ?> inner = (Map<?, ?>) o;
            // Rudimentary type-check to make sure it's a map from String
            Iterator<?> temp = inner.keySet().iterator();
            if (temp.hasNext() && temp.next() instanceof String) {
                return fullyDeserialize((Map<String, Object>) o);
            }
        } else if (o instanceof List) {
            List<Object> inner = (List<Object>) o;
            ListIterator<Object> iter = inner.listIterator();
            while (iter.hasNext()) {
                Object item = iter.next();
                iter.set(fullyDeserializeInternal(item));
            }
        }
        return o;
    }

    public static ConfigurationSerializable fullyDeserialize(Map<String, Object> data) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            entry.setValue(fullyDeserializeInternal(entry.getValue()));
        }

        return ConfigurationSerialization.deserializeObject(data);
    }
}

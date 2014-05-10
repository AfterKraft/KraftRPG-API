/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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

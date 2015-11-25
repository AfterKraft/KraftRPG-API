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
package com.afterkraft.kraftrpg.api.util;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

/**
 * Represents a group of {@link Contextable} objects which may be passed to a
 * service on initialization.
 */
public class Context {

    private final Map<Class<? extends Contextable>, Contextable> data;

    /**
     * Creates a new {@link Context}.
     */
    public Context() {
        this.data = Maps.newConcurrentMap();
    }

    /**
     * Gets a {@link Contextable} object from this context.
     * 
     * @param cls The requested type
     * @param <T> The requested context type
     * @return The object if found
     */
    @SuppressWarnings("unchecked")
    public <T extends Contextable> Optional<T> get(Class<T> cls) {
        Object obj = this.data.get(cls);
        if(obj == null) {
            //No direct match found, fall back to checking isAssignableFrom
            for(Class<? extends Contextable> key: this.data.keySet()) {
                if(cls.isAssignableFrom(key)) {
                    // found a subclass of what we're looking for
                    return Optional.of((T) this.data.get(key));
                }
            }
            if(obj == null) {
                //Failed to match any class
                return Optional.absent();
            }
        }
        return Optional.fromNullable((T) obj);
    }

    /**
     * Gets whether this context contains the given type.
     * 
     * @param cls The requested type
     * @return If the type is contained within this context
     */
    public boolean has(Class<? extends Contextable> cls) {
        return this.data.containsKey(cls);
    }

    /**
     * Puts a {@link Contextable} object into this context. If an object already
     * exists within this context with the same type then it is overwritten with
     * the new object.
     * 
     * @param object The new contextable object
     */
    public void put(Contextable object) {
        this.data.put(object.getClass(), object);
    }

}

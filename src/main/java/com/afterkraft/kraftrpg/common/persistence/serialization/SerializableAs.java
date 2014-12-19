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
package com.afterkraft.kraftrpg.common.persistence.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a class to have a particular key constant that can be used in
 * a {@link com.afterkraft.kraftrpg.common.persistence.data.DataView}.
 * Usually, a {@link DataSerializable} can be directly fetched from a DataView
 * so long as the key is used.
 * <p>The class may be marked as compoundable to signify that the class data
 * can be merged with a parent {@link com.afterkraft.kraftrpg.common.persistence.data.DataView}
 * without creating a new DataView child to the parent.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SerializableAs {

    /**
     * Gets the key to refer to this {@link DataSerializable} when stored in
     * a {@link com.afterkraft.kraftrpg.common.persistence.data.DataView}.
     *
     * @return The key to refer to the target
     */
    String key();

    /**
     * Returns whether this serialized object can be compounded and merged
     * in with a DataView at the current level without creating a child
     * {@link com.afterkraft.kraftrpg.common.persistence.data.DataView}.
     *
     * @return Whether this can be compounded with a parent object
     */
    boolean compoundable() default false;
}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.effect;

import java.util.Set;

import org.spongepowered.api.data.DataSerializable;

import com.afterkraft.kraftrpg.api.entity.Being;

/**
 * Represents a property of the effect. Properties define whether an effect is created by a skill,
 * immediate, or persisting, etc.
 *
 * <p>Note that effect properties do not define whether an effect is damaging, periodic, or imbuing
 * etc.</p>
 *
 * @param <T> The implementing property must be comparable to itself
 */
public interface EffectProperty<T extends EffectProperty<T>> extends Comparable<T>,
        DataSerializable {

    /**
     * Gets the {@link String} identifier of this {@link EffectProperty}. Consider that each
     * instance of an {@link EffectProperty} is commonly created by various plugins, the
     * serialization must be able to re-create the {@link Effect} with all {@link EffectProperty}(s)
     * applied to a {@link Being}.
     *
     * @return The unique identifier of the effect property
     */
    String getId();

    /**
     * Gets all applicable operations of this property.
     *
     * @return The various operations associated with this property
     */
    Set<EffectOperation> getOperations();
}

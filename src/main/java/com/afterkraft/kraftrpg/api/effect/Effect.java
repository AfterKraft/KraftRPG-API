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
package com.afterkraft.kraftrpg.api.effect;

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.text.Text;

import com.afterkraft.kraftrpg.api.effect.operation.ApplyEffectOperation;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * The base of KraftPRG's Effect system. An effect can perform various operations on an {@link
 * Insentient} being. While it is recommended to utilize the {@link Effect} as the standard
 * implementation, various other interfaces have different implementations.
 */
public interface Effect extends DataSerializable {

    /**
     * Returns this individual Effect's name. (Should be as unique and recognizable as possible).
     *
     * @return the name of this effect.
     */
    String getName();

    /**
     * Check if this Effect is of a certain EffectType
     *
     * @param queryType The type of effect to query
     *
     * @return True if this Effect is of the queried EffectType
     */
    boolean isType(EffectType queryType);

    /**
     * Gets the time of application on an {@link Insentient} being.
     *
     * @return The time this effect was applied
     */
    long getApplyTime();

    /**
     * Gets the desired property of this effect.
     *
     * <p>As effects are immutable upon creation, the properties too are immutable once created.
     * </p>
     *
     * @param propertyClass The property class to get
     * @param <T>           The type of effect property
     *
     * @return The effect property, if not {@link Optional#absent()}
     */
    <T extends EffectProperty<?>> Optional<T> getProperty(Class<T> propertyClass);

    /**
     * Gets all applicable operations that are executed on an {@link Insentient} being when this
     * effect is applied.
     *
     * <p>Note that some abstract effects provide basic functions such as applying potion effects.
     * If further customization is needed, implementing an individual function is recommended.</p>
     *
     * @return An unmodifiable set of functions to apply to an insentient being
     */
    Set<ApplyEffectOperation> getApplicationOperations();

    /**
     * Get the message that should be sent to players when this effect is applied
     *
     * @return the message when this effect is applied
     */
    Text getApplyText();


}

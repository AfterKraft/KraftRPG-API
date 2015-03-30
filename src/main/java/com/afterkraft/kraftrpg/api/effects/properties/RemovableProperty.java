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

package com.afterkraft.kraftrpg.api.effects.properties;

import java.util.Set;

import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.text.Text;

import com.afterkraft.kraftrpg.api.effects.EffectOperation;
import com.afterkraft.kraftrpg.api.effects.EffectOperationResult;
import com.afterkraft.kraftrpg.api.effects.EffectProperty;
import com.afterkraft.kraftrpg.api.entity.Insentient;

public interface RemovableProperty extends EffectProperty<RemovableProperty> {

    /**
     * Gets all applicable operations to be executed on an {@link Insentient} being when this effect
     * is removed.
     *
     * <p>Note that some abstract effects provide basic operations such as removing/applying {@link
     * PotionEffect}s. If further customization is needed, implementing an individual operation is
     * recommended.</p>
     *
     * @return An unmodifiable set of functions to apply to an insentient being upon effect removal
     */
    Set<RemoveEffectOperation> getRemovalOperations();

    /**
     * Get the message that should be sent to players when this effect expires
     *
     * @return the message when this effect expires
     */
    Text getRemovalText();

    /**
     * Represents an operation that is executed when an effect is being removed from an {@link
     * Insentient} being. The removal can be called for any reason whatsoever, including but not
     * limited to: {@link ExpiringProperty} effects, {@link TimedProperty} effects, etc.
     */
    interface RemoveEffectOperation extends EffectOperation {


        /**
         * @param being
         *
         * @return
         */
        EffectOperationResult apply(Insentient being);

    }
}

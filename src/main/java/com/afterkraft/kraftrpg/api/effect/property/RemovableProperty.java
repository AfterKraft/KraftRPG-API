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
package com.afterkraft.kraftrpg.api.effect.property;

import java.util.Set;

import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.text.Text;

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.api.effect.EffectProperty;
import com.afterkraft.kraftrpg.api.effect.operation.RemoveEffectOperation;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents an {@link EffectProperty} that is removable where {@link RemoveEffectOperation}s are
 * called upon removal of the {@link Effect} from an {@link Insentient} entity.
 */
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

}

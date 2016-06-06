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
package com.afterkraft.kraftrpg.api.effect.operation;

import com.afterkraft.kraftrpg.api.effect.EffectOperation;
import com.afterkraft.kraftrpg.api.effect.EffectOperationResult;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents an operation of an effect being applied on an {@link Insentient} being.
 *
 * <p>If the operation fails for any of the possible many operations, the effect application is
 * </p>
 */
public interface ApplyEffectOperation extends EffectOperation {

    /**
     * A simple check if the target {@link Insentient} being is able to have the owning effect
     * applied. If any {@link ApplyEffectOperation}s of an effect are incompatible with the
     * target, the effect is not applied.
     *
     * @param being The insentient being the effect is being applied to
     *
     * @return Whether this operation is compatible with the being
     */
    boolean isApplicableTo(Insentient being);

    /**
     * Performs an operation on the {@link Insentient} being when the owning effect is applied.
     *
     * @param being The insentient being
     *
     * @return The operation result
     */
    EffectOperationResult apply(Insentient being);
}

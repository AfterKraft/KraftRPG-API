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

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.api.effect.EffectOperation;
import com.afterkraft.kraftrpg.api.effect.EffectOperationResult;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents a ticked operation performed on an {@link Insentient} being for a recurring
 * effect. The operation may do whatever is necessary.
 */
@FunctionalInterface
public interface TickEffectOperation extends EffectOperation {

    /**
     * Ticks on the insentient being. This will perform an action on the insentient being after
     * the parent {@link Effect} effect has reached a period to "tick" this operation.
     *
     * <p>If for any reason the operation {@link EffectOperationResult#FAIL}s, the operation
     * will attempt to tick after the next period. If the operation result is {@link
     * EffectOperationResult#INVALID}, the effect may be forcibly removed.</p>
     *
     * @param being The insentient being that is being acted upon
     *
     * @return The result of the operation.
     */
    EffectOperationResult tick(Insentient being);
}

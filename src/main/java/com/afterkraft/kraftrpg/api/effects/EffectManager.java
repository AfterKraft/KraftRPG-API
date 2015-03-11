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
package com.afterkraft.kraftrpg.api.effects;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Basic manager for applying, ticking, and removing effects from {@link
 * com.afterkraft.kraftrpg.api.entity.Insentient} It can be used to apply new effects via <code>
 * {@link #manageEffect(com.afterkraft.kraftrpg.api.entity.Insentient, Timed)} </code> and remove
 * current effects via <code> {@link #queueRemoval(com.afterkraft.kraftrpg.api.entity.Insentient,
 * Timed)} </code>
 */
public interface EffectManager extends Manager {

    /**
     * Applies the Timed effect to the Insentient being. Depending on the status of the Insentient
     * being and the effect, the effect may not be applied immediately.
     *
     * @param being  to apply the timed effect on
     * @param effect to apply
     *
     * @throws IllegalArgumentException If the being is null
     * @throws IllegalArgumentException If the effect is null
     */
    void manageEffect(Insentient being, Timed effect);

    /**
     * Queues the removal of the {@link Timed} effect from the {@link Insentient} being.
     *
     * @param being  The being removing the effect
     * @param effect The effect to remove
     */
    void queueRemoval(Insentient being, Timed effect);
}

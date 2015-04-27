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
package com.afterkraft.kraftrpg.api.entity;

import org.spongepowered.api.entity.living.Living;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.effects.Effect;

/**
 * Represents an Insentient being that allows retreival of the being's status and handles the basic
 * {@link Effect}. It is important to note that an Insentient, and it's subclasses, may not always
 * be attached to a {@link Living}. This is why these methods are provided to assure that Effects
 * and Skills may still function and apply themselves to Insentient beings.  It is advisable that
 * the following method may return null: <code> {@link #getEntity()} </code> and therefor any common
 * information retrieval should be performed using the supplied methods instead of assuming the
 * LivingEntity methods.
 */
public interface Insentient extends Being {

    /**
     * Attempts to fetch the attached LivingEntity of this Insentient Being, unless a Living is not
     * attached, to which this will return null.
     *
     * @return the attached LivingEntity, if there is no LivingEntity, then null.
     */
    @Override
    Optional<? extends Living> getEntity();


}

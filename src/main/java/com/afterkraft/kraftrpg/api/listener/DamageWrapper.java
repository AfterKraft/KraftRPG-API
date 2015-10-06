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
package com.afterkraft.kraftrpg.api.listener;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.event.cause.Cause;

/**
 * This is a simple wrapper for the damage dealt to an entity. It wraps the various necessary
 * information required to handle RpgCommon events.
 */
public class DamageWrapper {

    final Cause originalCause;
    final double originalDamage;
    final double modifiedDamage;
    final Cause modifiedCause;

    public DamageWrapper(Cause originalCause, double originalDamage,
                         double modifiedDamage,
                         Cause modifiedCause) {
        checkNotNull(originalCause);
        checkNotNull(modifiedCause);
        this.originalCause = originalCause;
        this.originalDamage = originalDamage;
        this.modifiedDamage = modifiedDamage;
        this.modifiedCause = modifiedCause;
    }

    public final Cause getOriginalCause() {
        return this.originalCause;
    }

    public final double getOriginalDamage() {
        return this.originalDamage;
    }

    public final double getModifiedDamage() {
        return this.modifiedDamage;
    }

    public final Cause getModifiedCause() {
        return this.modifiedCause;
    }

}

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
package com.afterkraft.kraftrpg.api.entity.combat;

/**
 * Reasons for when an Insentient being has left combat with any other Insentient being.
 */
public enum LeaveCombatReason {
    /**
     * Normal death by fall, fire, suffocation etc.
     */
    DEATH,
    /**
     * Death by suicide, defined by the circumstance of the death.
     */
    SUICIDE,
    /**
     * Leaving combat due to an error on the server or KraftRPG
     */
    ERROR,
    /**
     * Due to an expiration of the combat timer with another entity
     */
    TIMED,
    /**
     * When the champion logs off the server
     */
    LOGOUT,
    /**
     * When the champion is kicked from the server
     */
    KICK,
    /**
     * When the target has died
     */
    TARGET_DEATH,
    /**
     * When the target has logged off
     */
    TARGET_LOGOUT,
    /**
     * Customized reason, possibly third party plugins.
     */
    CUSTOM,

}

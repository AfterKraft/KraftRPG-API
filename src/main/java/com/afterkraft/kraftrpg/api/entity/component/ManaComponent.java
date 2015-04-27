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
package com.afterkraft.kraftrpg.api.entity.component;

public interface ManaComponent extends Component<ManaComponent> {

    /**
     * Get the current mana this being has.
     *
     * @return current mana
     */
    int getMana();

    /**
     * Set the total mana of the being.
     *
     * @param mana this being should have.
     *
     * @throws IllegalArgumentException If the mana is negative
     */
    void setMana(int mana);

    /**
     * Gets the current max mana
     *
     * @return the max mana for this being
     */
    int getMaxMana();

    /**
     * Sets the current maximum mana for this being.
     *
     * @param mana as the maximum
     *
     * @throws IllegalArgumentException If the new max mana is negative or zero
     */
    void setMaxMana(int mana);

}

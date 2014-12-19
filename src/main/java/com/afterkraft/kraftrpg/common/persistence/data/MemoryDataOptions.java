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
package com.afterkraft.kraftrpg.common.persistence.data;

import static com.google.common.base.Preconditions.checkArgument;

public class MemoryDataOptions implements DataOptions {

    private final DataContainer container;
    private char pathSeparator = '.';

    protected MemoryDataOptions(MemoryDataContainer container) {
        this.container = container;
    }

    @Override
    public char getPathSeparator() {
        return this.pathSeparator;
    }

    @Override
    public DataOptions setPathSeparator(char separator) {
        checkArgument(separator != '\u0000', "Cannot set an empty separator.");
        this.pathSeparator = separator;
        return this;
    }

    @Override
    public DataContainer getContainer() {
        return this.container;
    }
}

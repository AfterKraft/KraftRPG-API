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
package com.afterkraft.kraftrpg.api;

/**
 * Thrown when a cyclic dependency is detected in a {@link com.afterkraft.kraftrpg.api.util.DirectedGraph}.
 */
public class CircularDependencyException extends RuntimeException {
    private static final long serialVersionUID = 6924360924388448810L;

    /**
     * Creates a new {@link CircularDependencyException}.
     */
    public CircularDependencyException() {
        super();
    }

    /**
     * Creates a new {@link CircularDependencyException} with the provided message.
     *
     * @param message The message to provide with the exception
     */
    public CircularDependencyException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link CircularDependencyException} with the provided message and cause.
     *
     * @param message The message to provide with the exception
     * @param cause   The cause
     */
    public CircularDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new {@link CircularDependencyException} with the provided cause.
     *
     * @param cause The cause
     */
    public CircularDependencyException(Throwable cause) {
        super(cause);
    }

}

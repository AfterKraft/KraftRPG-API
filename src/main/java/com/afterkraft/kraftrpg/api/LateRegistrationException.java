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
package com.afterkraft.kraftrpg.api;

/**
 * Registration that failed due to the KraftRPG plugin having completed loading.
 */
public class LateRegistrationException extends RuntimeException {
    private static final long serialVersionUID = -3147116935352263037L;

    /**
     * Creates a new {@link LateRegistrationException}.
     */
    public LateRegistrationException() {
        super();
    }

    /**
     * Creates a new {@link LateRegistrationException} to be thrown.
     *
     * @param message The message to include in the exception
     */
    public LateRegistrationException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link LateRegistrationException} to be thrown.
     *
     * @param cause The cause
     */
    public LateRegistrationException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new {@link LateRegistrationException} to be thrown.
     *
     * @param message The message to include in the exception
     * @param cause   The cause
     */
    public LateRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}

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

package com.afterkraft.kraftrpg.common.skills;

import com.afterkraft.kraftrpg.api.skills.SkillArgument;

/**
 * Default abstract implementation of {@link SkillArgument}.
 *
 * @param <T> The type of argument to return and utilize
 */
public abstract class AbstractSkillArgument<T> implements SkillArgument<T> {
    private final boolean required;
    protected boolean present;

    /**
     * Creates a new skill argument
     */
    protected AbstractSkillArgument() {
        this(false);
    }

    /**
     * Creates a new skill argument with a requirement for casting.
     *
     * @param required Whether the argument is required for skill casting
     */
    protected AbstractSkillArgument(boolean required) {
        this.required = required;
    }

    @Override
    public boolean isOptional() {
        return !this.required;
    }

    @Override
    public boolean isPresent() {
        return this.present;
    }

    @Override
    public void setPresent(boolean present) {
        this.present = present;
    }
}

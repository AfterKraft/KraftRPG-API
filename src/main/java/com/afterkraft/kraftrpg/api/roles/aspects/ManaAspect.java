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

package com.afterkraft.kraftrpg.api.roles.aspects;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Optional;

public final class ManaAspect implements ResourceAspect<Integer> {
    private final int baseMana;
    private final int baseManaRegen;
    private final int manaPerLevel;
    private final int manaRegenPerLevel;

    private ManaAspect(ManaAspectBuilder builder) {
        this.baseMana = builder.baseMana;
        this.baseManaRegen = builder.baseManaRegen;
        this.manaPerLevel = builder.manaPerLevel;
        this.manaRegenPerLevel = builder.manaRegenPerLevel;
    }

    @Override
    public Integer getBaseResource() {
        return this.baseMana;
    }

    @Override
    public Integer getBaseResourceRegeneration() {
        return this.baseManaRegen;
    }

    @Override
    public Integer getResourceIncreasePerLevel() {
        return this.manaPerLevel;
    }

    @Override
    public Integer getResourceRegenerationIncreasePerLevel() {
        return this.manaRegenPerLevel;
    }

    @Override
    public Optional<Integer> getResourceAtLevel(int level) {
        checkArgument(level > 0);
        return Optional.of(this.baseMana + (this.manaPerLevel * (level -1)));
    }

    public static final class ManaAspectBuilder {
        int baseMana;
        int baseManaRegen;
        int manaPerLevel;
        int manaRegenPerLevel;

        private ManaAspectBuilder() {

        }




    }
}

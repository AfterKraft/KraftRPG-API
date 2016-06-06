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
package com.afterkraft.kraftrpg.api.role.aspect;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Represents a mana aspect
 */
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

    /**
     * Gets a new builder for creating a {@link ManaAspect}.
     *
     * @return The mana builder
     */
    public static ManaAspectBuilder builder() {
        return new ManaAspectBuilder();
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
        return Optional.of(this.baseMana + (this.manaPerLevel * (level - 1)));
    }

    /**
     * Represents a builder for {@link ManaAspect}s.
     */
    public static final class ManaAspectBuilder {
        int baseMana;
        int baseManaRegen;
        int manaPerLevel;
        int manaRegenPerLevel;

        private ManaAspectBuilder() {

        }

        /**
         * Sets the base mana.
         *
         * @param mana The base mana
         *
         * @return This builder, for chaining
         */
        public ManaAspectBuilder baseMana(int mana) {
            checkArgument(mana > 0);
            this.baseMana = mana;
            return this;
        }

        /**
         * Sets the base mana regeneration.
         *
         * @param manaRegen The base mana regeneration
         *
         * @return This builder, for chaining
         */
        public ManaAspectBuilder baseManaRegen(int manaRegen) {
            checkArgument(manaRegen > 0);
            this.baseManaRegen = manaRegen;
            return this;
        }

        /**
         * Sets the mana increase per level.
         *
         * <p>Mana increases can be zero or greater, but never less than zero.</p>
         *
         * @param manaPerLevel The increase per level
         *
         * @return This builder, for chaining
         */
        public ManaAspectBuilder manaPerLevel(int manaPerLevel) {
            checkArgument(manaPerLevel >= 0);
            this.manaPerLevel = manaPerLevel;
            return this;
        }

        /**
         * Sets the mana regeneration increase per level.
         *
         * @param manaRegenPerLevel The mana regeneration increase per level
         *
         * @return This builder, for chaining
         */
        public ManaAspectBuilder manaRegenPerLevel(int manaRegenPerLevel) {
            checkArgument(manaRegenPerLevel >= 0);
            this.manaRegenPerLevel = manaRegenPerLevel;
            return this;
        }

        /**
         * Creates a new {@link ManaAspect} based on the current state of this builder.
         *
         * @return The newly created mana aspect
         */
        public ManaAspect build() {
            checkState(this.baseMana > 0);
            checkState(this.baseManaRegen >= 0);
            return new ManaAspect(this);
        }

        /**
         * Resets this builder to a clean state.
         *
         * @return This builder, for chaining
         */
        public ManaAspectBuilder reset() {
            this.baseMana = 0;
            this.baseManaRegen = 0;
            this.manaPerLevel = 0;
            this.manaRegenPerLevel = 0;
            return this;
        }
    }
}

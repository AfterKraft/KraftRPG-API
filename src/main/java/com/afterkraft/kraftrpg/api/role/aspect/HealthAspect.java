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

import com.afterkraft.kraftrpg.api.role.Role;
import org.spongepowered.api.data.DataContainer;

/**
 * Represents a {@link ResourceAspect} for a role that involves health at specific levels and
 * benefits. Note that normally, {@link Role.RoleType#ADDITIONAL} {@link Role}s are
 */
public final class HealthAspect implements ResourceAspect<Double> {

    private final double baseHealth;
    private final double healthPerLevel;

    private HealthAspect(HealthAspectBuilder builder) {
        this.baseHealth = builder.baseHealth;
        this.healthPerLevel = builder.healthPerLevel;
    }

    /**
     * Gets a new builder for building a {@link HealthAspect}.
     *
     * @return A new builder
     */
    public static HealthAspectBuilder builder() {
        return new HealthAspectBuilder();
    }

    @Override
    public Double getBaseResource() {
        return getBaseHealth();
    }

    /**
     * Gets the health at level 0.
     *
     * @return The health at level 0
     */
    public double getBaseHealth() {
        return this.baseHealth;
    }

    @Override
    public Double getBaseResourceRegeneration() {
        return 0D;
    }

    @Override
    public Double getResourceIncreasePerLevel() {
        return this.getHealthIncrasePerLevel();
    }

    /**
     * Gets the health increase per level
     *
     * @return The health increase per level
     */
    public double getHealthIncrasePerLevel() {
        return this.healthPerLevel;
    }

    @Override
    public Double getResourceRegenerationIncreasePerLevel() {
        return 0D;
    }

    @Override
    public Optional<Double> getResourceAtLevel(int level) {
        return this.getHealthAtLevel(level);
    }

    /**
     * Gets the health available at the desired level.
     *
     * @param level The level
     *
     * @return The health at the desired level, if available
     */
    public Optional<Double> getHealthAtLevel(int level) {
        checkArgument(level > 1);
        return Optional.of(this.baseHealth
                                   + (this.healthPerLevel * (level - 1)));
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return null;
    }

    /**
     * Represents a builder for an Experience aspect
     */
    public static class HealthAspectBuilder {
        double baseHealth;
        double healthPerLevel;

        private HealthAspectBuilder() {

        }

        /**
         * Sets the given resource base value at level zero.
         *
         * @param minimum The minimum value
         *
         * @return This builder for chaining
         */
        public HealthAspectBuilder setHealthAtZero(double minimum) {
            checkArgument(minimum > 0);
            this.baseHealth = minimum;
            return this;
        }

        /**
         * Sets the resource increase per level.
         *
         * @param increase The amount to increase
         *
         * @return This builder for chaining
         */
        public HealthAspectBuilder setHealthPerLevel(double increase) {
            checkArgument(increase >= 0, "Health increase per level can "
                    + "not be negative!");
            this.healthPerLevel = increase;
            return this;
        }


        /**
         * Clears this builder of all settings ready for new data.
         *
         * @return This bulider for chaining
         */
        public HealthAspectBuilder reset() {
            this.baseHealth = 0;
            this.healthPerLevel = 0;
            return this;
        }

        /**
         * Builds a new {@link ExperienceAspect}.
         *
         * @return The new ExperienceAspect
         */
        public HealthAspect build() {
            checkState(this.baseHealth > 0);
            return new HealthAspect(this);
        }
    }
}

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

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import com.afterkraft.kraftrpg.api.role.ExperienceType;
import com.afterkraft.kraftrpg.api.role.RoleAspect;

/**
 * Represents a {@link RoleAspect} that can determine whether a role may gain specific experience
 * types.
 */
public final class ExperienceAspect implements RoleAspect {
    private final ExperienceType[] experienceTypes;

    private ExperienceAspect(ExperienceAspectBuilder builder) {
        this.experienceTypes = builder.experienceTypes
                .toArray(new ExperienceType[builder.experienceTypes.size()]);
    }

    /**
     * Checks if this Role can gain experience from the provided type
     *
     * @param type The type of experience to check
     *
     * @return True if the type of experience can be gained
     */
    boolean canGainExperience(ExperienceType type) {
        for (ExperienceType experienceType : this.experienceTypes) {
            if (type.equals(experienceType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a list of accepted experience types.
     *
     * @return A list of accepted experience types
     */
    List<ExperienceType> getAcceptedTypes() {
        return ImmutableList.<ExperienceType>builder()
                .add(this.experienceTypes).build();
    }

    /**
     * Represents a builder for an Experience aspect
     */
    public static class ExperienceAspectBuilder {

        List<ExperienceType> experienceTypes = Lists.newArrayList();

        private ExperienceAspectBuilder() {

        }

        /**
         * Adds the specified ItemType type to the allowed armor for this role. Armor can restricted
         * to different roles.
         *
         * @param type Type of armor material
         *
         * @return This builder for chaining
         */
        @SuppressWarnings("unused")
        public ExperienceAspectBuilder allowExperience(ExperienceType type) {
            checkNotNull(type);
            this.experienceTypes.add(type);
            return this;
        }


        /**
         * Clears this builder of all settings ready for new data.
         *
         * @return This bulider for chaining
         */
        public ExperienceAspectBuilder reset() {
            this.experienceTypes = Lists.newArrayList();
            return this;
        }

        /**
         * Builds a new {@link ExperienceAspect}.
         *
         * @return The new ExperienceAspect
         */
        public ExperienceAspect build() {
            return new ExperienceAspect(this);
        }
    }
}

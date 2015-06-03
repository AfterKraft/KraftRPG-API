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
package com.afterkraft.kraftrpg.api.role.aspect;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.role.RoleAspect;
import com.afterkraft.kraftrpg.api.skill.Skill;

/**
 * Represents a {@link RoleAspect} that limits the use of skills.
 */
public final class RestrictedSkillAspect implements RoleAspect {
    private final String[] restrictedSkills;

    private RestrictedSkillAspect(RestrictedSkillAspectBuilder builder) {
        this.restrictedSkills = builder.restrictedSkills
                .toArray(new String[builder.restrictedSkills.size()]);
    }


    /**
     * Creates a new {@link RestrictedSkillAspectBuilder} for use.
     *
     * @return A new builder
     */
    public static RestrictedSkillAspectBuilder builder() {
        return new RestrictedSkillAspectBuilder();
    }

    /**
     * Returns whether this aspect limits the use of the given skill.
     *
     * @param skill The skill to check
     *
     * @return True if the skill is restricted
     */
    public boolean isSkillRestricted(Skill skill) {
        for (String skillName : this.restrictedSkills) {
            if (skillName.equalsIgnoreCase(skill.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a list of all restricted skills by this aspect.
     *
     * @return A list of skills restricted by this aspect
     */
    List<Skill> getRestrictedSkills() {
        ImmutableList.Builder<Skill> builder = ImmutableList.builder();
        for (String skillName : this.restrictedSkills) {
            Optional<Skill> optional = RpgCommon.getPlugin()
                    .getSkillManager().getSkill(skillName);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return builder.build();
    }

    /**
     * Represents a standard builder for a {@link RestrictedSkillAspect}.
     */
    public static final class RestrictedSkillAspectBuilder {
        List<String> restrictedSkills = Lists.newArrayList();

        /**
         * Adds a skill to the restricted list. Skills in the restricted list are uncastable while a
         * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} has this role active.
         *
         * @param skill The skill to restrict
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException If the skill is already in the granted skills list
         */
        public RestrictedSkillAspectBuilder addRestirctedSkill(Skill skill) {
            checkNotNull(skill);
            this.restrictedSkills.add(skill.getName());
            return this;
        }

        /**
         * Removes a skill from the restricted list. Skills in the restricted list are uncastable
         * while a {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} has this role active.
         *
         * @param skill The skill to un-restrict
         *
         * @return This builder for chaining
         */
        public RestrictedSkillAspectBuilder removeRestrictedSkill(
                Skill skill) {
            checkNotNull(skill);
            this.restrictedSkills.remove(skill.getName());
            return this;
        }

        /**
         * Builds the desired {@link RestrictedSkillAspect}.
         *
         * @return The newly created restricted skill aspect
         */
        public RestrictedSkillAspect build() {
            return new RestrictedSkillAspect(this);
        }

        /**
         * Resets this builder to a clean state.
         *
         * @return This builder, for chaining
         */
        public RestrictedSkillAspectBuilder reset() {
            this.restrictedSkills = Lists.newArrayList();
            return this;
        }
    }
}

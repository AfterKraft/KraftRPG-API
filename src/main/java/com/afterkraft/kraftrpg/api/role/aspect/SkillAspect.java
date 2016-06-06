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
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.DataView;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.role.RoleAspect;
import com.afterkraft.kraftrpg.api.role.RoleSkill;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.afterkraft.kraftrpg.api.skill.SkillSetting;

/**
 * Represents a {@link RoleAspect} that deals specifically with {@link Skill} granting and usage.
 */
public final class SkillAspect implements RoleAspect {
    private final RoleSkill[] skills;

    private SkillAspect(SkillAspectBuilder builder) {
        this.skills = builder.skills
                .toArray(new RoleSkill[builder.skills.size()]);
    }

    /**
     * Creates a new {@link SkillAspectBuilder} for use.
     *
     * @return A new builder
     */
    public static SkillAspectBuilder builder() {
        return new SkillAspectBuilder();
    }

    /**
     * Checks if this role has the defined {@link Skill} at any level
     *
     * @param skill to check
     *
     * @return true if the skill is defined
     */
    public boolean hasSkill(Skill skill) {
        return getRoleSkill(skill).isPresent();
    }

    /**
     * Gets the underlying {@link RoleSkill} for the given {@link Skill}.
     *
     * @param skill The skill to get the configuration of
     *
     * @return The role skill configuration
     */
    public Optional<RoleSkill> getRoleSkill(Skill skill) {
        for (RoleSkill roleSkill : this.skills) {
            if (skill.getName().equals(roleSkill.getSkillName())) {
                return Optional.of(roleSkill);
            }
        }
        return Optional.empty();
    }

    /**
     * Check if the specified skill is granted at the specified level, if not before the level.
     *
     * @param skill The skill to query
     * @param level The level to check
     *
     * @return True if the skill is granted at the level or before
     */
    public boolean hasSkillAtLevel(Skill skill, int level) {
        Optional<RoleSkill> roleSkillOptional = getRoleSkill(skill);
        return roleSkillOptional.isPresent()
                && roleSkillOptional.get().getLevel() <= level;
    }

    /**
     * Returns the level required for gaining the specified ISkill.
     *
     * @param skill To check
     *
     * @return The level at which the skill is granted, if available
     */
    public Optional<Integer> getLevelRequired(Skill skill) {
        Optional<RoleSkill> roleSkillOptional = getRoleSkill(skill);
        if (roleSkillOptional.isPresent()) {
            return Optional.of(roleSkillOptional.get().getLevel());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Return a copy of the skill configuration for the specified skill at the desired level, if the
     * skill is granted at the specified level or before.
     *
     * @param skill to get the configuration of
     * @param level to check if the skill is granted at that level
     *
     * @return a copy of the configuration section
     */
    public Optional<DataView> getSkillConfigIfAvailable(Skill skill, int level) {
        Optional<RoleSkill> roleSkillOptional = getRoleSkill(skill);
        if (roleSkillOptional.isPresent()) {
            if (roleSkillOptional.get().getLevel() <= level) {
                return Optional.<DataView>of(
                        roleSkillOptional.get().getConfig());
            }
        }
        return Optional.empty();
    }

    /**
     * Creates an immutable set of all defined skills for this Role.
     *
     * @return An immutable set of all defined skills
     */
    public List<Skill> getAllSkills() {
        ImmutableList.Builder<Skill> builder = ImmutableList.builder();
        for (RoleSkill roleSkill : this.skills) {
            Optional<Skill> optional = RpgCommon.getSkillManager()
                    .getSkill(roleSkill.getSkillName());
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return builder.build();

    }

    /**
     * Gets an immutable set of {@link Skill}s that are granted below or at the specified level
     *
     * @param level at which the skills have been granted
     *
     * @return the skills granted up to the specified level.
     * @throws IllegalArgumentException if the level is negative
     */
    public List<Skill> getAllSkillsAtLevel(int level) {
        ImmutableList.Builder<Skill> builder = ImmutableList.builder();
        for (RoleSkill roleSkill : this.skills) {
            if (roleSkill.getLevel() >= level) {
                Optional<Skill> optional = RpgCommon.getSkillManager()
                        .getSkill(roleSkill.getSkillName());
                if (optional.isPresent()) {
                    builder.add(optional.get());
                }
            }
        }
        return builder.build();
    }

    /**
     * A builder for a new {@link SkillAspect}.
     */
    public static final class SkillAspectBuilder {
        List<RoleSkill> skills = Lists.newArrayList();

        private SkillAspectBuilder() {

        }

        /**
         * Add a {@link Skill} to be granted at with the specified ConfigurationSection that
         * dictates the level requirements, reagents, skill dependencies amongst other things
         *
         * @param skill   to define
         * @param section defining the settings for the skill being added to this Role
         *
         * @return The builder for chaining
         * @throws IllegalArgumentException if the skill level node is not defined correctly
         * @throws IllegalArgumentException if the skill is listed in the restricted skills
         */
        public SkillAspectBuilder addRoleSkill(Skill skill, DataView section) {
            checkNotNull(skill);
            checkNotNull(section);
            checkArgument(!skill.getName().isEmpty(),
                          "Cannot have an empty Skill name!");
            checkArgument(section.getInt(SkillSetting.LEVEL.node()).isPresent(),
                          "Level not specified in the skill configuration!");
            this.skills.add(new RoleSkill(skill.getName(), section));
            return this;
        }

        /**
         * Resets this builder to a blank state.
         *
         * @return This builder, for chaining
         */
        public SkillAspectBuilder reset() {
            this.skills = Lists.newArrayList();
            return this;
        }

        /**
         * Creates a new {@link SkillAspect} ready for use.
         *
         * <p>Note that skill aspects are immutable by default.</p>
         *
         * @return The newly created SkillAspect
         */
        public SkillAspect build() {
            return new SkillAspect(this);
        }
    }
}

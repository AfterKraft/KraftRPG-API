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
package com.afterkraft.kraftrpg.api.roles;


import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataView;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.skills.Skill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;
import com.afterkraft.kraftrpg.api.util.DataUtil;

/**
 * Representation for a Skill and an associated MemoryConfiguration for that skill. This is a simple
 * datastructure utilized in Roles.
 */
public final class RoleSkill {
    private String skill;
    private DataContainer section;

    /**
     * Creates a representation of a skill with a sepecific configuration for a role.
     *
     * @param skill   The skill being configured
     * @param section The configuration
     */
    public RoleSkill(String skill, DataView section) {
        checkNotNull(skill);
        checkNotNull(section);
        this.skill = skill;
        Optional<DataContainer> optional = DataUtil.containerFromExisting(
                section);
        checkArgument(optional.isPresent(), "Cannot have an invalid "
                + "configuration for a role skill!");
        this.section = optional.get();
    }

    /**
     * Checks if this {@link RoleSkill} is equal to the given {@link Skill}.
     *
     * @param other The other skill to check
     *
     * @return True if the skill is the same as the stored skill
     */
    public boolean skillEquals(Skill other) {
        return this.skill.equalsIgnoreCase(other.getName());

    }

    /**
     * Gets the configured skill name.
     *
     * @return The configured skill name
     */
    public String getSkillName() {
        return this.skill;
    }

    /**
     * Gets the level at which the skill is made available.
     *
     * @return The level at which the skill is available
     */
    public int getLevel() {
        return this.section.getInt(SkillSetting.LEVEL.node()).get();
    }

    /**
     * Gets the desired {@link DataContainer} configuration for the skill.
     *
     * @return The desired skill configuration for the role
     */
    public DataContainer getConfig() {
        return DataUtil.containerFromExisting(this.section).get();
    }
}

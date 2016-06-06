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
package com.afterkraft.kraftrpg.api.role;


import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import com.afterkraft.kraftrpg.api.RpgQueries;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.afterkraft.kraftrpg.api.skill.SkillSetting;

/**
 * Representation for a Skill and an associated MemoryConfiguration for that skill. This is a simple
 * datastructure utilized in Roles.
 */
public final class RoleSkill implements DataSerializable {
    private String skill;
    private DataContainer section;

    /**
     * Creates a representation of a skill with a sepecific configuration for a role.
     *
     * @param skill   The skill being configured
     * @param section The configuration
     */
    public RoleSkill(String skill, DataView section) {
        this.skill = checkNotNull(skill);
        this.section = checkNotNull(section).copy();
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
        return this.section.copy();
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(RpgQueries.RoleSkill.SKILL_NAME, this.skill)
                .set(RpgQueries.RoleSkill.SKILL_CONFIGURATION, this.section);
    }

    public static final class Builder extends AbstractDataBuilder<RoleSkill> {
        public Builder() {
            super(RoleSkill.class, 1);
        }

        @Override
        protected Optional<RoleSkill> buildContent(DataView container) throws InvalidDataException {
            if (!container.contains(RpgQueries.RoleSkill.SKILL_NAME,
                                    RpgQueries.RoleSkill.SKILL_CONFIGURATION)) {
                return Optional.empty();
            }
            final String skillName = container.getString(RpgQueries.RoleSkill.SKILL_NAME)
                    .orElseThrow(IllegalArgumentException::new);
            final DataView settings = container.getView(RpgQueries.RoleSkill.SKILL_CONFIGURATION)
                    .orElseThrow(IllegalArgumentException::new);
            return Optional.of(new RoleSkill(skillName, settings.copy()));
        }
    }
}

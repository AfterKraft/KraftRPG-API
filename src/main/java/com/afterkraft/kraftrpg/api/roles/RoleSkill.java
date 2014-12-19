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
package com.afterkraft.kraftrpg.api.roles;


import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;
import com.afterkraft.kraftrpg.common.persistence.data.DataContainer;
import com.afterkraft.kraftrpg.common.persistence.data.DataUtil;
import com.afterkraft.kraftrpg.common.persistence.data.DataView;

/**
 * Representation for a Skill and an associated MemoryConfiguration for that skill. This is a simple
 * datastructure utilized in Roles.
 */
public final class RoleSkill {
    private String skill;
    private DataContainer section;

    RoleSkill(String skill, DataView section) {
        this.skill = skill;
        Optional<DataContainer> optional = DataUtil.copyFromExisiting(section);
        checkArgument(optional.isPresent(), "Cannot have an invalid "
                + "configuration for a role skill!");
        this.section = optional.get();
    }

    public boolean skillEquals(ISkill other) {
        return this.skill.equalsIgnoreCase(other.getName());

    }

    public String getSkillName() {
        return this.skill;
    }

    public int getLevel() {
        return this.section.getInt(SkillSetting.LEVEL.node()).get();
    }

    public DataContainer getConfig() {
        return DataUtil.copyFromExisiting(this.section).get();
    }
}

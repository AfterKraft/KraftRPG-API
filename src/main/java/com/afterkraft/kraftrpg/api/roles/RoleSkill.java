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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;

public final class RoleSkill {
    private String skill;
    private MemoryConfiguration section;

    RoleSkill(String skill, ConfigurationSection section) {
        this.skill = skill;
        this.section = new MemoryConfiguration();
        this.section.addDefaults(section.getValues(true));
    }

    public boolean skillEquals(ISkill other) {
        checkArgument(other != null, "Cannot compare to a null Skill!");
        return this.skill.equalsIgnoreCase(other.getName());

    }

    public String getSkillName() {
        return this.skill;
    }

    public int getLevel() {
        return this.section.getInt(SkillSetting.LEVEL.node());
    }

    public ConfigurationSection getConfig() {
        MemoryConfiguration clone = new MemoryConfiguration();
        clone.addDefaults(this.section);
        return clone;
    }
}

/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.roles;

import org.apache.commons.lang.Validate;

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
        Validate.notNull(other, "Cannot compare to a null Skill!");
        return skill.equalsIgnoreCase(other.getName());

    }

    public String getSkillName() {
        return skill;
    }

    public int getLevel() {
        return section.getInt(SkillSetting.LEVEL.node());
    }

    public ConfigurationSection getConfig() {
        MemoryConfiguration clone = new MemoryConfiguration();
        clone.addDefaults(section);
        return clone;
    }
}

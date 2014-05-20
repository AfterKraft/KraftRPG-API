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
package com.afterkraft.kraftrpg.api.entity.roles;

import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;
import org.bukkit.configuration.ConfigurationSection;

public class RoleSkill {
    private ISkill skill;
    private ConfigurationSection section;

    public RoleSkill(ISkill skill, ConfigurationSection section) {
        this.skill = skill;
        this.section = section;
    }

    public boolean skillEquals(ISkill other) {
        return skill == other || skill.equals(other);

    }

    public ISkill getSkill() {
        return skill;
    }

    public int getLevel() {
        return section.getInt(SkillSetting.LEVEL.node());
    }

    public ConfigurationSection getConfig() {
        return section;
    }
}

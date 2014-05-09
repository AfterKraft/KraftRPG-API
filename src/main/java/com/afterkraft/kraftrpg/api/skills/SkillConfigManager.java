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
package com.afterkraft.kraftrpg.api.skills;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.entity.roles.Role;


public interface SkillConfigManager extends Manager {

    public void reload();

    public void saveSkillConfig();

    public Configuration getClassConfig(String name);

    public void addClassSkillSettings(String className, String skillName, ConfigurationSection section);

    public void loadSkillDefaults(ISkill skill);

    // Because bukkit can't handle setting defaults before sections exist
    public void setClassDefaults();

    //------------------------//
    // Data retrieval methods //
    //------------------------//

    public String getRaw(ISkill skill, String setting, String def);

    public String getRaw(ISkill skill, SkillSetting setting, String def);

    public Boolean getRaw(ISkill skill, SkillSetting setting, boolean def);

    public Boolean getRaw(ISkill skill, String setting, boolean def);

    public Set<String> getRawKeys(ISkill skill, String setting);

    public Object getSetting(Role role, ISkill skill, String setting);

    public int getSetting(Role role, ISkill skill, String setting, int def);

    public double getSetting(Role role, ISkill skill, String setting, double def);

    public String getSetting(Role role, ISkill skill, String setting, String def);

    public Boolean getSetting(Role role, ISkill skill, String setting, boolean def);

    public List<String> getSetting(Role role, ISkill skill, String setting, List<String> def);

    public Set<String> getSettingKeys(Role role, ISkill skill, String setting);

    public Set<String> getUseSettingKeys(SkillCaster caster, ISkill skill, String setting);

    public List<String> getUseSettingKeys(SkillCaster caster, ISkill skill);

    public int getLevel(Sentient being, ISkill skill, int def);

    public int getUseSetting(SkillCaster caster, ISkill skill, SkillSetting setting, int def, boolean lower);

    public String getUseSetting(SkillCaster caster, ISkill skill, SkillSetting setting, String def);

    public double getUseSetting(SkillCaster caster, ISkill skill, SkillSetting setting, double def, boolean lower);

    public boolean getUseSetting(SkillCaster caster, ISkill skill, SkillSetting setting, boolean def);

    public int getUseSetting(SkillCaster caster, ISkill skill, String setting, int def, boolean lower);

    public double getUseSetting(SkillCaster caster, ISkill skill, String setting, double def, boolean lower);

    public boolean getUseSetting(SkillCaster caster, ISkill skill, String setting, boolean def);

    public String getUseSetting(SkillCaster caster, ISkill skill, String setting, String def);

    public List<String> getUseSetting(SkillCaster caster, ISkill skill, String setting, List<String> def);
}

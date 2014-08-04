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
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.roles.Role;


public interface SkillConfigManager extends Manager {

    /**
     * Attempts to reload all skill settings. This will cause all linked
     * {@link Role}s to be reconfigured with any updated skill settings.
     */
    public void reload();

    /**
     * Saves the current skill settings from memory to file.
     */
    public void saveSkillConfig();

    public Configuration getRoleSkillConfig(String name);

    public void addRoleSkillSettings(String roleName, String skillName, ConfigurationSection section);

    public void loadSkillDefaults(ISkill skill);

    /**
     * Applies a customized skill configuration for the specified skill and
     * skill caster. The provided configuration section should be completely
     * filled with the correct defaults the skill may call on. Uses for this
     * method include applying custom skill settings when an ItemStack is
     * granting a specific skill use.
     *
     * @param skill   The skill to set the custom defaults for
     * @param caster  The caster to use these defaults
     * @param section The section of skill settings for the caster's next skill
     *                use
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the caster is null
     * @throws IllegalArgumentException If the section is null or empty
     */
    public void addTemporarySkillConfigurations(ISkill skill, SkillCaster caster, ConfigurationSection section);

    /**
     * Clears all custom skill settings after use. This is used for cleaning up
     * the provided skill defaults for a caster's use.
     *
     * @param caster
     */
    public void clearTemporarySkillConfigurations(SkillCaster caster);

    /**
     * Gets the global {@link ISkill} setting defined by the String setting.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a
     *                                  default
     */
    public String getRawString(ISkill skill, String setting);

    /**
     * Gets the global {@link ISkill} setting defined by the {@link SkillSetting} setting.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a
     *                                  default
     */
    public String getRawString(ISkill skill, SkillSetting setting);

    /**
     * Gets the global {@link ISkill} setting defined by the {@link SkillSetting} setting.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a
     *                                  default
     */
    public Boolean getRawBoolean(ISkill skill, SkillSetting setting);

    /**
     * Gets the global {@link ISkill} setting defined by the String setting.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a
     *                                  default
     */
    public Boolean getRawBoolean(ISkill skill, String setting);

    /**
     * Gets the global {@link ISkill}'s setting keys.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a
     *                                  default
     */
    public Set<String> getRawKeys(ISkill skill, String setting);

    /**
     * Checks if the queried skill has a default for the desired {@link SkillSetting}
     *
     * @param skill   The skill to check the default configurations of
     * @param setting The setting node to check if it is configured
     * @return True if the setting for the skill has been set in the default
     * configurations
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     */
    public boolean isSettingConfigured(ISkill skill, SkillSetting setting);

    /**
     * Checks if the queried skill has a default for the desired {@link SkillSetting}
     *
     * @param skill   The skill to check the default configurations of
     * @param setting The setting node to check if it is configured
     * @return True if the setting for the skill has been set in the default
     * configurations
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     */
    public boolean isSettingConfigured(ISkill skill, String setting);

    public Object getSetting(Role role, ISkill skill, SkillSetting setting);

    public Object getSetting(Role role, ISkill skill, String setting);

    public int getIntSetting(Role role, ISkill skill, SkillSetting setting);

    public int getIntSetting(Role role, ISkill skill, String setting);

    public double getDoubleSetting(Role role, ISkill skill, SkillSetting setting);

    public double getDoubleSetting(Role role, ISkill skill, String setting);

    public String getStringSetting(Role role, ISkill skill, SkillSetting setting);

    public String getStringSetting(Role role, ISkill skill, String setting);

    public Boolean getBooleanSetting(Role role, ISkill skill, SkillSetting setting);

    public Boolean getBooleanSetting(Role role, ISkill skill, String setting);

    public List<String> getStringListSetting(Role role, ISkill skill, SkillSetting setting);

    public List<String> getStringListSetting(Role role, ISkill skill, String setting);

    public ItemStack getItemStackSetting(Role role, ISkill skill, SkillSetting setting);

    public ItemStack getItemStackSetting(Role role, ISkill skill, String setting);

    public Set<String> getRootKeys(Role role, ISkill skill);

    public Set<String> getSettingKeys(Role role, ISkill skill, String setting);

    public Set<String> getSettingKeys(Role role, ISkill skill, SkillSetting setting);

    public Set<String> getUseSettingKeys(SkillCaster caster, ISkill skill, SkillSetting setting);

    public Set<String> getUseSettingKeys(SkillCaster caster, ISkill skill, String setting);

    public List<String> getUseSettingKeys(SkillCaster caster, ISkill skill);

    public int getLevel(Sentient being, ISkill skill);

    public int getLevel(Sentient being, ISkill skill, int defaultSetting);

    public Object getUsedSetting(SkillCaster caster, ISkill skill, SkillSetting setting);

    public Object getUsedSetting(SkillCaster caster, ISkill skill, String setting);

    public int getUsedIntSetting(SkillCaster caster, ISkill skill, SkillSetting setting);

    public int getUsedIntSetting(SkillCaster caster, ISkill skill, String setting);

    public double getUsedDoubleSetting(SkillCaster caster, ISkill skill, SkillSetting setting);

    public double getUsedDoubleSetting(SkillCaster caster, ISkill skill, String setting);

    public boolean getUsedBooleanSetting(SkillCaster caster, ISkill skill, SkillSetting setting);

    public boolean getUsedBooleanSetting(SkillCaster caster, ISkill skill, String setting);

    public String getUsedStringSetting(SkillCaster caster, ISkill skill, SkillSetting setting);

    public String getUsedStringSetting(SkillCaster caster, ISkill skill, String setting);

    public List<?> getUsedListSetting(SkillCaster caster, ISkill skill, SkillSetting setting);

    public List<?> getUsedListSetting(SkillCaster caster, ISkill skill, String setting);

    public List<String> getUsedStringListSetting(SkillCaster caster, ISkill skill, SkillSetting setting);

    public List<String> getUsedStringListSetting(SkillCaster caster, ISkill skill, String setting);

    public ItemStack getUsedItemStackSetting(SkillCaster caster, ISkill skill, SkillSetting setting);

    public ItemStack getUsedItemStackSetting(SkillCaster caster, ISkill skill, String setting);

}

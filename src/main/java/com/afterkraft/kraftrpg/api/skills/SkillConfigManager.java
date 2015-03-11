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
package com.afterkraft.kraftrpg.api.skills;

import java.util.List;
import java.util.Set;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.persistence.data.DataContainer;
import org.spongepowered.api.service.persistence.data.DataQuery;
import org.spongepowered.api.service.persistence.data.DataView;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.roles.Role;

/**
 * A manager providing skill configurations for all skills. There may be more than one skill manager
 * provided at any time, provided that the implementing skill is defined to use more than one. All
 * Role settings are performed for the role specifically and does not take into account any other
 * role settings, or alternative settings provided by other means.  All use settings, such as {@link
 * #getUsedSetting(SkillCaster, Skill, SkillSetting)} have the following priority in terms of role
 * skill definitions: <ol> <li>{@link com.afterkraft.kraftrpg.api.roles.Role.RoleType#PRIMARY}</li>
 * <li>{@link com.afterkraft.kraftrpg.api.roles.Role.RoleType#SECONDARY}</li> <li>{@link
 * com.afterkraft.kraftrpg.api.roles.Role.RoleType#ADDITIONAL}</li> </ol>
 */
public interface SkillConfigManager extends Manager {

    /**
     * Attempts to reload all skill settings. This will cause all linked {@link Role}s to be
     * reconfigured with any updated skill settings.
     */
    void reload();

    /**
     * Saves the current skill settings from memory to file.
     */
    void saveSkillConfig();

    DataContainer getRoleSkillConfig(String name);

    void addRoleSkillSettings(String roleName, String skillName,
                              DataView section);

    void loadSkillDefaults(Skill skill);

    /**
     * Applies a customized skill configuration for the specified skill and skill caster. The
     * provided configuration section should be completely filled with the correct defaults the
     * skill may call on. Uses for this method include applying custom skill settings when an
     * ItemStack is granting a specific skill use.
     *
     * @param skill   The skill to set the custom defaults for
     * @param caster  The caster to use these defaults
     * @param section The section of skill settings for the caster's next skill use
     *
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the caster is null
     * @throws IllegalArgumentException If the section is null or empty
     */
    void addTemporarySkillConfigurations(Skill skill, SkillCaster caster,
                                         DataView section);

    /**
     * Clears all custom skill settings after use. This is used for cleaning up the provided skill
     * defaults for a caster's use.
     *
     * @param caster The caster using the defaults
     */
    void clearTemporarySkillConfigurations(SkillCaster caster);

    /**
     * Clears the custom skill settings for the specified skill. This is used for cleaning up the
     * provided skill defaults for a caster's specific skill use.
     *
     * @param caster The caster using the defaults
     * @param skill  The skill for the defaults
     */
    void clearTemporarySkillConfigurations(SkillCaster caster, Skill skill);

    /**
     * Gets the global {@link Skill} setting defined by the {@link SkillSetting} setting.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     *
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a default
     */
    String getRawString(Skill skill, SkillSetting setting);

    /**
     * Gets the global {@link Skill} setting defined by the {@link DataQuery} setting.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     *
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a default
     */
    String getRawString(Skill skill, DataQuery setting);

    /**
     * Gets the global {@link Skill} setting defined by the {@link SkillSetting} setting.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     *
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a default
     */
    Boolean getRawBoolean(Skill skill, SkillSetting setting);

    /**
     * Gets the global {@link Skill} setting defined by the {@link DataQuery} setting.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     *
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a default
     */
    Boolean getRawBoolean(Skill skill, DataQuery setting);

    /**
     * Gets the global {@link Skill}'s setting keys.
     *
     * @param skill   The skill to get the settings of
     * @param setting The node to use
     *
     * @return The Boolean value found at the configured node
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     * @throws IllegalStateException    If the setting was not configured with a default
     */
    Set<DataQuery> getRawKeys(Skill skill, DataQuery setting);

    /**
     * Checks if the queried skill has a default for the desired {@link SkillSetting}
     *
     * @param skill   The skill to check the default configurations of
     * @param setting The setting node to check if it is configured
     *
     * @return True if the setting for the skill has been set in the default configurations
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     */
    boolean isSettingConfigured(Skill skill, SkillSetting setting);

    /**
     * Checks if the queried skill has a default for the desired {@link SkillSetting}
     *
     * @param skill   The skill to check the default configurations of
     * @param setting The setting node to check if it is configured
     *
     * @return True if the setting for the skill has been set in the default configurations
     * @throws IllegalArgumentException If the skill is null
     * @throws IllegalArgumentException If the setting is null
     */
    boolean isSettingConfigured(Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Object getRawSetting(Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Object getRawSetting(Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested integer
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    int getRawIntSetting(Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    int getRawIntSetting(Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested double
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    double getRawDoubleSetting(Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    double getRawDoubleSetting(Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    String getRawStringSetting(Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    String getRawStringSetting(Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Boolean getRawBooleanSetting(Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Boolean getRawBooleanSetting(Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    List<String> getRawStringListSetting(Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    List<String> getRawStringListSetting(Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    ItemStack getRawItemStackSetting(Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    ItemStack getRawItemStackSetting(Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Object getSetting(Role role, Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Object getSetting(Role role, Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    int getIntSetting(Role role, Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    int getIntSetting(Role role, Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    double getDoubleSetting(Role role, Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    double getDoubleSetting(Role role, Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    String getStringSetting(Role role, Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    String getStringSetting(Role role, Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Boolean getBooleanSetting(Role role, Skill skill, SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Boolean getBooleanSetting(Role role, Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    List<String> getStringListSetting(Role role, Skill skill,
                                      SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    List<String> getStringListSetting(Role role, Skill skill,
                                      DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    ItemStack getItemStackSetting(Role role, Skill skill,
                                  SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param role    The role to check for specific configurations of
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    ItemStack getItemStackSetting(Role role, Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster The caster to find all available roles granting the skill
     * @param skill  The skill to use the settings of
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    int getLevel(SkillCaster caster, Skill skill);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Object getUsedSetting(SkillCaster caster, Skill skill,
                          SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    Object getUsedSetting(SkillCaster caster, Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    int getUsedIntSetting(SkillCaster caster, Skill skill,
                          SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    int getUsedIntSetting(SkillCaster caster, Skill skill, DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    double getUsedDoubleSetting(SkillCaster caster, Skill skill,
                                SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    double getUsedDoubleSetting(SkillCaster caster, Skill skill,
                                DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    boolean getUsedBooleanSetting(SkillCaster caster, Skill skill,
                                  SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    boolean getUsedBooleanSetting(SkillCaster caster, Skill skill,
                                  DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    String getUsedStringSetting(SkillCaster caster, Skill skill,
                                SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    String getUsedStringSetting(SkillCaster caster, Skill skill,
                                DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    List<?> getUsedListSetting(SkillCaster caster, Skill skill,
                               SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    List<?> getUsedListSetting(SkillCaster caster, Skill skill,
                               DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    List<String> getUsedStringListSetting(SkillCaster caster, Skill skill,
                                          SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    List<String> getUsedStringListSetting(SkillCaster caster, Skill skill,
                                          DataQuery setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    ItemStack getUsedItemStackSetting(SkillCaster caster, Skill skill,
                                      SkillSetting setting);

    /**
     * Gets the raw value of the specific configuration according to the global skill
     * configuration.
     *
     * <p>Skill settings have a contract that if a requested setting is not available either by
     * default or not provided by the skill defaults, an exception is thrown. It is imperative that
     * all skill configurations have defaults available by {@link Skill#getUsedConfigNodes}.</p>
     *
     * @param caster  The {@link SkillCaster} to find available roles granting use of the skill
     * @param skill   The skill to use the settings of
     * @param setting The query to the setting object value
     *
     * @return The requested object
     * @throws SkillConfigurationException If the requested setting is not configured
     */
    ItemStack getUsedItemStackSetting(SkillCaster caster, Skill skill,
                                      DataQuery setting);

}

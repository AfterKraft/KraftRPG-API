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

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.Validate;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;


/**
 * A Role is an Immutable object representing the skill tree and damage values
 * that a {@link com.afterkraft.kraftrpg.api.entity.Sentient} may have. All
 * {@link com.afterkraft.kraftrpg.api.skills.ISkill}s are granted for use to
 * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}s and experience is
 * gained by {@link com.afterkraft.kraftrpg.api.entity.Sentient}s.
 * 
 * To construct a Role, use the linked
 * {@link com.afterkraft.kraftrpg.api.roles.Role.Builder}
 */
public final class Role {

    private final RPGPlugin plugin;
    private final String name;
    private final Map<String, RoleSkill> skills;
    private final Set<String> restirctedSkills;
    private final Map<Material, Double> itemDamages;
    private final Map<Material, Double> itemDamagePerLevel;
    private final Map<Material, Boolean> itemVaryingDamage;
    private final Set<ExperienceType> allowedExperience;
    private final Set<String> children;
    private final Set<String> parents;
    private final RoleType type;
    private final int advancementLevel;
    private final boolean choosable;
    private final String description;
    private final String manaName;
    private final double hpAt0, hpPerLevel, mpAt0, mpPerLevel, mpRegenAt0,
            mpRegenPerLevel;

    Role(RPGPlugin plugin, String name, Map<String, RoleSkill> skills, Set<ExperienceType> experienceTypes, Map<Material, Double> itemDamages, Map<Material, Double> itemDamagePerLevel, Map<Material, Boolean> itemVaryingDamage, RoleType type, int advancementLevel, boolean choosable, String description, String manaName, double hpAt0, double hpPerLevel, double mpAt0, double mpPerLevel, double mpRegenAt0, double mpRegenPerLevel, Set<String> children, Set<String> parents, Set<String> restirctedSkills) {
        this.plugin = plugin;
        this.name = name;
        this.skills = skills;
        this.allowedExperience = experienceTypes;
        this.itemDamages = itemDamages;
        this.itemDamagePerLevel = itemDamagePerLevel;
        this.itemVaryingDamage = itemVaryingDamage;
        this.type = type;
        this.advancementLevel = advancementLevel;
        this.choosable = choosable;
        this.description = description;
        this.manaName = manaName;
        this.hpAt0 = hpAt0;
        this.hpPerLevel = hpPerLevel;
        this.mpAt0 = mpAt0;
        this.mpPerLevel = mpPerLevel;
        this.mpRegenAt0 = mpRegenAt0;
        this.mpRegenPerLevel = mpRegenPerLevel;
        this.children = children;
        this.parents = parents;
        this.restirctedSkills = restirctedSkills;
    }

    /**
     * Construct a Role. It is necessary to provide the link to
     * {@link com.afterkraft.kraftrpg.api.RPGPlugin} as many of the operations
     * performed in a Role use it.
     * 
     * @param plugin implementation of KraftRPG
     * @return a builder
     */
    public static Builder builder(RPGPlugin plugin) {
        return new Builder(plugin);
    }

    /**
     * Get the type of Role this is.
     * 
     * @return the {@link com.afterkraft.kraftrpg.api.roles.Role.RoleType}
     */
    public final RoleType getType() {
        return this.type;
    }

    /**
     * Return the configured name for this Role
     * 
     * @return the name for this role
     */
    public String getName() {
        return this.name;
    }

    /**
     * Check if the specified skill is granted at the specified level, if not
     * before the level.
     * 
     * @param skill to query
     * @param level to check
     * @return true if the skill is granted at the level or before
     * @throws IllegalArgumentException if the skill is null
     */
    public boolean hasSkillAtLevel(ISkill skill, int level) throws IllegalArgumentException {
        Validate.notNull(skill, "Cannot check against a null skill!");
        RoleSkill rs = skills.get(skill.getName());

        return rs != null && rs.getLevel() <= level;
    }

    /**
     * Return a copy of the skill configuration for the specified skill at the
     * desired level, if the skill is granted at the specified level or
     * before.
     * 
     * @param skill to get the configuration of
     * @param level to check if the skill is granted at that level
     * @return a copy of the configuration section
     * @throws IllegalArgumentException if the skill is null
     * @throws com.afterkraft.kraftrpg.api.roles.RoleSkillConfigurationException
     *             if there is an error in the Role configuration
     */
    public ConfigurationSection getSkillConfigIfAvailable(ISkill skill, int level) throws IllegalArgumentException, RoleSkillConfigurationException {
        if (hasSkill(skill)) {
            RoleSkill rs = skills.get(skill.getName());
            if (rs == null) throw new RoleSkillConfigurationException("There is a null RoleSkill in " + name + "'s Configuration! Please fix your configuration!");
            if (rs.getLevel() <= level) {
                MemoryConfiguration section = new MemoryConfiguration();
                section.addDefaults(rs.getConfig().getValues(true));
                return section;
            }
        }
        return null;
    }

    /**
     * Checks if this role has the defined
     * {@link com.afterkraft.kraftrpg.api.skills.ISkill} at any level
     * 
     * @param skill to check
     * @return true if the skill is defined
     * @throws IllegalArgumentException if the skill is null
     */
    public boolean hasSkill(ISkill skill) throws IllegalArgumentException {
        Validate.notNull(skill, "Cannot check a null ISkill!");
        return skills.containsKey(skill.getName());
    }

    public boolean isSkillRestricted(ISkill skill) throws IllegalArgumentException {
        Validate.notNull(skill, "Cannot check a null ISkill!");
        return restirctedSkills.contains(skill.getName());
    }

    /**
     * Returns the level required for gaining the specified ISkill.
     * 
     * @param skill to check
     * @return the level at which the skill is granted
     * @throws IllegalArgumentException if the skill is null
     * @throws com.afterkraft.kraftrpg.api.roles.RoleSkillConfigurationException
     *             if the skill does not have a skill configuration for the
     *             level required
     */
    public int getLevelRequired(ISkill skill) throws IllegalArgumentException, RoleSkillConfigurationException {
        Validate.notNull(skill, "Cannot check a null ISkill!");
        RoleSkill rs = skills.get(skill.getName());
        if (rs == null) {
            throw new RoleSkillConfigurationException("A skill: " + skill.getName() + " does not have a configured level requirement!");
        }
        return rs.getLevel();
    }

    /**
     * Creates an immutable set of all defined skills for this Role.
     * 
     * @return an immutable set of all defined skills
     */
    public Set<ISkill> getAllSkills() {
        ImmutableSet.Builder<ISkill> builder = ImmutableSet.builder();
        for (String skillName : skills.keySet()) {
            builder.add(plugin.getSkillManager().getSkill(skillName));
        }
        return builder.build();
    }

    /**
     * Gets an immutable set of
     * {@link com.afterkraft.kraftrpg.api.skills.ISkill}s that are granted
     * below or at the specified level
     * 
     * @param level at which the skills have been granted
     * @return
     */
    public Set<ISkill> getAllSkillsAtLevel(int level) throws IllegalArgumentException {
        Validate.isTrue(level >= 0, "Cannot get Skills for a negative role level!");
        ImmutableSet.Builder<ISkill> builder = ImmutableSet.builder();
        for (Map.Entry<String, RoleSkill> entry : skills.entrySet()) {
            if (entry.getValue().getLevel() <= level) {
                builder.add(plugin.getSkillManager().getSkill(entry.getKey()));
            }
        }
        return builder.build();
    }

    /**
     * Creates an immutable set of Roles that are defined as this Role's
     * parents
     * 
     * @return an immutable set of roles that are this role's parents
     */
    public Set<Role> getParents() {
        ImmutableSet.Builder<Role> builder = ImmutableSet.builder();
        for (String roleName : parents) {
            builder.add(plugin.getRoleManager().getRole(roleName));
        }
        return builder.build();
    }

    /**
     * Creates an immutable set of Roles that are defined as this Role's
     * children
     * 
     * @return an immutable set of roles that are this role's children
     */
    public Set<Role> getChildren() {
        ImmutableSet.Builder<Role> builder = ImmutableSet.builder();
        for (String roleName : children) {
            builder.add(plugin.getRoleManager().getRole(roleName));
        }
        return builder.build();
    }

    /**
     * Check if this Role is defined as the default Role as configured in
     * KraftRPG.
     * 
     * @return true if this role is the default role
     */
    public boolean isDefault() {
        if (type == RoleType.PRIMARY) {
            return this.equals(plugin.getRoleManager().getDefaultPrimaryRole());
        } else if (type == RoleType.SECONDARY) {
            return this.equals(plugin.getRoleManager().getDefaultSecondaryRole());
        }
        return false;
    }

    /**
     * Gets the max health at the specified level
     * 
     * @param level specified
     * @return the max health at the specified level
     * @throws java.lang.IllegalArgumentException if the level is less than 0
     */
    public double getMaxHealthAtLevel(int level) throws IllegalArgumentException {
        Validate.isTrue(level >= 0, "Cannot calculate for a negative Role level!");
        return hpAt0 + hpPerLevel * level;
    }

    /**
     * Gets the max mana at the specified level
     * 
     * @param level specified
     * @return the max mana at the specified level
     * @throws java.lang.IllegalArgumentException if the level is less than 0
     */
    public int getMaxManaAtLevel(int level) throws IllegalArgumentException {
        Validate.isTrue(level >= 0, "Cannot calculate for a negative Role level!");
        return (int) (mpAt0 + mpPerLevel * level);
    }

    /**
     * Gets the max health at the specified level
     * 
     * @param level specified
     * @return the max health at the specified level
     * @throws java.lang.IllegalArgumentException if the level is less than 0
     */
    public double getManaRegenAtLevel(int level) throws IllegalArgumentException {
        Validate.isTrue(level >= 0, "Cannot calculate for a negative Role level!");
        return mpRegenAt0 + mpRegenPerLevel * level;
    }

    /**
     * Gets the customized (if not default) name of mana for this role.
     * 
     * @return the customized name of Mana
     */
    public String getManaName() {
        return manaName;
    }

    /**
     * Gets the maximum health at level 0.
     * 
     * @return the maximum health at level 0.
     */
    public double getMaxHealthAtZero() {
        return hpAt0;
    }

    /**
     * Gets the maximum health increase per level.
     * 
     * @return the maximum health increase per level
     */
    public double getHeatlhIncreasePerLevel() {
        return hpPerLevel;
    }

    /**
     * Gets the maximum mana at level 0.
     * 
     * @return the maximum mana at level 0
     */
    public double getMaxManaAtZero() {
        return mpAt0;
    }

    /**
     * Gets the maximum mana increase per level.
     * 
     * @return the maximum mana increase per level
     */
    public double getManaIncreasePerLevel() {
        return mpPerLevel;
    }

    /**
     * Gets the mana regeneration at level 0.
     * 
     * @return the mana regeneration at level 0
     */
    public double getManaRegenAtZero() {
        return mpRegenAt0;
    }

    /**
     * Gets the mana regeneration increase per level.
     * 
     * @return the mana regeneration increase per level
     */
    public double getManaRegenIncreasePerLevel() {
        return mpRegenPerLevel;
    }


    /**
     * Check if this role can by chosen by players. A non-choosable role must
     * be granted through some means other than role tree advancement.
     * 
     * @return can be chosen
     */
    public boolean isChoosable() {
        return choosable;
    }

    /**
     * Gets the level at which this Role is considered eligible for
     * advancement.
     * 
     * @return the level that this role is eligible for advancement to a child
     *         role
     */
    public int getAdvancementLevel() {
        return advancementLevel;
    }

    /**
     * Gets the flat damage for the specified {@link org.bukkit.Material}. If
     * the damage is not configured, the default damage is 0.
     * 
     * @param type to check
     * @return the damage for the perscribed material, if not 0
     * @throws java.lang.IllegalArgumentException if the type is null
     */
    public double getItemDamage(Material type) throws IllegalArgumentException {
        Validate.notNull(type, "Cannot check the Item damage of a null Material type!");
        return this.itemDamages.containsKey(type) ? this.itemDamages.get(type) : 0.0D;
    }

    /**
     * If a Role defines an item to deal varying damage for each attack, the
     * final damage is not always the same. This checks if a
     * {@link org.bukkit.Material} type is configured to deal varying damage.
     * 
     * @param type to check
     * @return true if this Role is configured to have varying damage for the
     *         item
     * @throws java.lang.IllegalArgumentException if the type is null
     */
    public boolean doesItemVaryDamage(Material type) throws IllegalArgumentException {
        return this.itemVaryingDamage.containsKey(type) ? this.itemVaryingDamage.get(type) : false;
    }

    /**
     * Gets the damage to add to the base damage for the specified Material
     * per level. If the damage increase is not defined, it will default to 0.
     * 
     * @param type of item to get the damage increase per level of
     * @return the damage increase per level if not 0
     * @throws IllegalArgumentException if the type is null
     */
    public double getItemDamagePerLevel(Material type) throws IllegalArgumentException {
        return this.itemDamagePerLevel.get(type) != null ? this.itemDamagePerLevel.get(type) : 0.0D;
    }

    /**
     * Gets the description of this role.
     * 
     * @return the description of this role
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int hash = 1;
        hash = hash * PRIME + name.hashCode();
        hash = hash * PRIME + description.hashCode();
        hash = hash * PRIME + skills.hashCode();
        hash = hash * PRIME + itemDamages.hashCode();
        hash = hash * PRIME + itemDamagePerLevel.hashCode();
        hash = hash * PRIME + itemVaryingDamage.hashCode();
        hash = hash * PRIME + children.hashCode();
        hash = hash * PRIME + parents.hashCode();
        hash = hash * PRIME + (int) hpAt0;
        hash = hash * PRIME + (int) hpPerLevel;
        hash = hash * PRIME + (int) mpAt0;
        hash = hash * PRIME + (int) mpPerLevel;
        hash = hash * PRIME + (int) mpRegenAt0;
        hash = hash * PRIME + (int) mpRegenPerLevel;
        hash = hash * PRIME + type.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Role)) {
            return false;
        }
        Role role = (Role) obj;

        return this.name.equals(role.name)
                && this.description.equals(role.description)
                && this.choosable == role.choosable
                && this.skills.equals(role.skills)
                && this.itemDamages.equals(role.itemDamages)
                && this.itemDamagePerLevel.equals(role.itemDamagePerLevel)
                && this.itemVaryingDamage.equals(role.itemVaryingDamage)
                && this.allowedExperience.equals(role.allowedExperience)
                && this.advancementLevel == role.advancementLevel
                && this.children.equals(role.children)
                && this.parents.equals(role.parents)
                && this.hpAt0 == role.hpAt0
                && this.hpPerLevel == role.hpPerLevel
                && this.mpAt0 == role.mpAt0
                && this.mpPerLevel == role.mpPerLevel
                && this.mpRegenAt0 == role.mpRegenAt0
                && this.mpRegenPerLevel == role.mpRegenPerLevel;
    }

    public Role asNewCopy() {
        return Role.builder(plugin).copyOf(this).build();
    }

    public static enum RoleType {

        /**
         * A Primary role. Usually defined as a combat Role with skill
         * granting and health and mana benefits. It may contain restrictions
         * on skill usage provided by other Roles. A
         * {@link com.afterkraft.kraftrpg.api.entity.Sentient} may only have
         * one Primary Role active at any given time.
         */
        PRIMARY,
        /**
         * A Secondary role. Usually a professional Role defining non combat
         * skills and minor health and mana benefits. It may contain
         * restrictions on skill usage provided by other Roles. A
         * {@link com.afterkraft.kraftrpg.api.entity.Sentient} may only have
         * one Secondary Role active at any given time.
         */
        SECONDARY,
        /**
         * An Additional role defines only skills to be granted at level 0 or
         * a progressing skill tree. It does not alter health or mana
         * benefits. A {@link com.afterkraft.kraftrpg.api.entity.Sentient}
         * may have several Additional roles active at any given time.
         */
        ADDITIONAL
    }

    /**
     * This is a builder for Role.
     */
    public static final class Builder {
        RPGPlugin plugin;
        String name;
        Map<String, RoleSkill> skills = new HashMap<String, RoleSkill>();
        Map<Material, Double> itemDamages = new EnumMap<Material, Double>(Material.class);
        Map<Material, Double> itemDamagePerLevel = new EnumMap<Material, Double>(Material.class);
        Map<Material, Boolean> itemVaryingDamage = new EnumMap<Material, Boolean>(Material.class);
        Set<ExperienceType> experienceTypes = new HashSet<ExperienceType>();
        Set<String> restirctedSkills = new HashSet<String>();
        RoleType type = RoleType.PRIMARY;
        Set<String> children = new HashSet<String>();
        Set<String> parents = new HashSet<String>();
        int advancementLevel;
        boolean choosable = true;
        String description;
        String manaName = "Mana";
        double hpAt0 = 20, hpPerLevel, mpAt0 = 100, mpPerLevel, mpRegenAt0 = 1,
                mpRegenPerLevel;

        Builder(RPGPlugin plugin) {
            Validate.notNull(plugin, "Cannot create a Role builder with a null plugin!");
            this.plugin = plugin;
        }

        public Builder removeChild(Role parent) throws IllegalArgumentException {
            Validate.notNull(parent, "Cannot set a null parent Role!");
            Validate.isTrue(!parents.contains(parent.getName()), "Cannot remove a child role when it is already a parent role!");
            children.remove(parent.getName());
            return this;
        }

        public Builder removeParent(Role parent) throws IllegalArgumentException {
            Validate.notNull(parent, "Cannot set a null parent Role!");
            Validate.isTrue(!children.contains(parent.getName()), "Cannot remove a parent role when it is already a child role!");
            parents.remove(parent.getName());
            return this;
        }

        public Role build() throws IllegalArgumentException {
            Validate.notNull(description, "Cannot have a null description!");
            Validate.notNull(advancementLevel, "Cannot have a null advancement level!");
            Validate.isTrue(hpAt0 > 0, "Cannot have zero or negative health at first level!");
            Validate.isTrue(hpPerLevel >= 0, "Cannot have a negative health per level!");
            if (mpPerLevel < 0) {
                Validate.isTrue(mpAt0 > advancementLevel * mpPerLevel, "Cannot have a negative Mana value after an advancement level!");
            }
            ImmutableMap.Builder<Material, Double> itemDamagesBuilder = ImmutableMap.builder();
            for (Map.Entry<Material, Double> entry : itemDamages.entrySet()) {
                itemDamagesBuilder.put(entry.getKey(), entry.getValue());
            }
            ImmutableMap.Builder<Material, Double> itemDamagePerLevelBuilder = ImmutableMap.builder();
            for (Map.Entry<Material, Double> entry : itemDamagePerLevel.entrySet()) {
                itemDamagePerLevelBuilder.put(entry.getKey(), entry.getValue());
            }
            ImmutableMap.Builder<Material, Boolean> itemDamageVaryingBuilder = ImmutableMap.builder();
            for (Map.Entry<Material, Boolean> entry : itemVaryingDamage.entrySet()) {
                itemDamageVaryingBuilder.put(entry.getKey(), entry.getValue());
            }
            ImmutableMap.Builder<String, RoleSkill> skillBuilder = ImmutableMap.builder();
            for (Map.Entry<String, RoleSkill> entry : skills.entrySet()) {
                skillBuilder.put(entry.getKey(), new RoleSkill(entry.getValue().getSkillName(), entry.getValue().getConfig()));
            }
            ImmutableSet.Builder<String> childList = ImmutableSet.builder();
            for (String child : children) {
                childList.add(child);
            }
            ImmutableSet.Builder<String> parentList = ImmutableSet.builder();
            for (String parent : parents) {
                parentList.add(parent);
            }
            ImmutableSet.Builder<ExperienceType> experienceTypeBuilder = ImmutableSet.builder();
            for (ExperienceType type1 : experienceTypes) {
                experienceTypeBuilder.add(type1);
            }
            ImmutableSet.Builder<String> restrictedSkillsBuilder = ImmutableSet.builder();
            for (String skillName : restirctedSkills) {
                restrictedSkillsBuilder.add(skillName);
            }
            return new Role(plugin, name, skillBuilder.build(), experienceTypeBuilder.build(), itemDamagesBuilder.build(), itemDamagePerLevelBuilder.build(), itemDamageVaryingBuilder.build(), type, advancementLevel, choosable, description, manaName, hpAt0, hpPerLevel, mpAt0, mpPerLevel, mpRegenAt0, mpRegenPerLevel, childList.build(), parentList.build(), restrictedSkillsBuilder.build());
        }

        public Builder copyOf(Role role) throws IllegalArgumentException {
            Validate.notNull(role, "Cannot copy a null Role!");
            Builder builder = new Builder(role.plugin)
                    .setName(role.name)
                    .setAdvancementLevel(role.advancementLevel)
                    .setChoosable(role.choosable)
                    .setDescription(role.description)
                    .setHpAt0(role.hpAt0)
                    .setHpPerLevel(role.hpPerLevel)
                    .setMpAt0(role.mpAt0)
                    .setMpRegenAt0(role.mpRegenAt0)
                    .setMpPerLevel(role.mpPerLevel)
                    .setMpRegenPerLevel(role.mpRegenPerLevel)
                    .setManaName(role.manaName)
                    .setType(role.type);

            for (Map.Entry<Material, Double> entry : role.itemDamages.entrySet()) {
                builder.setItemDamage(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<Material, Double> entry : role.itemDamagePerLevel.entrySet()) {
                builder.setItemDamagePerLevel(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<Material, Boolean> entry : role.itemVaryingDamage.entrySet()) {
                builder.setItemDamageVaries(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, RoleSkill> entry : role.skills.entrySet()) {
                builder.addRoleSkill(plugin.getSkillManager().getSkill(entry.getKey()), entry.getValue().getConfig());
            }
            for (String child : role.children) {
                builder.addChild(plugin.getRoleManager().getRole(child));
            }
            for (String parent : role.parents) {
                builder.addParent(plugin.getRoleManager().getRole(parent));
            }
            for (ExperienceType type1 : experienceTypes) {
                builder.addExperienceType(type1);
            }
            return builder;
        }

        /**
         * Set the specified RoleType
         * 
         * @param type of Role
         * @return this builder for chaining
         * @throws java.lang.IllegalArgumentException if the role type is null
         */
        public Builder setType(RoleType type) throws IllegalArgumentException {
            Validate.notNull(type, "Cannot have a null RoleType!");
            this.type = type;
            return this;
        }

        /**
         * Set the visible mana name for the role. This name is used to
         * describe to players their current count of the "mana" resource.
         * 
         * @param manaName customized name of Mana
         * @return This builder, for chaining
         * @throws IllegalArgumentException if the name is null or empty
         */
        public Builder setManaName(String manaName) throws IllegalArgumentException {
            Validate.notNull(manaName, "Cannot have a null Mana Name!");
            Validate.isTrue(!manaName.isEmpty(), "Cannot have an empty Mana Name!");
            this.manaName = manaName;
            return this;
        }

        public Builder setMpRegenPerLevel(double mpRegenPerLevel) {
            this.mpRegenPerLevel = mpRegenPerLevel;
            return this;
        }

        public Builder setMpPerLevel(double mpPerLevel) throws IllegalArgumentException {
            Validate.isTrue(mpPerLevel >= 0, "Cannot have a negative mana gained per level!");
            this.mpPerLevel = mpPerLevel;
            return this;
        }

        public Builder setMpRegenAt0(double mpRegenAt0) {
            this.mpRegenAt0 = mpRegenAt0;
            return this;
        }

        public Builder setMpAt0(double mpAt0) throws IllegalArgumentException {
            Validate.isTrue(mpAt0 > 0, "Cannot have a zero or negative starting Mana!");
            this.mpAt0 = mpAt0;
            return this;
        }

        public Builder setHpPerLevel(double hpPerLevel) throws IllegalArgumentException {
            Validate.isTrue(hpPerLevel >= 0, "Cannot have a negative health gained per level!");
            this.hpPerLevel = hpPerLevel;
            return this;
        }

        public Builder setHpAt0(double hpAt0) throws IllegalArgumentException {
            Validate.isTrue(hpAt0 > 0, "Cannot have a zero or negative starting health!");
            this.hpAt0 = hpAt0;
            return this;
        }

        public Builder setDescription(String description) throws IllegalArgumentException {
            Validate.notNull(description, "Cannot have a null Role description!");
            this.description = description;
            return this;
        }

        public Builder setChoosable(boolean choosable) throws IllegalArgumentException {
            Validate.notNull(choosable, "Cannot have a null choosable value!");
            this.choosable = choosable;
            return this;
        }

        public Builder setAdvancementLevel(int advancementLevel) throws IllegalArgumentException {
            Validate.isTrue(advancementLevel >= 0, "Cannot have a less than zero advancement level!");
            this.advancementLevel = advancementLevel;
            return this;
        }

        public Builder setName(String name) throws IllegalArgumentException {
            Validate.notNull(name, "Cannot have a null Role name!");
            Validate.isTrue(!name.isEmpty(), "Cannot have an empty Role name!");
            this.name = name;
            return this;
        }

        public Builder setItemDamage(Material type, double damage) throws IllegalArgumentException {
            Validate.notNull(type, "Cannot have a null Material type!");
            Validate.isTrue(damage > 0, "Cannot have a zero or less than zero damage value!");
            itemDamages.put(type, damage);
            return this;
        }

        public Builder setItemDamagePerLevel(Material type, double damagePerLevel) throws IllegalArgumentException {
            Validate.notNull(type, "Cannot have a null Material type!");
            Validate.isTrue(damagePerLevel > 0, "Cannot have a zero or less than zero damage per level value!");
            itemDamagePerLevel.put(type, damagePerLevel);
            return this;
        }

        /**
         * Set the {@link org.bukkit.Material} type of item to deal varying
         * damage or not. Setting this will affect all damage immediately.
         * 
         * @param type of Material
         * @param doesDamageVary if true, set the item to deal varying damage.
         */
        public Builder setItemDamageVaries(Material type, boolean doesDamageVary) throws IllegalArgumentException {
            Validate.notNull(type, "Cannot have a null Material type!");
            Validate.notNull(doesDamageVary, "Cannot have a zero or less than zero damage per level value!");
            itemVaryingDamage.put(type, doesDamageVary);
            return this;
        }

        /**
         * Add a {@link com.afterkraft.kraftrpg.api.skills.ISkill} to be
         * granted at with the specified ConfigurationSection that dictates
         * the level requirements, reagents, skill dependencies amongst other
         * things
         * 
         * @param skill to define
         * @param section defining the settings for the skill being added to
         *            this Role
         */
        public Builder addRoleSkill(ISkill skill, ConfigurationSection section) throws IllegalArgumentException {
            Validate.notNull(skill, "Cannot have a null ISkill!");
            Validate.notNull(section, "Cannot have a null ConfigurationSection!");
            Validate.isTrue(section.getInt(SkillSetting.LEVEL.node(), 0) >= 0, "Level not specified in the skill configuration!");
            Validate.isTrue(!restirctedSkills.contains(skill.getName()), "Cannot add a skill that is already restricted!");
            skills.put(skill.getName(), new RoleSkill(skill.getName(), section));
            return this;
        }

        public Builder addChild(Role child) throws IllegalArgumentException {
            Validate.notNull(child, "Cannot set a null child Role!");
            Validate.isTrue(!parents.contains(child.getName()), "Cannot add a child role when it is already a parent role!");
            children.add(child.getName());
            return this;
        }

        public Builder addParent(Role parent) throws IllegalArgumentException {
            Validate.notNull(parent, "Cannot set a null parent Role!");
            Validate.isTrue(!children.contains(parent.getName()), "Cannot add a parent role when it is already a child role!");
            parents.add(parent.getName());
            return this;
        }

        public Builder addExperienceType(ExperienceType type) {
            Validate.notNull(type, "Cannot add a null ExperienceType!");
            experienceTypes.add(type);
            return this;
        }

        public Builder addRestirctedSkill(ISkill skill) throws IllegalArgumentException {
            Validate.notNull(skill, "Cannot add a restriction on a null Skill!");
            Validate.isTrue(!skills.containsKey(skill.getName()), "Cannot restrict a skill that is already added as a granted skill!");
            restirctedSkills.add(skill.getName());
            return this;
        }

        public Builder removeRestrictedSkill(ISkill skill) throws IllegalArgumentException {
            Validate.notNull(skill, "Cannot remove a restriction on a null Skill!");
            restirctedSkills.remove(skill.getName());
            return this;
        }

    }
}

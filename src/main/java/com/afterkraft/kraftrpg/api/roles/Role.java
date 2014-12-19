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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import org.spongepowered.api.item.ItemType;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;
import com.afterkraft.kraftrpg.common.persistence.data.DataUtil;
import com.afterkraft.kraftrpg.common.persistence.data.DataView;

/**
 * A Role is an Immutable object representing the skill tree and damage values that a {@link
 * com.afterkraft.kraftrpg.api.entity.Sentient} may have. All {@link ISkill}s
 * are granted for use to {@link
 * com.afterkraft.kraftrpg.api.entity.SkillCaster}s and experience is gained by {@link
 * com.afterkraft.kraftrpg.api.entity.Sentient}s.  To construct a Role, use
 * the linked {@link Builder}
 */
public final class Role {

    private final RPGPlugin plugin;
    private final String name;
    private final Map<String, RoleSkill> skills;
    private final Set<String> restrictedSkills;
    private final Map<ItemType, Double> itemDamages;
    private final Map<ItemType, Double> itemDamagePerLevel;
    private final Map<ItemType, Boolean> itemVaryingDamage;
    private final Set<ItemType> allowedArmor;
    private final Set<ItemType> allowedWeapon;
    private final Set<ExperienceType> allowedExperience;
    private final Set<String> children;
    private final Set<String> parents;
    private final RoleType type;
    private final int advancementLevel;
    private final int maxLevel;
    private final boolean choosable;
    private final String description;
    private final String manaName;
    private final double hpAt0;
    private final double hpPerLevel;
    private final int mpAt0;
    private final int mpPerLevel;
    private final int mpRegenAt0;
    private final int mpRegenPerLevel;

    private Role(Builder builder) {
        this.plugin = builder.plugin;
        this.name = builder.name;
        this.type = builder.type;
        this.choosable = builder.choosable;
        this.description = builder.description;
        this.manaName = builder.manaName;
        this.hpAt0 = builder.hpAt0;
        this.hpPerLevel = builder.hpPerLevel;
        this.mpAt0 = builder.mpAt0;
        this.mpPerLevel = builder.mpPerLevel;
        this.mpRegenAt0 = builder.mpRegenAt0;
        this.mpRegenPerLevel = builder.mpRegenPerLevel;
        this.advancementLevel = builder.advancementLevel;
        this.maxLevel = builder.maxLevel;

        ImmutableMap.Builder<ItemType, Double> itemDamagesBuilder = ImmutableMap.builder();
        for (Map.Entry<ItemType, Double> entry : builder.itemDamages.entrySet()) {
            itemDamagesBuilder.put(entry.getKey(), entry.getValue());
        }
        ImmutableMap.Builder<ItemType, Double> itemDamagePerLevelBuilder = ImmutableMap.builder();
        for (Map.Entry<ItemType, Double> entry : builder.itemDamagePerLevel.entrySet()) {
            itemDamagePerLevelBuilder.put(entry.getKey(), entry.getValue());
        }
        ImmutableMap.Builder<ItemType, Boolean> itemDamageVaryingBuilder = ImmutableMap.builder();
        for (Map.Entry<ItemType, Boolean> entry : builder.itemVaryingDamage.entrySet()) {
            itemDamageVaryingBuilder.put(entry.getKey(), entry.getValue());
        }
        ImmutableMap.Builder<String, RoleSkill> skillBuilder = ImmutableMap.builder();
        for (Map.Entry<String, RoleSkill> entry : builder.skills.entrySet()) {
            skillBuilder.put(entry.getKey(), new RoleSkill(entry.getValue().getSkillName(),
                    entry.getValue().getConfig()));
        }
        ImmutableSet.Builder<String> childList = ImmutableSet.builder();
        for (String child : builder.children) {
            childList.add(child);
        }
        ImmutableSet.Builder<String> parentList = ImmutableSet.builder();
        for (String parent : builder.parents) {
            parentList.add(parent);
        }
        ImmutableSet.Builder<ExperienceType> experienceTypeBuilder = ImmutableSet.builder();
        for (ExperienceType type1 : builder.experienceTypes) {
            experienceTypeBuilder.add(type1);
        }
        ImmutableSet.Builder<String> restrictedSkillsBuilder = ImmutableSet.builder();
        for (String skillName : builder.restrictedSkills) {
            restrictedSkillsBuilder.add(skillName);
        }
        ImmutableSet.Builder<ItemType> allowedArmorBuilder = ImmutableSet.builder();
        for (ItemType type : builder.allowedArmor) {
            allowedArmorBuilder.add(type);
        }
        ImmutableSet.Builder<ItemType> allowedWeaponBuilder = ImmutableSet.builder();
        for (ItemType type : builder.allowedWeapon) {
            allowedWeaponBuilder.add(type);
        }
        this.skills = skillBuilder.build();
        this.allowedExperience = experienceTypeBuilder.build();
        this.itemDamages = itemDamagesBuilder.build();
        this.itemDamagePerLevel = itemDamagePerLevelBuilder.build();
        this.itemVaryingDamage = itemDamageVaryingBuilder.build();
        this.children = childList.build();
        this.parents = parentList.build();
        this.restrictedSkills = restrictedSkillsBuilder.build();
        this.allowedArmor = allowedArmorBuilder.build();
        this.allowedWeapon = allowedWeaponBuilder.build();
    }

    /**
     * Construct a Role. It is necessary to provide the link to {@link
     * com.afterkraft.kraftrpg.api.RPGPlugin} as many of the operations performed in a Role use it.
     *
     * @param plugin implementation of KraftRPG
     *
     * @return a builder
     */
    public static Builder builder(RPGPlugin plugin) {
        return new Builder(plugin);
    }

    public static Builder copyOf(Role role) {
        Builder builder = new Builder(role.plugin)
                .setName(role.name)
                .setAdvancementLevel(role.advancementLevel)
                .setMaxLevel(role.maxLevel)
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

        for (Map.Entry<ItemType, Double> entry : role.itemDamages.entrySet()) {
            builder.setItemDamage(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<ItemType, Double> entry : role.itemDamagePerLevel.entrySet()) {
            builder.setItemDamagePerLevel(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<ItemType, Boolean> entry : role.itemVaryingDamage.entrySet()) {
            builder.setItemDamageVaries(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, RoleSkill> entry : role.skills.entrySet()) {
            builder.addRoleSkill(role.plugin.getSkillManager()
                                         .getSkill(entry.getKey()).get(),
                    entry.getValue().getConfig());
        }
        for (String child : role.children) {
            builder.addChild(role.plugin.getRoleManager().getRole(child).get());
        }
        for (String parent : role.parents) {
            builder.addParent(role.plugin.getRoleManager().getRole(parent).get());
        }
        for (ExperienceType type1 : role.allowedExperience) {
            builder.addExperienceType(type1);
        }
        return builder;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * Get the type of Role this is.
     *
     * @return the {@link RoleType}
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
     * Check if the specified skill is granted at the specified level, if not before the level.
     *
     * @param skill to query
     * @param level to check
     *
     * @return true if the skill is granted at the level or before
     */
    public boolean hasSkillAtLevel(ISkill skill, int level) {
        return this.skills.containsKey(skill.getName())
                && this.skills.get(skill.getName()).getLevel() <= level;
    }

    /**
     * Return a copy of the skill configuration for the specified skill at the desired level, if the
     * skill is granted at the specified level or before.
     *
     * @param skill to get the configuration of
     * @param level to check if the skill is granted at that level
     *
     * @return a copy of the configuration section
     * @throws RoleSkillConfigurationException if there is an error in the Role configuration
     */
    public Optional<? extends DataView> getSkillConfigIfAvailable(ISkill skill,
                                                               int level)
            throws RoleSkillConfigurationException {
        if (hasSkill(skill)) {
            if (!this.skills.containsKey(skill.getName())) {
                throw new RoleSkillConfigurationException(
                        "There is an invalid RoleSkill in "
                        + this.name
                                + "'s Configuration!"
                                + " Please fix your configuration!");
            }
            RoleSkill rs = this.skills.get(skill.getName());
            if (rs.getLevel() <= level) {
                return Optional.fromNullable(
                        DataUtil.copyFromExisiting(rs.getConfig()));
            }
        }
        return Optional.absent();
    }

    /**
     * Checks if this role has the defined {@link ISkill} at any
     * level
     *
     * @param skill to check
     *
     * @return true if the skill is defined
     */
    public boolean hasSkill(ISkill skill) {
        return this.skills.containsKey(skill.getName());
    }

    public boolean isSkillRestricted(ISkill skill) {
        return this.restrictedSkills.contains(skill.getName());
    }

    /**
     * Returns the level required for gaining the specified ISkill.
     *
     * @param skill to check
     *
     * @return the level at which the skill is granted
     * @throws RoleSkillConfigurationException if the skill does not have a skill configuration for
     *                                         the level required
     */
    public int getLevelRequired(ISkill skill) throws RoleSkillConfigurationException {
        if (!this.skills.containsKey(skill.getName())) {
            throw new RoleSkillConfigurationException("A skill: " + skill.getName()
                    + " does not have a configured level requirement!");
        }
        return this.skills.get(skill.getName()).getLevel();
    }

    /**
     * Creates an immutable set of all defined skills for this Role.
     *
     * @return an immutable set of all defined skills
     */
    public Set<ISkill> getAllSkills() {
        ImmutableSet.Builder<ISkill> builder = ImmutableSet.builder();
        for (String skillName : this.skills.keySet()) {
            builder.add(this.plugin
                                .getSkillManager().getSkill(skillName).get());
        }
        return builder.build();
    }

    /**
     * Gets an immutable set of {@link com.afterkraft.kraftrpg.api.skills.ISkill}s that are granted
     * below or at the specified level
     *
     * @param level at which the skills have been granted
     *
     * @return the skills granted up to the specified level.
     * @throws IllegalArgumentException if the level is negative
     */
    public Set<ISkill> getAllSkillsAtLevel(int level) {
        ImmutableSet.Builder<ISkill> builder = ImmutableSet.builder();
        for (Map.Entry<String, RoleSkill> entry : this.skills.entrySet()) {
            if (entry.getValue().getLevel() <= level) {
                builder.add(this.plugin
                                    .getSkillManager()
                                    .getSkill(entry.getKey()).get());
            }
        }
        return builder.build();
    }

    /**
     * Creates an immutable set of Roles that are defined as this Role's parents
     *
     * @return an immutable set of roles that are this role's parents
     */
    public Set<Role> getParents() {
        ImmutableSet.Builder<Role> builder = ImmutableSet.builder();
        for (String roleName : this.parents) {
            builder.add(this.plugin.getRoleManager().getRole(roleName).get());
        }
        return builder.build();
    }

    /**
     * Creates an immutable set of Roles that are defined as this Role's children
     *
     * @return an immutable set of roles that are this role's children
     */
    public Set<Role> getChildren() {
        ImmutableSet.Builder<Role> builder = ImmutableSet.builder();
        for (String roleName : this.children) {
            builder.add(this.plugin.getRoleManager().getRole(roleName).get());
        }
        return builder.build();
    }

    /**
     * Check if this Role is defined as the default Role as configured in KraftRPG.
     *
     * @return true if this role is the default role
     */
    public boolean isDefault() {
        if (this.type == RoleType.PRIMARY && this.plugin.getRoleManager()
                .getDefaultPrimaryRole().isPresent()) {
            return this == this.plugin.getRoleManager()
                    .getDefaultPrimaryRole().get();
        } else if (this.type == RoleType.SECONDARY && this.plugin
                .getRoleManager().getDefaultSecondaryRole().isPresent()) {
            return this == this.plugin.getRoleManager()
                    .getDefaultSecondaryRole().get();
        }
        return false;
    }

    /**
     * Gets the max health at the specified level
     *
     * @param level specified
     *
     * @return the max health at the specified level
     * @throws IllegalArgumentException if the level is less than 0
     */
    public double getMaxHealthAtLevel(int level) {
        checkArgument(level >= 0, "Cannot calculate for a negative Role level!");
        return this.hpAt0 + this.hpPerLevel * level;
    }

    /**
     * Gets the max mana at the specified level
     *
     * @param level specified
     *
     * @return the max mana at the specified level
     * @throws IllegalArgumentException if the level is less than 0
     */
    public int getMaxManaAtLevel(int level) {
        checkArgument(level >= 0, "Cannot calculate for a negative Role level!");
        return (int) (this.mpAt0 + this.mpPerLevel * level);
    }

    /**
     * Gets the max health at the specified level
     *
     * @param level specified
     *
     * @return the max health at the specified level
     * @throws IllegalArgumentException if the level is less than 0
     */
    public int getManaRegenAtLevel(int level) {
        checkArgument(level >= 0, "Cannot calculate for a negative Role level!");
        return this.mpRegenAt0 + this.mpRegenPerLevel * level;
    }

    /**
     * Gets the customized (if not default) name of mana for this role.
     *
     * @return the customized name of Mana
     */
    public String getManaName() {
        return this.manaName;
    }

    /**
     * Gets the maximum health at level 0.
     *
     * @return the maximum health at level 0.
     */
    public double getMaxHealthAtZero() {
        return this.hpAt0;
    }

    /**
     * Gets the maximum health increase per level.
     *
     * @return the maximum health increase per level
     */
    public double getHeatlhIncreasePerLevel() {
        return this.hpPerLevel;
    }

    /**
     * Gets the maximum mana at level 0.
     *
     * @return the maximum mana at level 0
     */
    public double getMaxManaAtZero() {
        return this.mpAt0;
    }

    /**
     * Gets the maximum mana increase per level.
     *
     * @return the maximum mana increase per level
     */
    public double getManaIncreasePerLevel() {
        return this.mpPerLevel;
    }

    /**
     * Gets the mana regeneration at level 0.
     *
     * @return the mana regeneration at level 0
     */
    public double getManaRegenAtZero() {
        return this.mpRegenAt0;
    }

    /**
     * Gets the mana regeneration increase per level.
     *
     * @return the mana regeneration increase per level
     */
    public double getManaRegenIncreasePerLevel() {
        return this.mpRegenPerLevel;
    }

    /**
     * Check if this role can by chosen by players. A non-choosable role must be granted through
     * some means other than role tree advancement.
     *
     * @return can be chosen
     */
    public boolean isChoosable() {
        return this.choosable;
    }

    /**
     * Gets the level at which this Role is considered eligible for advancement.
     *
     * @return the level that this role is eligible for advancement to a child role
     */
    public int getAdvancementLevel() {
        return this.advancementLevel;
    }

    /**
     * Gets the flat damage for the specified {@link ItemType}. If the damage is not
     * configured, the default damage is 0.
     *
     * @param type to check
     *
     * @return the damage for the perscribed material, if not 0
     */
    public double getItemDamage(ItemType type) {
        return this.itemDamages.containsKey(type) ? this.itemDamages.get(type) : 0.0D;
    }

    /**
     * If a Role defines an item to deal varying damage for each attack, the final damage is not
     * always the same. This checks if a {@link ItemType} type is configured to deal
     * varying damage.
     *
     * @param type to check
     *
     * @return true if this Role is configured to have varying damage for the item
     */
    public boolean doesItemVaryDamage(ItemType type) {
        return this.itemVaryingDamage.containsKey(type) ? this.itemVaryingDamage.get(type) : false;
    }

    /**
     * Gets the damage to add to the base damage for the specified ItemType per level. If the damage
     * increase is not defined, it will default to 0.
     *
     * @param type of item to get the damage increase per level of
     *
     * @return the damage increase per level if not 0
     */
    public Optional<Double> getItemDamagePerLevel(ItemType type) {
        return Optional.fromNullable(this.itemDamagePerLevel.get(type));
    }

    /**
     * Checks if the material type is considered to be a wearable Armor piece for this role.
     *
     * @param type The type of material to check
     *
     * @return True if the material is allowed as an armor piece
     */
    public boolean isArmorAllowed(ItemType type) {
        return this.allowedArmor.contains(type);
    }

    /**
     * Checks if the material type is considered to be an equipable Weapon for this role.
     *
     * @param type The type of material to check
     *
     * @return True if the material is allowed as a weapon
     */
    public boolean isWeaponAllowed(ItemType type) {
        return this.allowedWeapon.contains(type);
    }

    /**
     * Gets the description of this role.
     *
     * @return the description of this role
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Checks if this Role can gain experience from the provided type
     *
     * @param type The type of experience to check
     *
     * @return True if the type of experience can be gained
     */
    public boolean canGainExperience(ExperienceType type) {
        return this.allowedExperience.contains(type);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int hash = 1;
        hash = hash * PRIME + this.name.hashCode();
        hash = hash * PRIME + this.description.hashCode();
        hash = hash * PRIME + this.skills.hashCode();
        hash = hash * PRIME + (this.choosable ? 1 : 0);
        hash = hash * PRIME + this.itemDamages.hashCode();
        hash = hash * PRIME + this.itemDamagePerLevel.hashCode();
        hash = hash * PRIME + this.itemVaryingDamage.hashCode();
        hash = hash * PRIME + this.allowedExperience.hashCode();
        hash = hash * PRIME + this.restrictedSkills.hashCode();
        hash = hash * PRIME + this.children.hashCode();
        hash = hash * PRIME + this.parents.hashCode();
        hash = hash * PRIME + (int) this.hpAt0;
        hash = hash * PRIME + (int) this.hpPerLevel;
        hash = hash * PRIME + this.mpAt0;
        hash = hash * PRIME + this.mpPerLevel;
        hash = hash * PRIME + this.mpRegenAt0;
        hash = hash * PRIME + this.mpRegenPerLevel;
        hash = hash * PRIME + this.type.hashCode();
        hash = hash * PRIME + this.advancementLevel;
        hash = hash * PRIME + this.maxLevel;
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
                && this.skills.equals(role.skills)
                && this.restrictedSkills.equals(role.restrictedSkills)
                && this.type.equals(role.type)
                && this.choosable == role.choosable
                && this.itemDamages.equals(role.itemDamages)
                && this.itemDamagePerLevel.equals(role.itemDamagePerLevel)
                && this.itemVaryingDamage.equals(role.itemVaryingDamage)
                && this.allowedExperience.equals(role.allowedExperience)
                && this.advancementLevel == role.advancementLevel
                && this.maxLevel == role.maxLevel
                && this.children.equals(role.children)
                && this.parents.equals(role.parents)
                && this.hpAt0 == role.hpAt0
                && this.hpPerLevel == role.hpPerLevel
                && this.mpAt0 == role.mpAt0
                && this.mpPerLevel == role.mpPerLevel
                && this.mpRegenAt0 == role.mpRegenAt0
                && this.mpRegenPerLevel == role.mpRegenPerLevel;
    }

    /**
     * Creates a copy of this current role with all settings.
     *
     * @return a copy of this Role
     */
    public Role asNewCopy() {
        return copyOf(this).build();
    }

    /**
     * Standard definition of the various types of Roles.
     */
    public static enum RoleType {

        /**
         * A Primary role. Usually defined as a combat Role with skill granting and health and mana
         * benefits. It may contain restrictions on skill usage provided by other Roles. A {@link
         * com.afterkraft.kraftrpg.api.entity.Sentient} may only have one Primary Role active at any
         * given time.
         */
        PRIMARY,
        /**
         * A Secondary role. Usually a professional Role defining non combat skills and minor health
         * and mana benefits. It may contain restrictions on skill usage provided by other Roles. A
         * {@link com.afterkraft.kraftrpg.api.entity.Sentient} may only have one Secondary Role
         * active at any given time.
         */
        SECONDARY,
        /**
         * An Additional role defines only skills to be granted at level 0 or a progressing skill
         * tree. It does not alter health or mana benefits. Nor does it provide any
         * weapon/armor/item allowances. It should be noted that a {@link
         * com.afterkraft.kraftrpg.api.entity.Sentient} may have several active Additional roles at
         * any given time. Additional roles may still provide skill settings.
         */
        ADDITIONAL
    }

    /**
     * This is a builder for Role.
     */
    public static final class Builder {
        RPGPlugin plugin;
        String name;
        Map<String, RoleSkill> skills = new HashMap<>();
        Map<ItemType, Double> itemDamages = new HashMap<>();
        Map<ItemType, Double> itemDamagePerLevel = new HashMap<>();
        Map<ItemType, Boolean> itemVaryingDamage = new HashMap<>();
        Set<ExperienceType> experienceTypes = new HashSet<>();
        Set<ItemType> allowedArmor = new HashSet<>();
        Set<ItemType> allowedWeapon = new HashSet<>();
        Set<String> restrictedSkills = new HashSet<>();
        RoleType type = RoleType.PRIMARY;
        Set<String> children = new HashSet<>();
        Set<String> parents = new HashSet<>();
        int advancementLevel;
        int maxLevel = 1;
        boolean choosable = true;
        String description;
        String manaName = "Mana";
        double hpAt0 = 20;
        double hpPerLevel;
        int mpAt0 = 100;
        int mpPerLevel;
        int mpRegenAt0 = 1;
        int mpRegenPerLevel;

        Builder(RPGPlugin plugin) {
            this.plugin = plugin;
        }

        /**
         * Set the specified RoleType
         *
         * @param type of Role
         *
         * @return this builder for chaining
         */
        public Builder setType(RoleType type) {
            this.type = type;
            return this;
        }

        /**
         * Set the visible mana name for the role. This name is used to describe to players their
         * current count of the "mana" resource.
         *
         * @param manaName customized name of Mana
         *
         * @return This builder, for chaining
         */
        public Builder setManaName(String manaName) {
            checkArgument(!manaName.isEmpty(), "Cannot have an empty Mana Name!");
            this.manaName = manaName;
            return this;
        }

        public Builder setMpRegenPerLevel(int mpRegenPerLevel) {
            this.mpRegenPerLevel = mpRegenPerLevel;
            return this;
        }

        public Builder setMpPerLevel(int mpPerLevel) {
            checkArgument(mpPerLevel >= 0, "Cannot have a negative mana gained per level!");
            this.mpPerLevel = mpPerLevel;
            return this;
        }

        public Builder setMpRegenAt0(int mpRegenAt0) {
            this.mpRegenAt0 = mpRegenAt0;
            return this;
        }

        public Builder setMpAt0(int mpAt0) {
            checkArgument(mpAt0 > 0, "Cannot have a zero or negative starting Mana!");
            this.mpAt0 = mpAt0;
            return this;
        }

        public Builder setHpPerLevel(double hpPerLevel) {
            checkArgument(hpPerLevel >= 0, "Cannot have a negative health gained per level!");
            this.hpPerLevel = hpPerLevel;
            return this;
        }

        public Builder setHpAt0(double hpAt0) {
            checkArgument(hpAt0 > 0, "Cannot have a zero or negative starting health!");
            this.hpAt0 = hpAt0;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setChoosable(boolean choosable) {
            this.choosable = choosable;
            return this;
        }

        public Builder setMaxLevel(int maxLevel) {
            checkArgument(maxLevel > 0 && maxLevel >= this.advancementLevel,
                    "Cannot have a max level lower than the advancement level or less than zero!");
            this.maxLevel = maxLevel;
            return this;
        }

        public Builder setAdvancementLevel(int advancementLevel) {
            checkArgument(advancementLevel >= 0, "Cannot have a less than zero advancement level!");
            this.advancementLevel = advancementLevel;
            return this;
        }

        public Builder setName(String name) {
            checkArgument(!name.isEmpty(), "Cannot have an empty Role name!");
            this.name = name;
            return this;
        }

        public Builder setItemDamage(ItemType type, double damage) {
            checkArgument(damage > 0, "Cannot have a zero or less than zero damage value!");
            this.itemDamages.put(type, damage);
            return this;
        }

        public Builder setItemDamagePerLevel(ItemType type, double damagePerLevel) {
            checkArgument(damagePerLevel > 0, "Cannot have a zero or less than zero "
                    + "damage per level value!");
            this.itemDamagePerLevel.put(type, damagePerLevel);
            return this;
        }

        /**
         * Set the {@link ItemType} type of item to deal varying damage or not. Setting
         * this will affect all damage immediately.
         *
         * @param type           of ItemType
         * @param doesDamageVary if true, set the item to deal varying damage.
         *
         * @return The builder for chaining
         */
        public Builder setItemDamageVaries(ItemType type, boolean doesDamageVary) {
            if (doesDamageVary) {
                this.itemVaryingDamage.put(type, true);
            } else {
                this.itemVaryingDamage.remove(type);
            }
            return this;
        }

        /**
         * Add a {@link com.afterkraft.kraftrpg.api.skills.ISkill} to be granted at with the
         * specified ConfigurationSection that dictates the level requirements, reagents, skill
         * dependencies amongst other things
         *
         * @param skill   to define
         * @param section defining the settings for the skill being added to this Role
         *
         * @return The builder for chaining
         * @throws IllegalArgumentException if the skill level node is not defined correctly
         * @throws IllegalArgumentException if the skill is listed in the restricted skills
         */
        public Builder addRoleSkill(ISkill skill, DataView section) {
            checkArgument(!skill.getName().isEmpty(), "Cannot have an empty Skill name!");
            checkArgument(section.getInt(SkillSetting.LEVEL.node()).isPresent(),
                    "Level not specified in the skill configuration!");
            checkArgument(!this.restrictedSkills.contains(skill.getName()),
                    "Cannot add a skill that is already restricted!");
            this.skills.put(skill.getName(), new RoleSkill(skill.getName(), section));
            return this;
        }

        /**
         * Add a Role as a child to this role. This will not affect the child role being parented.
         *
         * @param child The child being added to this role
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException if the child was already added as a parent
         */
        public Builder addChild(Role child) {
            checkArgument(!this.parents.contains(child.getName()),
                    "Cannot add a child role when it is already a parent role!");
            this.children.add(child.getName());
            return this;
        }

        /**
         * Add a Role as a child to this role. This will not affect the parent being dependend on.
         *
         * @param parent The parent being added to this role
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException if the parent was already added as a child
         */
        public Builder addParent(Role parent) {
            checkArgument(!this.children.contains(parent.getName()),
                    "Cannot add a parent role when it is already a child role!");
            this.parents.add(parent.getName());
            return this;
        }

        /**
         * Adds an {@link ExperienceType} that this Role may benefit from when a {@link
         * com.afterkraft.kraftrpg.api.entity.Sentient} being is gaining experience.
         *
         * @param type The type of Experience that can be gained
         *
         * @return This builder for chaining
         */
        public Builder addExperienceType(ExperienceType type) {
            this.experienceTypes.add(type);
            return this;
        }

        /**
         * Removes the child from the dependency list. This will not affect the child Role.
         *
         * @param child The child to remove from the dependency list
         *
         * @return This builder for chaining
         */
        public Builder removeChild(Role child) {
            checkArgument(!this.parents.contains(child.getName()),
                    "Cannot remove a child role when it is already a parent role!");
            this.children.remove(child.getName());
            return this;
        }

        /**
         * Adds the specified ItemType type to the allowed armor for this role. Armor can restricted
         * to different roles.
         *
         * @param type Type of armor material
         *
         * @return This builder for chaining
         */
        public Builder addAllowedArmor(ItemType type) {
            this.allowedArmor.add(type);
            return this;
        }

        public Builder addAllowedWeapon(ItemType type) {
            this.allowedWeapon.add(type);
            return this;
        }

        /**
         * Removes the parent from the dependency list. This will not affect the parent Role.
         *
         * @param parent The parent to remove from the dependency list
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException If the parent is also a child
         */
        public Builder removeParent(Role parent) {
            checkArgument(!this.children.contains(parent.getName()),
                    "Cannot remove a parent role when it is already a child role!");
            this.parents.remove(parent.getName());
            return this;
        }

        /**
         * Gets a Role from the present state of the Builder. There are requirements to generating a
         * Role that otherwise, the Builder is in an incomplete state. The requirements are: <ul>
         * <li>A name</li> <li>A description</li> <li>A max level</li> <li>Minimum health at level
         * 0</li> <li>Health gains per level is not 0</li> <li>Mana per level is not 0</li> </ul>
         *
         * @return A newly constructed Role that can not be changed.
         * @throws IllegalStateException If maximum level has not been set
         * @throws IllegalStateException If the health at level zero has not been set
         * @throws IllegalStateException If the health per level gained is less than zero
         * @throws IllegalStateException If the mana gained per level at max level is less than
         *                               zero
         */
        public Role build() {
            checkState(this.advancementLevel > 0, "Cannot have a zero advancement level!");
            checkState(this.maxLevel > 0, "Cannot have a zero max level!");
            checkState(this.hpAt0 > 0, "Cannot have a zero or negative health at level zero!");
            checkState(this.hpPerLevel > 0, "Cannot have a negative health per level!");
            checkState(this.mpPerLevel > 0
                            || this.mpAt0 > (this.maxLevel * this.mpPerLevel + this.mpAt0),
                    "Cannot have a negative mana value at max level!");
            return new Role(this);
        }

        /**
         * Adds a skill to the restricted list. Skills in the restricted list are uncastable while a
         * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} has this role active.
         *
         * @param skill The skill to restrict
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException If the skill is already in the granted skills list
         */
        public Builder addRestirctedSkill(ISkill skill) {
            checkArgument(!this.skills.containsKey(skill.getName()),
                    "Cannot restrict a skill that is already added as a granted skill!");
            this.restrictedSkills.add(skill.getName());
            return this;
        }

        /**
         * Removes a skill from the restricted list. Skills in the restricted list are uncastable
         * while a {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} has this role active.
         *
         * @param skill The skill to un-restrict
         *
         * @return This builder for chaining
         */
        public Builder removeRestrictedSkill(ISkill skill) {
            this.restrictedSkills.remove(skill.getName());
            return this;
        }

    }
}

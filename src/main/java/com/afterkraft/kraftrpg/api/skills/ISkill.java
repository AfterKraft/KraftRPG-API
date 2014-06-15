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

import java.util.Collection;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * Represents a usable Skill for KraftRPG. The recommended use of
 * implementation is {@link Skill}
 */
public interface ISkill {

    /**
     * Returns the generated permission node as a string for this ISkill.
     * 
     * @return the string name permission node for this ISkill
     */
    public String getPermissionNode();

    /**
     * Return the unique name of this ISkill
     * 
     * @return the name of this skill
     */
    public String getName();

    /**
     * Returns the default configuration for this skill.
     * 
     * @return the defalt configuration for this skill
     */
    public ConfigurationSection getDefaultConfig();

    public Collection<SkillSetting> getUsedConfigNodes();

    /**
     * Return the description for this skill. This should be unique for every
     * skill
     * 
     * @return the description for this skill
     */
    public String getDescription();

    /**
     * Set the description for this skill.
     * 
     * @param description the description for this skill
     */
    public void setDescription(String description);

    /**
     * Similar to {@link org.bukkit.plugin.Plugin#onEnable()}, should be used
     * to register any necessary listeners and managers for active use.
     */
    public void initialize();

    /**
     * Similar to {@link org.bukkit.plugin.Plugin#onDisable()}, should be used
     * to unregister any listeners and be prepared for removal.
     */
    public void shutdown();

    /**
     * Adds an Entity as a skill target.
     * 
     * @param entity to add as a target
     * @param caster casting this skill
     * @return true if successful
     */
    public boolean addSkillTarget(Entity entity, SkillCaster caster);

    /**
     * Check if this ISkill is of the requested {@link SkillType}
     * 
     * @param type the type to check
     * @return true if this skill is of the requested type
     */
    public boolean isType(SkillType type);

    /**
     * Checks if the receiving Champion is in meesage range to receive skill
     * messages
     * 
     * @param broadcaster sending the message
     * @param receiver receiving the message
     * @return true if the receiver is in range
     */
    public boolean isInMessageRange(SkillCaster broadcaster, Champion receiver);

    /**
     * Attempts to damage the defending LivingEntity, this allows for various
     * protection plugins to cancel damage events.
     * 
     * @param attacker attempting to deal the damage
     * @param defending entity being damaged
     * @return true if the damage check was successful
     */
    public boolean damageCheck(Insentient attacker, LivingEntity defending);

    public int hashCode();

    public boolean equals(ISkill other);
}

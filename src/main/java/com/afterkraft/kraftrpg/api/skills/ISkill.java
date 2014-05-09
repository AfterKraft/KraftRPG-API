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

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
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
    public Configuration getDefaultConfig();

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
     * Utility method to knockback the {@link org.bukkit.entity.LivingEntity}
     * that is targetted from the direction of the attacking LivingEntity
     * 
     * @param target the target to knockback
     * @param attacker the attacker to make seemingly performing the knockback
     * @param damage the damage to deal from the knockback
     */
    public void knockback(LivingEntity target, LivingEntity attacker, double damage);

    /**
     * Utility method to deal damage to the targetted
     * {@link org.bukkit.entity.LivingEntity} by the attacker. This will set
     * the last damage cause and all other relations properly acting as if the
     * attacker indeed did attack the target.
     * 
     * @param target the targetted LivingEntity to deal damage
     * @param attacker the attacking LivingEntity
     * @param damage total damage to deal to the target
     * @return true if successful or if damage was not cancelled
     */
    public boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage);

    /**
     * Utility method to deal damage to the targetted
     * {@link org.bukkit.entity.LivingEntity} by the attacker. This will set
     * the last damage cause and all other relations properly acting as if the
     * attacker indeed did attack the target.
     * 
     * @param target the targetted LivingEntity to deal damage
     * @param attacker the attacking LivingEntity
     * @param damage total damage to deal to the target
     * @param cause the actual damage cause to call for reason
     * @return true if successful or if damage was not cancelled
     */
    public boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause);

    /**
     * Utility method to deal damage to the targetted
     * {@link org.bukkit.entity.LivingEntity} by the attacker. This will set
     * the last damage cause and all other relations properly acting as if the
     * attacker indeed did attack the target.
     * 
     * @param target the targetted LivingEntity to deal damage
     * @param attacker the attacking LivingEntity
     * @param damage total damage to deal to the target
     * @param cause the actual damage cause to call for reason
     * @param knockback if true, knockback the targetted entity away from the
     *            attacker
     * @return true if successful or if damage was not cancelled
     */
    public boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause, boolean knockback);

    /**
     * @param broadcaster
     * @param receiver
     * @return
     */
    public boolean isInMessageRange(Champion broadcaster, Champion receiver);

    /**
     * @param attacker
     * @param defending
     * @return
     */
    public boolean damageCheck(IEntity attacker, LivingEntity defending);

    public void awardExperience(SkillCaster caster);
}

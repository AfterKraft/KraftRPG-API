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
package com.afterkraft.kraftrpg.api.entity;

import java.util.Collection;

import com.afterkraft.kraftrpg.api.roles.Role;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.Stalled;
import com.afterkraft.kraftrpg.api.util.SkillRequirement;

/**
 * SkillCaster is the core interface that
 * {@link com.afterkraft.kraftrpg.api.skills.ISkill}s use when casting. A
 * SkillCaster is able to cast skills and may have cooldowns for various types
 * of skills and effects.
 */
public interface SkillCaster extends Sentient, PartyMember {

    /**
     * Attempts to remove the designated {@link SkillRequirement} from this
     * caster. Calling this should assume this caster has satisfied the
     * requirement and also assume that the requirement is removed properly.
     * 
     * @param skillRequirement to remove from the caster
     * @throws IllegalArgumentException If the requirement is null
     */
    public void removeSkillRequirement(SkillRequirement skillRequirement);

    /**
     * Get the key'ed cooldown. Used by skills to mark individual cooldowns
     * 
     * @param key name of the cooldown being checked
     * @return the time in milliseconds that the cooldown expires
     * @throws IllegalArgumentException If the key is null
     */
    public Long getCooldown(String key);

    /**
     * Get the global cooldown.
     * 
     * The global cooldown is the cooldown applied for a caster using skills.
     * A Skill may not be used if the global cooldown has not expired.
     * 
     * @return the global cooldown if not 0
     */
    public long getGlobalCooldown();

    /**
     * Sets the global cooldown.
     * 
     * The global cooldown is the cooldown applied for a caster using skills.
     * A Skill may not be used if the global cooldown has not expired.
     * 
     * @param duration of the global cooldown
     * @throws IllegalArgumentException If the duration is negative
     */
    public void setGlobalCooldown(long duration);

    /**
     * Sets the cooldown for anything of a required key.
     * 
     * @param key of the cooldown
     * @param duration of the cooldown, if not 0
     * @throws IllegalArgumentException If the key is null
     * @throws IllegalArgumentException If the key is empty
     * @throws IllegalArgumentException If the duration is negative
     */
    public void setCooldown(String key, long duration);

    /**
     * Fetch the highest level of all active
     * {@link com.afterkraft.kraftrpg.api.roles.Role}s that provide the
     * designated {@link com.afterkraft.kraftrpg.api.skills.ISkill}.
     * 
     * @param skill the skill in question
     * @return the highest level, if none, 0.
     * @throws IllegalArgumentException If the skill is null
     */
    public int getHighestSkillLevel(ISkill skill);

    /**
     * Check if this SkillCaster can use the given skill at their current
     * level.
     * 
     * @param skill skill to check
     * @return true if skill can be used
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean canUseSkill(ISkill skill);

    /**
     * Get all skills accessible at the current level in all roles.
     * 
     * @return an unmodifiable collection of available skills
     */
    public Collection<ISkill> getAvailableSkills();

    /**
     * Same as {@link #getAvailableSkills()}, except return the names, and
     * only of {@link com.afterkraft.kraftrpg.api.skills.Active} skills.
     * 
     * @return names of all skills currently accessible
     */
    public Collection<String> getActiveSkillNames();

    /**
     * Get all skills accessible at the max level in each role.
     * 
     * @return an unmodifiable collection of all possible skills in all roles
     *         currently active
     */
    public Collection<ISkill> getPossibleSkillsInRoles();

    /**
     * Check for all active roles of this Caster whether the requested skill
     * is blocked from use. A Role may block a skill depending on various
     * reasons, such as level or configured disabling of the skill in a role.
     * 
     * @param skill to check
     * @return false if no roles restrict use of the queried skill
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean isSkillRestricted(ISkill skill);

    /**
     * Checks the current primary role if the desired skill can be used. This
     * may return false for various reasons, such as insufficient experience
     * or the designated skill is not actively enabled by the caster
     * 
     * @param skill to check
     * @return true if the caster is allowed to use the skill
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean canPrimaryUseSkill(ISkill skill);

    /**
     * Check if the current primary role explicitly restricts usage of the
     * skill in question.
     * 
     * @param skill to check
     * @return false if the currently active primary role does not restrict
     *         this skill
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean doesPrimaryRestrictSkill(ISkill skill);

    /**
     * Checks the current secondary role if the desired skill can be used.
     * This may return false for various reasons, such as insufficient
     * experience or the designated skill is not actively enabled by the
     * caster
     * 
     * @param skill to check
     * @return true if the caster is allowed to use the skill
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean canSecondaryUseSkill(ISkill skill);

    /**
     * Check if the current secondary role explicitly restricts usage of the
     * skill in question
     * 
     * @param skill to check
     * @return false if the currently active secondary role does not restrict
     *         this skill
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean doesSecondaryRestrictSkill(ISkill skill);

    /**
     * Checks all currently active additional roles if the desired skill can
     * be used. This may return false for various reasons, such as
     * insufficient experience or the designated skill is not actively enabled
     * by the caster in all of the additional roles.
     * 
     * @param skill to check
     * @return true if the caster is allowed to use the skill
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean canAdditionalUseSkill(ISkill skill);

    /**
     * Checks the specific additional role, if active, if the skill can be
     * used. This may return false for various reasons, such as insufficient
     * experience or the designated skill is not enabled by the caster, or if
     * the additional role is not active on the caster.
     * 
     * @param role to check
     * @param skill to check
     * @return true if the role is an active additional role and the caster is
     *         allowed to use the skill
     * @throws IllegalArgumentException If the role is null
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean canSpecificAdditionalUseSkill(Role role, ISkill skill);

    /**
     * Checks all active additional roles whether any of them explicitly
     * restrict usage of the queried skill.
     * 
     * @param skill in question
     * @return false if none of the addtional roles currently active do not
     *         restrict use of the queried skill
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean doesAdditionalRestrictSkill(ISkill skill);

    /**
     * Get the currently stalled skill for this caster, if not null.
     * 
     * @return the currently stalled skill
     */
    public Stalled getStalledSkill();

    /**
     * Sets the currently stalled skill to the one provided. If another skill
     * is already stalled on this caster, then nothing is done.
     * 
     * @param stalledSkill to set
     * @return true if the stalled skill was successfully set.
     */
    public boolean setStalledSkill(Stalled stalledSkill);

    /**
     * Interrupt the current Stalled skill.
     * 
     * @param enemyAction whether the cancellation is the result of another
     *            SkillCaster's skill use
     * @return true if the delayed skill was interrupted
     */
    public boolean cancelStalledSkill(boolean enemyAction);

}

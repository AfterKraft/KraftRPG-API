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

import org.bukkit.Location;

import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.Stalled;
import com.afterkraft.kraftrpg.api.util.SkillRequirement;


/**
 * SkillCaster is the core interface that
 * {@link com.afterkraft.kraftrpg.api.skills.ISkill}s use when casting.
 * skillCasters are not specifically entities and can range from TileEntities
 * to blocks. Custom skillCasters can be interfaced with this way.
 */
public interface SkillCaster extends Sentient, PartyMember {

    public void removeSkillRequirement(SkillRequirement skillRequirement);

    public boolean isDead();

    public Location getLocation();

    /**
     * Get the key'ed cooldown. Used by skills to mark individual cooldowns
     *
     * @param key name of the cooldown being checked
     * @return the time in milliseconds that the cooldown expires
     */
    public Long getCooldown(String key);

    /**
     * Get the global cooldown
     *
     * @return the global cooldown if not 0
     */
    public long getGlobalCooldown();

    public void setGlobalCooldown(long duration);

    public void setCooldown(String key, long duration);

    /**
     * Fetch the highest level of all active
     * {@link com.afterkraft.kraftrpg.api.entity.roles.Role}s that provide the
     * designated {@link com.afterkraft.kraftrpg.api.skills.ISkill}.
     *
     * @param skill the skill in question
     * @return the highest level, if none, 0.
     */
    public int getHighestSkillLevel(ISkill skill);

    /**
     * Check if this SkillCaster can use the given skill at their current
     * level.
     *
     * @param skill skill to check
     * @return true if skill can be used
     */
    public boolean canUseSkill(ISkill skill);

    /**
     * Get all skills accessible at the current level in all roles.
     *
     * @return
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
     * @return
     */
    public Collection<ISkill> getPossibleSkillsInRoles();

    /**
     * @param skill
     * @return
     */
    public boolean isSkillRestricted(ISkill skill);

    /**
     * @param skill
     * @return
     */
    public boolean canPrimaryUseSkill(ISkill skill);

    /**
     * @param skill
     * @return
     */
    public boolean doesPrimaryRestrictSkill(ISkill skill);

    /**
     * @param skill
     * @return
     */
    public boolean canSecondaryUseSkill(ISkill skill);

    /**
     * @param skill
     * @return
     */
    public boolean doesSecondaryRestrictSkill(ISkill skill);

    /**
     * @param skill
     * @return
     */
    public boolean canAdditionalUseSkill(ISkill skill);

    /**
     * @param role
     * @param skill
     * @return
     */
    public boolean canSpecificAdditionalUseSkill(Role role, ISkill skill);

    /**
     * @param skill
     * @return
     */
    public boolean doesAdditionalRestrictSkill(ISkill skill);

    /**
     * @return
     */
    public Stalled getStalledSkill();

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

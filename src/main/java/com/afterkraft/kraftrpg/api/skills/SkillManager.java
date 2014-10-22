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

import java.util.Collection;

import org.bukkit.entity.Entity;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.common.Permissible;

/**
 * Manages skills with both skill loading and skill management.
 */
public interface SkillManager extends Manager {

    /**
     * Adds a skill to the skill mapping
     *
     * @param skill The skill in question to add
     */
    void addSkill(ISkill skill);

    /**
     * Checks if a skill by the skill name exists.
     *
     * @param skillName to check
     *
     * @return true if a Skill exists by the name
     * @throws java.lang.IllegalArgumentException if the name is null
     */
    boolean hasSkill(String skillName);

    /**
     * Returns a skill from it's name If the skill is not in the skill mapping it will attempt to
     * load it from file
     *
     * @param name The name of the skill
     *
     * @return The skill
     */
    ISkill getSkill(String name);

    /**
     * Attempt to load the requested skill name that is to represent a {@link Permissible} skill.
     *
     * @param name to create
     *
     * @return true if successful
     */
    boolean loadPermissionSkill(String name);

    /**
     * Returns a collection of all skills loaded in the skill manager
     *
     * @return a live collection of all loaded skills
     */
    Collection<ISkill> getSkills();

    /**
     * Checks if a skill has already been loaded
     *
     * @param name of the skill
     *
     * @return true if the skill has been initialized
     */
    boolean isLoaded(String name);

    /**
     * Removes a skill from the skill mapping
     *
     * @param skill to remove
     */
    void removeSkill(ISkill skill);

    /**
     * Check if the {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} has an actively {@link
     * Stalled} Skill.
     *
     * @param caster the caster in question
     *
     * @return true if the specified caster is in process of casting a Stalled skill
     */
    boolean isCasterDelayed(SkillCaster caster);

    /**
     * Gets the currently stalled skill the caster is about to cast.
     *
     * @param caster The caster in question
     *
     * @return The stalled skill for the caster
     */
    Stalled getDelayedSkill(SkillCaster caster);

    void setCompletedSkill(SkillCaster caster);

    void addSkillTarget(Entity entity, SkillCaster caster, ISkill skill);

    SkillUseObject getSkillTargetInfo(Entity o);

    boolean isSkillTarget(Entity entity);

    void removeSkillTarget(Entity entity, SkillCaster caster, ISkill skill);
}

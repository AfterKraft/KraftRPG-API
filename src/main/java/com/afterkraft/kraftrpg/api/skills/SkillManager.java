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

import org.bukkit.entity.Entity;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;


public interface SkillManager extends Manager {

    /**
     * Adds a skill to the skill mapping
     *
     * @param skill
     */
    public void addSkill(ISkill skill);

    /**
     * Returns a skill from it's name If the skill is not in the skill mapping
     * it will attempt to load it from file
     *
     * @param name
     * @return
     */
    public ISkill getSkill(String name);

    public boolean loadOutsourcedSkill(String name);

    /**
     * Returns a collection of all skills loaded in the skill manager
     *
     * @return
     */
    public Collection<ISkill> getSkills();

    /**
     * Checks if a skill has already been loaded
     *
     * @param name
     * @return
     */
    public boolean isLoaded(String name);

    /**
     * Removes a skill from the skill mapping
     *
     * @param skill
     */
    public void removeSkill(ISkill skill);

    /**
     * Check if the {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} has
     * an actively {@link Stalled} Skill.
     *
     * @param caster the caster in question
     * @return
     */
    public boolean isCasterDelayed(SkillCaster caster);

    /**
     * @param caster
     * @return
     */
    public Stalled<? extends SkillArgument> getDelayedSkill(SkillCaster caster);

    public void setCompletedSkill(SkillCaster caster);

    public void addSkillTarget(Entity o, SkillCaster caster, ISkill skill);

    public SkillUseObject<? extends SkillArgument> getSkillTargetInfo(Entity o);

    public boolean isSkillTarget(Entity o);
}

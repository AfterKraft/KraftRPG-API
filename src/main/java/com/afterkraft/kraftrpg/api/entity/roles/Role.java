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
package com.afterkraft.kraftrpg.api.entity.roles;

import java.util.Map;
import java.util.Set;

import org.bukkit.Material;

import com.afterkraft.kraftrpg.api.skills.ISkill;


/**
 * A Role represents a tree like structure for granting Skills and other bonuses
 * to {@link com.afterkraft.kraftrpg.api.entity.Sentient} beings.
 */
public interface Role {

    /**
     * Get the type of Role this is.
     *
     * @return the {@link com.afterkraft.kraftrpg.api.entity.roles.RoleType}
     */
    public RoleType getType();

    /**
     * Return the configured name for this Role
     *
     * @return the name for this role
     */
    public String getName();

    /**
     * Check if this Role has the given Skill
     *
     * @param skill
     * @return
     */
    public boolean hasSkill(ISkill skill);

    public boolean hasSkill(String name);

    public boolean addSkill(ISkill skill);

    /**
     * Removes the skill
     *
     * @param skill
     */
    public void removeSkill(ISkill skill);

    /**
     * Check if the given skill has a prerequisite skill dependency in this
     * Role
     *
     * @param skill the skill in question
     * @return
     */
    public boolean hasPrerequisite(ISkill skill);

    public boolean hasPrerequisite(String name);

    public Set<ISkill> getSkillDependency(ISkill skill);

    public Set<ISkill> getSkillDependency(String name);

    public Map<ISkill, Set<ISkill>> getSkillDependencies();

    public boolean addSkillDependency(ISkill skill, ISkill dependency);

    public void removeSkillDependency(ISkill skill, ISkill dependency);

    public void removeSkillDependency(ISkill skill);

    public double getItemDamage(Material type);

    public void setItemDamage(Material type, double damage);

    public double getItemDamagePerLevel(Material type);

    public void setItemDamagePerLevel(Material type, double damage);

}

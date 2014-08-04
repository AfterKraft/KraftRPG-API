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

import org.bukkit.entity.Entity;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

public interface Targeted<E extends Entity> extends Active {


    /**
     * Apply this skill using the previously parsed state and includes the
     * defined Entity target that is found with raytracing.
     *
     * @param caster The caster using this skill
     * @param target The found target for this targeted skill
     * @return final SkillCastResults
     */
    public SkillCastResult useSkill(SkillCaster caster, E target);
}

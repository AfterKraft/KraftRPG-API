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

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * Represents a passive skill that can not be activated by command or bind.
 * The recommended use of implementation is
 * {@link com.afterkraft.kraftrpg.api.skills.PassiveSkill}
 */
public interface Passive extends ISkill {

    /**
     * Attempts to apply this passive skill to the given
     * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}
     * 
     * @param caster to attempt to apply this passive skill to
     * @return true if successful
     */
    public boolean apply(SkillCaster caster);

    /**
     * Attempts to remove the passive skill from the given SkillCaster. It
     * should be noted that all references to the caster should be forgotten
     * and any update checks should ignore this caster.
     * 
     * @param caster to unapply this passive skill to
     */
    public void remove(SkillCaster caster);
}

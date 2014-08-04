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
package com.afterkraft.kraftrpg.api.skills.common;

import com.afterkraft.kraftrpg.api.effects.common.Imbuing;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.Active;

/**
 * A standard {@link com.afterkraft.kraftrpg.api.skills.ISkill} that will apply
 * a {@link com.afterkraft.kraftrpg.api.effects.common.Imbuing} effect on skill use.
 * It has a default implementation with the following common abstract skills:
 * <ul>
 *     <li>{@link ArrowSkill}</li>
 * </ul>
 */
public interface ImbuingSkill extends Active {

    /**
     * Abstract callback method by all implementations to apply an {@link Imbuing}
     * effect according to the provided SkillCaster.
     *
     * @param caster The caster using this skill.
     * @param maxUses The configured max uses for the Imbued effect.
     */
    public void addImbueEffect(SkillCaster caster, int maxUses);
}

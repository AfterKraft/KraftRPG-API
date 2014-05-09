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

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * Represents an Active Skill that requires a
 * {@link org.bukkit.entity.LivingEntity} as a target.
 */
public interface Targeted extends Active {

    /**
     * Primary designated method to use this skill on the targetted
     * LivingEntity
     * 
     * @param champion
     * @param entity
     * @param argument
     * @return
     */
    public SkillCastResult use(Champion champion, LivingEntity entity, String[] argument);

}

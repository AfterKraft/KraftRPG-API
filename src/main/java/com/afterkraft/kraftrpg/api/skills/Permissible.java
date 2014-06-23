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

import java.util.Map;

import com.afterkraft.kraftrpg.api.entity.Sentient;

/**
 * Represents a Permissive Skill
 */
public interface Permissible extends ISkill {

    /**
     * Set this permission skill to apply the specified
     * permission mapping.
     *
     * @param permissions to apply
     */
    public void setPermissions(Map<String, Boolean> permissions);

    /**
     * Try to learn this permission skill's permission node along with
     * any other permissions related to this Permissible skill.
     *
     * @param being to learn this skill
     */
    public void tryLearning(Sentient being);

    /**
     * Try to unlearn this permission skill's permission node along with
     * any other permissions related to this Permissible skill.
     *
     * @param being to unlearn
     */
    public void tryUnlearning(Sentient being);
}

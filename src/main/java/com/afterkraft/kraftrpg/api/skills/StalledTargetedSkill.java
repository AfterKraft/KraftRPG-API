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

import com.afterkraft.kraftrpg.api.entity.SkillCaster;


public class StalledTargetedSkill<T extends TargetedSkillArgument> extends StalledSkill<T> implements StalledTarget<T> {

    private LivingEntity target;

    public StalledTargetedSkill(Active<T> skill, T args, SkillCaster caster, LivingEntity target, long startTime, long warmup) {
        super(skill, args, caster, startTime, warmup);
        this.target = target;
    }

    @Override
    public LivingEntity getTarget() {
        return target;
    }
}

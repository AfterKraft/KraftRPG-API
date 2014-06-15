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

import java.lang.ref.WeakReference;
import java.util.List;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

public final class SkillUseObject {

    private final WeakReference<SkillCaster> entity;
    private final WeakReference<ISkill> skill;
    private final WeakReference<List<SkillArgument>> argument;


    public SkillUseObject(SkillCaster entity, ISkill skill, List<SkillArgument> argument) {
        this.entity = new WeakReference<SkillCaster>(entity);
        this.skill = new WeakReference<ISkill>(skill);
        this.argument = new WeakReference<List<SkillArgument>>(argument);
    }

    public SkillCaster getCaster() {
        return this.entity.get();
    }

    public ISkill getSkill() {
        return this.skill.get();
    }

    public List<SkillArgument> getArgument() {
        return this.argument.get();
    }
}

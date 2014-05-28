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

import com.afterkraft.kraftrpg.api.entity.Insentient;

public final class SkillUseObject<T extends SkillArgument> {

    private final WeakReference<Insentient> entity;
    private final WeakReference<ISkill> skill;
    private final WeakReference<T> argument;


    public SkillUseObject(Insentient entity, ISkill skill, T argument) {
        this.entity = new WeakReference<Insentient>(entity);
        this.skill = new WeakReference<ISkill>(skill);
        this.argument = new WeakReference<T>(argument);
    }

    public Insentient getEntity() {
        return this.entity.get();
    }

    public ISkill getSkill() {
        return this.skill.get();
    }

    public T getArgument() {
        return this.argument.get();
    }
}

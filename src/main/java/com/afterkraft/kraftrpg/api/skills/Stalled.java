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

import java.util.concurrent.Delayed;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * Represents a Skill that is in process of being queued for casting.
 */
public interface Stalled<T extends SkillArgument> extends Delayed {

    public boolean isReady();

    public long startTime();

    public T getArgument();

    public Active<T> getActiveSkill();

    public SkillCaster getCaster();

}

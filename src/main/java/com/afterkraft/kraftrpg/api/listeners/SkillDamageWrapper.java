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
package com.afterkraft.kraftrpg.api.listeners;

import java.lang.ref.WeakReference;

import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.ISkill;

public class SkillDamageWrapper extends AttackDamageWrapper {

    private final ISkill skillUsed;

    public SkillDamageWrapper(SkillCaster attackingIEntity, ISkill skill, EntityDamageEvent.DamageCause originalCause, double originalDamage, double modifiedDamage, EntityDamageEvent.DamageCause modifiedCause) {
        super(attackingIEntity, originalCause, originalDamage, modifiedDamage, modifiedCause);
        this.skillUsed = skill;
    }

    @Override
    public SkillCaster getAttackingIEntity() {
        return (SkillCaster) super.getAttackingIEntity();
    }

    public ISkill getSkillUsed() {
        return skillUsed;
    }
}

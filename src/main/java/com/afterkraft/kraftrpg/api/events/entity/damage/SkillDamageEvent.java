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
package com.afterkraft.kraftrpg.api.events.entity.damage;

import java.util.Map;

import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

public class SkillDamageEvent extends InsentientDamageInsentientEvent {

    public SkillDamageEvent(final SkillCaster attacker, final Insentient defender, final EntityDamageByEntityEvent event, final Map<DamageType, Double> modifiers, boolean isVaryingEnabled) {
        super(attacker, defender, event, modifiers, isVaryingEnabled);
    }

    @Override
    public final SkillCaster getAttacker() {
        return (SkillCaster) super.getAttacker();
    }

}

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
package com.afterkraft.kraftrpg.api.events.entity;

import java.util.WeakHashMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.EnterCombatReason;
import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * This event is called when a SkillCaster enters combat due to buffing or
 * healing a Sentient already in combat with the 'other'.
 */
public class HelperEnterCombatEvent extends EnterCombatEvent {
    private Sentient buffed;

    public HelperEnterCombatEvent(SkillCaster being, Sentient buffed, Sentient other, WeakHashMap<LivingEntity, EnterCombatReason> combatMap, EnterCombatReason reason) {
        super(being, other, combatMap, reason);
    }

    public final Sentient getBuffedBeing() {
        return this.buffed;
    }
}

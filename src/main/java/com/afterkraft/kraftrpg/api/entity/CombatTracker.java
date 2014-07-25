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
package com.afterkraft.kraftrpg.api.entity;

import java.util.Map;

import com.afterkraft.kraftrpg.api.Manager;

/**
 * Simple manager handling combat for various
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} beings. This is
 * mostly used to manage {@link com.afterkraft.kraftrpg.api.entity.Champion}
 * combat.
 */
public interface CombatTracker extends Manager {

    /**
     * Get a map of all current combatants with the targeted Insentient.
     * The map is unmodifiable and can not be added to or removed from.
     *
     * @param target in combat
     * @return A newly constructed Map of all Insentient attackers and the reasons for entering combat with them.
     * @throws IllegalArgumentException if the target is null
     */
    public Map<Insentient, EnterCombatReason> getCombatants(Insentient target);

    /**
     * Adds the target to be in combat with the attacker.
     * 
     * @param target
     * @param attacker
     * @param reason
     * @throws IllegalArgumentException if the target is null
     * @throws IllegalArgumentException if the attacker is null
     * @throws IllegalArgumentException if the reason is null
     */
    public void enterCombatWith(Insentient target, Insentient attacker, EnterCombatReason reason);

    /**
     * Performs the leave combat operation on the target by the attacker.
     * Depending on the reason, the attacker may be granted some buffs or
     * something.
     * 
     * @param target leaving combat
     * @param attacker that was in combat with the target
     * @param reason for leaving combat
     * @throws IllegalArgumentException if the target is null
     * @throws IllegalArgumentException if the attacker is null
     * @throws IllegalArgumentException if the reason is null
     */
    public void leaveCombatWith(Insentient target, Insentient attacker, LeaveCombatReason reason);

    /**
     * Notifies all combatants that the specified target is leaving combat with
     * the specified reason.
     *
     * @param target leaving combat
     * @param reason for leaving combat
     * @throws IllegalArgumentException if the target is null
     * @throws IllegalArgumentException if the reason is null
     */
    public void leaveCombat(Insentient target, LeaveCombatReason reason);

    /**
     * Check if the being is in combat with any entity for any reason.
     * 
     * @param being to check
     * @return true if the being is in combat with any other attacker for any
     *         reason
     * @throws IllegalArgumentException if the being is null
     */
    public boolean isInCombat(Insentient being);

    /**
     * Check directly if the target is in combat with the potentialAttacker.
     * 
     * @param target to check
     * @param potentialAttacker that might be in combat
     * @return true if the target is still in combat with the
     *         potentialAttacker
     * @throws IllegalArgumentException if the target is null
     * @throws IllegalArgumentException if the potentialAttacker is null
     */
    public boolean isInCombatWith(Insentient target, Insentient potentialAttacker);
}

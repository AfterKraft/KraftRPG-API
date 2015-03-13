/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.api.entity.combat;

import java.util.Map;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Simple manager handling combat for various {@link com.afterkraft.kraftrpg.api.entity.Insentient}
 * beings. This is mostly used to manage {@link com.afterkraft.kraftrpg.api.entity.Champion}
 * combat.
 */
public interface CombatTracker extends Manager {

    /**
     * Get a map of all current combatants with the targeted Insentient. The map is unmodifiable and
     * can not be added to or removed from.
     *
     * @param target in combat
     *
     * @return A newly constructed Map of all Insentient attackers and the reasons for entering
     * combat with them.
     * @throws IllegalArgumentException if the target is null
     */
    Map<Insentient, EnterCombatReason> getCombatants(Insentient target);

    /**
     * Adds the target to be in combat with the attacker.
     *
     * @param target   The target
     * @param attacker The attacker
     * @param reason   The reason
     *
     * @throws IllegalArgumentException if the target is null
     * @throws IllegalArgumentException if the attacker is null
     * @throws IllegalArgumentException if the reason is null
     */
    void enterCombatWith(Insentient target, Insentient attacker,
                         EnterCombatReason reason);

    /**
     * Performs the leave combat operation on the target by the attacker. Depending on the reason,
     * the attacker may be granted some buffs or something.
     *
     * @param target   leaving combat
     * @param attacker that was in combat with the target
     * @param reason   for leaving combat
     *
     * @throws IllegalArgumentException if the target is null
     * @throws IllegalArgumentException if the attacker is null
     * @throws IllegalArgumentException if the reason is null
     */
    void leaveCombatWith(Insentient target, Insentient attacker,
                         LeaveCombatReason reason);

    /**
     * Notifies all combatants that the specified target is leaving combat with the specified
     * reason.
     *
     * @param target leaving combat
     * @param reason for leaving combat
     *
     * @throws IllegalArgumentException if the target is null
     * @throws IllegalArgumentException if the reason is null
     */
    void leaveCombat(Insentient target, LeaveCombatReason reason);

    /**
     * Check if the being is in combat with any entity for any reason.
     *
     * @param being to check
     *
     * @return true if the being is in combat with any other attacker for any reason
     * @throws IllegalArgumentException if the being is null
     */
    boolean isInCombat(Insentient being);

    /**
     * Check directly if the target is in combat with the potentialAttacker.
     *
     * @param target            to check
     * @param potentialAttacker that might be in combat
     *
     * @return true if the target is still in combat with the potentialAttacker
     * @throws IllegalArgumentException if the target is null
     * @throws IllegalArgumentException if the potentialAttacker is null
     */
    boolean isInCombatWith(Insentient target, Insentient potentialAttacker);
}

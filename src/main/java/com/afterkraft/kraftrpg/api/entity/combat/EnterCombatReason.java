/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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

/**
 * Derp
 */
public enum EnterCombatReason {

    /**
     * The Sentient was hit by a non-Champion Insentient
     */
    DAMAGED_BY_MOB,
    /**
     * The Sentient was hit by a Champion
     */
    DAMAGED_BY_PLAYER,
    /**
     * The Sentient attacked a non-Champion
     */
    ATTACKED_MOB,
    /**
     * The Sentient attacked a Champion
     */
    ATTACKED_PLAYER,
    /**
     * The SkillCaster used a buff on a Sentient already in combat
     */
    BUFFED_COMBATANT,
    /**
     * A SkillCaster used a buff on a Sentient in combat with this one
     */
    BUFFED_COMBATANT_RECIPROCAL,
}

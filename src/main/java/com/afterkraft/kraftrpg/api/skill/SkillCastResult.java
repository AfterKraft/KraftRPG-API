/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.skill;

/**
 * A result of a skill cast. This is a descriptive representation for the result of an attempt to
 * cast a Skill.
 */
public enum SkillCastResult {

    /**
     * Generic success. <b>(Success)</b>
     */
    SUCCESS,
    /**
     * The caster is dead.
     */
    DEAD,
    /**
     * The caster can't use this skill.
     */
    NOT_AVAILABLE,
    /**
     * Can't pay mana cost.
     */
    LOW_MANA,
    /**
     * Can't pay health cost.
     */
    LOW_HEALTH,
    /**
     * Can't pay hunger (stamina) cost.
     */
    LOW_STAMINA,
    /**
     * Can't pay reagent (Item) cost.
     */
    MISSING_REAGENT,
    /**
     * The skill can't be used in combat, but you're in combat.
     */
    NO_COMBAT,
    /**
     * A Stalled use of the skill was started. <b>(Success)</b>
     */
    START_DELAY,
    /**
     * The Stalled use of the skill was cancelled.
     */
    EVENT_CANCELLED,
    /**
     * The skill can't be used due to something about the current Stalled skill.
     */
    STALLING_FAILURE,
    /**
     * The Global Cooldown is active.
     */
    ON_GLOBAL_COOLDOWN,
    /**
     * The skill is on cooldown.
     */
    ON_COOLDOWN,
    /**
     * The skill is warming up before casting.
     */
    ON_WARMUP,
    /**
     * The skill failed.
     */
    FAIL,
    /**
     * The skill has decided that the provided target is invalid.
     */
    INVALID_TARGET,
    /**
     * The skill has decided the the provided target is invalid, but does not wish to message the
     * caster.
     */
    INVALID_TARGET_NO_MESSAGE,
    /**
     * The skill has decided that the caster isn't allowed to target this.
     */
    UNTARGETABLE_TARGET,
    /**
     * The skill has sent its own message, and the caller should not print one.
     */
    CUSTOM_NO_MESSAGE_FAILURE,
    /**
     * The provided arguments do not make sense for the skill.
     */
    SYNTAX_ERROR
}

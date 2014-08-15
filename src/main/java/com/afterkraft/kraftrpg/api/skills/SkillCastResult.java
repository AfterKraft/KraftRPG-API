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


public enum SkillCastResult {

    /**
     * Generic success. <b>(Success)</b>
     */
    NORMAL,
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
     * The skill can't be used due to something about the current Stalled
     * skill.
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
     * XXX what is this
     */
    REMOVED_EFFECT,
    /**
     * XXX what is this
     */
    SKIP_POST_USAGE,
    /**
     * The skill has decided that the provided target is invalid.
     */
    INVALID_TARGET,
    /**
     * The skill has decided the the provided target is invalid, but does not
     * wish to message the caster.
     */
    INVALID_TARGET_NO_MESSAGE,
    /**
     * The skill has decided that the caster isn't allowed to target this.
     */
    UNTARGETABLE_TARGET,
    /**
     * The skill has sent its own message, and the caller should not print
     * one.
     */
    CUSTOM_NO_MESSAGE_FAILURE,
    /**
     * The provided arguments do not make sense for the skill.
     */
    SYNTAX_ERROR
}

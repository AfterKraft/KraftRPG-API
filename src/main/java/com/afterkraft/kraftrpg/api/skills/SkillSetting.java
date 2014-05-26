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


import java.util.EnumSet;
import java.util.Set;

public enum SkillSetting {

    /////////////////////////////////////////////////////////////////
    // Automatically inspected and applied
    // - Numbers
    // (Level)
    /**
     * Level required to use the skill. Mandatory. Cannot have a default.
     */
    LEVEL("level"),
    // (Other numbers)
    /**
     * Cooldown, in ****s, before the skill can be used a second time.
     */
    COOLDOWN("cooldown", true),
    /**
     * Delay between when the skill is used and when it is cast, in ****s.
     */
    DELAY("delay", true),
    /**
     * Experience awarded after each use of the skill.
     */
    EXP("exp-award", true),
    /**
     * HP deducted for each use of the skill.
     */
    HEALTH_COST("health-cost", true),
    /**
     * MP deducted for each use of the skill.
     */
    MANA_COST("mana-cost", true),
    /**
     * Hunger deducted for each use of the skill.
     */
    STAMINA_COST("stamina-cost", true),
    /**
     * Experience granted for each use of the skill.
     */
    EXP_ON_CAST("xp-on-cast", false),
    // - ItemStack
    /**
     * Item deducted for each use of the skill.
     */
    REAGENT("reagent"),
    /**
     * How many items are deducted. This will always be converted to a whole
     * number before use.
     */
    REAGENT_QUANTITY("reagent-amount", true),
    // - Bools
    /**
     * If true, the skill cannot be used while in combat.
     */
    NO_COMBAT_USE("no-combat-use"),
    // - Other
    /**
     * How many skill points it costs to learn the skill once the level
     * requirement is met. (TODO)
     */
    SKILL_POINT_COST("skill-point-cost"),

    /////////////////////////////////////////////////////////////////
    // Applied by the skill - must be declared to show up in auto config!
    // - Numbers
    AMOUNT("amount"),
    /**
     * How likely the skill is to succeed, on a scale of 0-1.
     */
    CHANCE("chance", true),
    DURATION("duration", true),
    DAMAGE("damage", true),
    DAMAGE_RECURRING("damage-per-tick", true),
    HEALING("healing", true),
    HEALING_RECURRING("healing-per-tick", true),
    /**
     * Maximum distance that the skill will search.
     */
    MAX_DISTANCE("max-distance", true),
    MAX_DISTANCE_CAP("max-distance-cap", false),
    RECURRENCE_PERIOD("period", true),
    /**
     * The distance, centered on the player, that the skill affects.
     */
    RADIUS("radius", true),
    RADIUS_CAP("radius-cap", false),

    /**
     * The presence of this SkillSetting in your Used Config Nodes declaration
     * signifies that you need extra data other than the provided variables.
     */
    CUSTOM(null),

    /**
     * The presence of this SkillSetting in your Used Config Nodes declaration
     * signifies that you need some per-player setting storage.
     */
    CUSTOM_PER_CHAMPION(null),

    // Strings
    APPLY_TEXT("apply-text"),
    DEATH_TEXT("death-text"),
    DELAY_TEXT("delay-text"),
    EXPIRE_TEXT("expire-text"),
    INTERRUPT_TEXT("interrupt-text"),
    UNAPPLY_TEXT("unapply-text"),
    USE_TEXT("use-text"),
    ;

    private final String node;
    private final boolean scaled;

    SkillSetting(String node) {
        this(node, false);
    }

    SkillSetting(String node, boolean scaled) {
        this.node = node;
        this.scaled = scaled;
    }

    public static final Set<SkillSetting> AUTOMATIC_SETTINGS = EnumSet.of(
            LEVEL, COOLDOWN, DELAY, EXP, HEALTH_COST, MANA_COST, STAMINA_COST,
            REAGENT, REAGENT_QUANTITY, NO_COMBAT_USE, SKILL_POINT_COST);

    public static final Set<SkillSetting> BOOLEAN_SETTINGS = EnumSet.of(NO_COMBAT_USE);

    public static final Set<SkillSetting> STRING_SETTINGS = EnumSet.of(
            APPLY_TEXT, DEATH_TEXT, DELAY_TEXT, EXPIRE_TEXT, INTERRUPT_TEXT,
            UNAPPLY_TEXT, USE_TEXT);

    public String node() {
        return this.node;
    }

    public boolean isLevelScaled() {
        return scaled;
    }

    public String scalingNode() {
        if (!scaled) return null;

        return node + "-per-level";
    }

    @Override
    public String toString() {
        return this.node;
    }
}

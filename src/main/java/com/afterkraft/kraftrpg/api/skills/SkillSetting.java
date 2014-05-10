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


public enum SkillSetting {
    // Level
    LEVEL("level"),

    // Automatically inspected and applied
    // - Numbers
    COOLDOWN("cooldown", true),
    DELAY("delay", true),
    EXP("exp-award", true),
    HEALTH_COST("health-cost", true),
    MANA_COST("mana-cost", true),
    STAMINA_COST("stamina-cost", true),
    // - ItemStack
    REAGENT("reagent"),
    REAGENT_QUANTITY("reagent-amount", true),
    // - Bools
    NO_COMBAT_USE("no-combat-use"),
    // - Other
    SKILL_POINT_COST("skill-point-cost"),

    // Applied by the skill - must be declared to show up in auto config!
    // - Numbers
    AMOUNT("amount"),
    CHANCE("chance", true),
    DURATION("duration", true),
    DAMAGE("damage", true),
    DAMAGE_RECURRING("damage-per-tick", true),
    HEALING("healing", true),
    HEALING_RECURRING("healing-per-tick", true),
    MAX_DISTANCE("max-distance", true),
    MAX_DISTANCE_CAP("max-distance-cap", false),
    RECURRENCE_PERIOD("period", true),
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

    // Attribute specific types. We need to match these manually with {@link AttributeType}
    ATTRIBUTE_MANA_COST_REDUCTION("attribute-mana-cost-reduction"),
    ATTRIBUTE_MANA_COST_INCREASE("attribute-mana-cost-increase"),
    ATTRIBUTE_HEALTH_COST_INCREASE("attribute-health-cost-increase"),
    ATTRIBUTE_HEALTH_COST_REDUCTION("attribute-health-cost-reduction"),
    ATTRIBUTE_SKILL_DAMAGE_INCREASE("attribute-skill-damage-increase"),
    ATTRIBUTE_SKILL_DAMAGE_REDUCTION("sattribute-kill-damage-reduction"),
    ATTRIBUTE_SKILL_COOLDOWN_REDUCTION("attribute-skill-cooldown-reduction"),
    ATTRIBUTE_SKILL_COOLDOWN_INCRASE("attribute-skill-cooldown-increase"),
    ATTRIBUTE_SKILL_BUFF_INCREASE("attribute-skill-buff-increase"),
    ATTRIBUTE_SKILL_BUFF_REDUCTION("attribute-skill-buff-reduction");

    private final String node;
    private final boolean scaled;

    SkillSetting(String node) {
        this(node, false);
    }

    SkillSetting(String node, boolean scaled) {
        this.node = node;
        this.scaled = scaled;
    }

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

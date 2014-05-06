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

    AMOUNT("amount"),
    APPLY_TEXT("apply-text"),
    CHANCE("chance"),
    CHANCE_PER_LEVEL("chance-per-level"),
    COOLDOWN("cooldown"),
    DAMAGE("damage"),
    DAMAGE_TICK("tick-damage"),
    DEATH_TEXT("death-text"),
    DELAY_TEXT("delay-text"),
    DELAY("delay"),
    DURATION("duration"),
    DURATION_INCREASE_PER_LEVEL("duration-increase-per-level"),
    EXP("exp"),
    EXPIRE_TEXT("expire-text"),
    HEALING("healing"),
    HEALING_TICK("tick-healing"),
    HEALTH_COST("health-cost"),
    INTERRUPT_TEXT("interrupt-text"),
    LEVEL("level"),
    SKILL_POINT_COST("skill-point-cost"),
    MANA("mana"),
    MANA_REDUCE_PER_LEVEL("mana-reduce-per-level"),
    MAX_DISTANCE("max-distance"),
    NO_COMBAT_USE("no-combat-use"),
    PERIOD("period"),
    RADIUS("radius"),
    REAGENT_COST("reagent-cost"),
    REAGENT("reagent"),
    STAMINA("stamina"),
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

    SkillSetting(String node) {
        this.node = node;
    }

    public String node() {
        return this.node;
    }

    @Override
    public String toString() {
        return this.node;
    }
}
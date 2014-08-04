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

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Centralized Skill Setting table for KraftRPG-API
 */
public class SkillSetting {

    /**
     * Level required to use the skill. Mandatory. Cannot have a default.
     */
    public static final SkillSetting LEVEL = new SkillSetting("level");
    /**
     * Depends on another skill being granted. Prevents use of the skill when
     * the depended skill is not activated. Defaults to none
     */
    public static final SkillSetting DEPENDS = new SkillSetting("depends");
    /**
     * An immutable set of SkillSettings that are normally considered lists.
     */
    public static final Set<SkillSetting> LIST_SETTINGS = ImmutableSet.of(DEPENDS);
    /**
     * Cooldown, in milliseconds, before the skill can be used a second time.
     */
    public static final SkillSetting COOLDOWN = new SkillSetting("cooldown", true);
    /**
     * Delay between when the skill is used and when it is cast, in milliseconds.
     */
    public static final SkillSetting DELAY = new SkillSetting("delay", true);
    /**
     * Experience awarded after each use of the skill.
     */
    public static final SkillSetting EXP = new SkillSetting("exp-award", true);
    /**
     * HP deducted for each use of the skill.
     */
    public static final SkillSetting HEALTH_COST = new SkillSetting("health-cost", true);
    /**
     * MP deducted for each use of the skill.
     */
    public static final SkillSetting MANA_COST = new SkillSetting("mana-cost", true);
    /**
     * Hunger deducted for each use of the skill.
     */
    public static final SkillSetting STAMINA_COST = new SkillSetting("stamina-cost", true);
    /**
     * Experience removed for each use of the skill.
     */
    public static final SkillSetting EXP_ON_CAST = new SkillSetting("xp-on-cast", false);
    /**
     * An ItemStack's name, as defined by VaultAPI's Items, to consume on skill cast
     */
    public static final SkillSetting REAGENT = new SkillSetting("reagent");
    /**
     * How many items are deducted. This will always be converted to a whole
     * number before use.
     */
    public static final SkillSetting REAGENT_QUANTITY = new SkillSetting("reagent-amount", true);
    /**
     * If true, the skill cannot be used while in combat.
     */
    public static final SkillSetting NO_COMBAT_USE = new SkillSetting("no-combat-use");
    /**
     * Default boolean settings provided by SkillSetting
     */
    public static final Set<SkillSetting> BOOLEAN_SETTINGS = ImmutableSet.of(NO_COMBAT_USE);
    /**
     * How many skill points it costs to learn the skill once the level
     * requirement is met. (TODO)
     */
    public static final SkillSetting SKILL_POINT_COST = new SkillSetting("skill-point-cost");
    /**
     * Default automatic settings applied for any skill
     */
    public static final Set<SkillSetting> AUTOMATIC_SETTINGS = ImmutableSet.of(
            LEVEL, COOLDOWN, DELAY, EXP, HEALTH_COST, MANA_COST, STAMINA_COST,
            REAGENT, REAGENT_QUANTITY, NO_COMBAT_USE, SKILL_POINT_COST);
    public static final SkillSetting AMOUNT = new SkillSetting("amount");
    /**
     * How likely the skill is to succeed, on a scale of 0-1.
     */
    public static final SkillSetting CHANCE = new SkillSetting("chance", true);
    /**
     * Duration in ticks that an Expirable effect will last on an Insentient being.
     */
    public static final SkillSetting DURATION = new SkillSetting("duration", true);
    /**
     * Damage to deal on skill use
     */
    public static final SkillSetting DAMAGE = new SkillSetting("damage", true);
    /**
     * Damage to deal per tick for a skill based effect
     */
    public static final SkillSetting DAMAGE_PER_TICK = new SkillSetting("damage-per-tick", true);
    /**
     * Amount of health to heal based on skill use
     */
    public static final SkillSetting HEALING = new SkillSetting("healing", true);
    /**
     * Amount of health to heal per tick for a skill based effect
     */
    public static final SkillSetting HEALTH_PER_TICK = new SkillSetting("healing-per-tick", true);
    /**
     * Maximum distance that the skill will search.
     */
    public static final SkillSetting MAX_DISTANCE = new SkillSetting("max-distance", true);
    /**
     * Amount of ticks between a {@link com.afterkraft.kraftrpg.api.effects.Periodic} effect's ticks.
     */
    public static final SkillSetting RECURRENCE_PERIOD = new SkillSetting("period", true);
    /**
     * The distance, centered on the player, that the skill affects.
     */
    public static final SkillSetting RADIUS = new SkillSetting("radius", true);
    /**
     * A text to broadcast when an effect is applied to an Insentient being
     */
    public static final SkillSetting APPLY_TEXT = new SkillSetting("apply-text");
    /**
     * A text to broadcast when an Insentient being dies while an effect was active.
     */
    public static final SkillSetting DEATH_TEXT = new SkillSetting("death-text");
    /**
     * A text to broadcast when a SkillCaster's cast of a skill is delayed and warming up.
     */
    public static final SkillSetting DELAY_TEXT = new SkillSetting("delay-text");
    /**
     * A text to broadcast when an effect expires on an Insentient being.
     */
    public static final SkillSetting EXPIRE_TEXT = new SkillSetting("expire-text");
    /**
     * A text to broadcast when a delayed skill cast is interrupted.
     */
    public static final SkillSetting INTERRUPT_TEXT = new SkillSetting("interrupt-text");
    /**
     * A text to broadcast when an effect is removed (not expired) from an Insentient being.
     */
    public static final SkillSetting UNAPPLY_TEXT = new SkillSetting("unapply-text");
    /**
     * A text to broadcast when a skill is casted
     */
    public static final SkillSetting USE_TEXT = new SkillSetting("use-text");
    /**
     * Default String settings provided by SkillSetting
     */
    public static final Set<SkillSetting> STRING_SETTINGS = ImmutableSet.of(
            APPLY_TEXT, DEATH_TEXT, DELAY_TEXT, EXPIRE_TEXT, INTERRUPT_TEXT,
            UNAPPLY_TEXT, USE_TEXT);
    /**
     * Custom setting
     */
    public static final SkillSetting CUSTOM = new SkillSetting(null);
    /**
     * Custom per caster setting
     */
    public static final SkillSetting CUSTOM_PER_CASTER = new SkillSetting(null);
    private final String node;
    private final boolean scaled;

    protected SkillSetting(String node) {
        this(node, false);
    }

    protected SkillSetting(String node, boolean scaled) {
        this.node = node;
        this.scaled = scaled;
    }

    /**
     * Gets the prescribed string node for this setting.
     *
     * @return The string node for this setting
     */
    public String node() {
        return this.node;
    }

    /**
     * Returns true if this setting is available in a 'per-level' basis.
     *
     * @return True if this setting is available on a 'per-level' basis.
     */
    public boolean isLevelScaled() {
        return this.scaled;
    }

    /**
     * Gets the 'per-level' string node for this setting if applicable.
     *
     * @return The 'per-level' string node for this setting
     */
    public String scalingNode() {
        if (!this.scaled) return null;

        return this.node + "-per-level";
    }

    @Override
    public String toString() {
        return this.node;
    }
}

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
package com.afterkraft.kraftrpg.api.skills;

import java.util.Set;

import org.spongepowered.api.service.persistence.data.DataQuery;

import com.google.common.base.Optional;
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
     * Depends on another skill being granted. Prevents use of the skill when the depended skill is
     * not activated. Defaults to none
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
     * How many items are deducted. This will always be converted to a whole number before use.
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
     * How many skill points it costs to learn the skill once the level requirement is met. (TODO)
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
     * Amount of ticks between a {@link com.afterkraft.kraftrpg.api.effects.Periodic} effect's
     * ticks.
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
    public static final SkillSetting CUSTOM = new SkillSetting("custom");
    /**
     * Custom per caster setting
     */
    public static final SkillSetting CUSTOM_PER_CASTER =
            new SkillSetting("custom");
    private final DataQuery node;
    private final boolean scaled;

    /**
     * Creates a new {@link SkillSetting} with the given string as a node.
     *
     * @param node The node of the setting
     */
    protected SkillSetting(String node) {
        this(node, false);
    }

    /**
     * Creates a new {@link SkillSetting} that has a scaling node.
     *
     * @param node The node of the setting
     * @param scaled Whether there is a scaling node or not
     */
    protected SkillSetting(String node, boolean scaled) {
        this.node = new DataQuery(node);
        this.scaled = scaled;
    }

    /**
     * Gets the prescribed string node for this setting.
     *
     * @return The string node for this setting
     */
    public String nodeAsString() {
        return this.node.asString('.');
    }

    /**
     * Gets the prescribed {@link DataQuery} node for this setting
     *
     * @return The prescribed data query
     */
    public DataQuery node() {
        return this.node;
    }

    /**
     * Gets the 'per-level' string node for this setting if applicable.
     * <p>If the node is not a scaling node, the this will return {@link
     * Optional#absent()}</p>
     *
     * @return The 'per-level' string node for this setting
     */
    public Optional<DataQuery> scalingNode() {
        if (!this.scaled) {
            return Optional.absent();
        }
        return Optional.of(new DataQuery(this.nodeAsString() + "-per-level"));
    }

    @Override
    public String toString() {
        return this.nodeAsString();
    }
}

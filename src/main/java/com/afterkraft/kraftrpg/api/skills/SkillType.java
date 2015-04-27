/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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

/**
 * Various types of skills. These can be joined together to further customize a skill and utilize
 * external modules to further interact with these skill types.
 */
public enum SkillType {
    /**
     * Grants a non-health benefit to allies.  Causes combat entry.
     */
    BUFFING,
    /**
     * A Damaging skill tends to deal damage.  Causes combat entry.
     */
    DAMAGING,
    /**
     * Causes a non-health impediment to enemies.  Causes combat entry.
     */
    DEBUFFING,
    /**
     * Cancels a hurt enemy's Stalled skill.  Causes combat entry.
     */
    ENEMY_INTERRUPTING,
    /**
     * If an Exclusive skill is stalled, no other skills can be used during its delay.
     */
    EXCLUSIVE,
    /**
     * A Healing skill tends to increase your health.  Causes combat entry if not used on yourself.
     */
    HEALING,
    /**
     * If a Self-Interrupting skill is used while you have a Stalled skill active, the Stalled skill
     * will be cancelled, unless
     */
    SELF_INTERRUPTING,
    /**
     * An unbindable skill cannot be bound to an item.
     */
    UNBINDABLE,
    /**
     * During the delay time of a Stalled Uninterruptible skill, no other skills can be used and the
     * skill cannot be cancelled, except by death.
     */
    UNINTERRUPTIBLE,
    /**
     * An Unsilencable skill is not cancelled if the caster is Silenced.
     */
    UNSILENCABLE,

    // === Types without code effects ===
    ABILITY_PROPERTY_AIR,
    ABILITY_PROPERTY_BLEED,
    ABILITY_PROPERTY_DARK,
    ABILITY_PROPERTY_DISEASE,
    ABILITY_PROPERTY_EARTH,
    ABILITY_PROPERTY_FIRE,
    ABILITY_PROPERTY_ICE,
    ABILITY_PROPERTY_ILLUSION,
    ABILITY_PROPERTY_LIGHT,
    ABILITY_PROPERTY_LIGHTNING,
    ABILITY_PROPERTY_MAGICAL,
    ABILITY_PROPERTY_PHYSICAL,
    ABILITY_PROPERTY_POISON,
    ABILITY_PROPERTY_PROJECTILE,
    ABILITY_PROPERTY_SONG,
    ABILITY_PROPERTY_WATER,
    ABILITY_PROPERTY_VOID,
    AGGRESSIVE,
    AREA_OF_EFFECT,
    ARMOR_PIERCING,
    BLINDING,
    BLOCK_CREATING,
    BLOCK_REMOVING,
    BLOCK_MODIFYING,
    /**
     * A Desummoning skill tends to remove entities or blocks created by a Summoning skill.
     */
    DESUMMONING,
    DISABLE_COUNTERING,
    DISABLING,
    DISKILLING,
    DURABILITY_INCREASING,
    DURABILITY_REDUCING,
    FORCE,
    /**
     * A Form Altering skill tends to change the apparent shape of the caster.
     */
    FORM_ALTERING,
    HEALTH_FREEZING,
    ITEM_CREATION,
    ITEM_DESTRUCTION,
    ITEM_MODIFYING,
    MANA_DECREASING,
    MANA_FREEZING,
    MANA_INCREASING,
    MAX_HEALTH_DECREASING,
    MAX_HEALTH_INCREASING,
    MAX_MANA_DECREASING,
    MAX_MANA_INCREASING,
    MAX_STAMINA_INCREASING,
    MAX_STAMINA_DECREASING,
    MOVEMENT_INCREASING,
    MOVEMENT_INCREASE_COUNTERING,
    MOVEMENT_PREVENTING,
    MOVEMENT_PREVENTION_COUNTERING,
    MOVEMENT_SLOWING,
    MOVEMENT_SLOWING_COUNTERING,
    MULTI_GRESSIVE,
    /**
     * A No-Self-Targetting skill cannot be used on yourself.
     */
    NO_SELF_TARGETTING,
    RESURRECTING,
    /**
     * A Silencing skill tends to Silence the target.
     */
    SILENCING,
    /**
     * Any use of a non-Stealthy skill will reveal you.
     */
    STEALTHY,
    STAMINA_DECREASING,
    STAMINA_INCREASING,
    STAMINA_FREEZING,
    STUNNING,
    /**
     * A Summoning skill tends to create entities or blocks.
     */
    SUMMONING,
    /**
     * A Teleporting skill tends to move entities.
     */
    TELEPORTING,
    VELOCITY_DECREASING,
    VELOCITY_INCREASING
}

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
package com.afterkraft.kraftrpg.api.effects;


import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spongepowered.api.service.persistence.data.DataQuery;
import org.spongepowered.api.service.persistence.data.DataView;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillType;

/**
 * A basic effect and affect enum. Various effects require different settings, and sometimes, some
 * videos or tv shows are annoying as shit anyway.
 */
public enum EffectType {

    /**
     * An arid effect usually causing some sort of flight.
     */
    AIR,
    /**
     * A standard area of effect type.
     */
    AREA_OF_EFFECT,
    /**
     * Increases non-specific attributes.
     */
    ATTRIBUTE_INCREASING,
    /**
     * Decreases non-specific attributes.
     */
    ATTRIBUTE_DECREASING,
    /**
     * Benefits the Insentient being the effect is applied to.
     */
    BENEFICIAL,
    /**
     * Causes the Insentient being to bleed and damage over time.
     */
    BLEED,
    /**
     * Causes the Insentient being to have a blinded potion effect. Usually makes physical and
     * magical attacks to miss their targets.
     */
    BLIND,
    /**
     * Creates blocks in the world around the Insentient being
     */
    BLOCK_CREATION,
    /**
     * Modifies blocks in the world around the Insentient being.
     */
    BLOCK_MODIFYING,
    /**
     * Removes blocks in the world around the Insentient being.
     */
    BLOCK_REMOVING,
    /**
     * Causes the Insentient being to have a nauseous potion effect applied.
     */
    CONFUSION,
    /**
     * Damages the affected Insentient being. This can also be damaging over time.
     */
    DAMAGING,
    /**
     * A mysterious dark elemental effect.
     */
    DARK,
    /**
     * Disables the Insentient being from controlling their movements. Can be used to apply a {@link
     * EffectType#STUN} or some sporadic movement. SkillCasters are unable to cast skills while
     * disabled.
     */
    DISABLE,
    /**
     * Prevents an Insentient being from equipping anything in hand.
     */
    DISARM,
    /**
     * Usually an effect that renders the Insentient being slowed, fatigued, nauseated and
     * confused.
     */
    DISEASE,
    /**
     * Marks the effect as being able to be removed from an Insentient being without harming
     * effects. Usually tied together with {@link #BENEFICIAL}
     */
    DISPELLABLE,
    /**
     * Causes an increase in durability of either all worn items, or a specific piece or
     * armor/weapon.
     */
    DURABILITY_INCREASING,
    /**
     * Reduces the durability of all worn items, or a specific piece of armor or weapon.
     */
    DURABILITY_REDUCING,
    /**
     * An earth based effect that usually has to do with {@link #BLOCK_MODIFYING}.
     */
    EARTH,
    /**
     * A fire based effect. Usually used to apply a burning effect on the Insentient being.
     */
    FIRE,
    /**
     * A forceful effect that causes an Insentient being to move dangerously and far.
     */
    FORCE,
    /**
     * An effect that is harmful to the applier, usually the applier will be saved from harm due to
     * the effect.
     */
    HARMFUL,
    /**
     * Heals a certain amount of health to the Insentient being.
     */
    HEALING,
    /**
     * Amplifies the power of healing based effects.
     */
    HEALING_AMPLIFIER,
    /**
     * Reduces the healing power applied to the affected Insentient being.
     */
    HEALING_REDUCTION,
    /**
     * The Insentient being is usually being drained of all stamina.
     */
    HUNGER,
    /**
     * Usually applies a slowing effect in relation to the ice element.
     */
    ICE,
    /**
     * Imbues a weapon/armor/item with a mystical enchantment.
     */
    IMBUE,
    /**
     * Renders the Insentient being as invisible to enemy monsters. It will not prevent Players from
     * seeing the Insentient being.
     */
    INVIS,
    /**
     * Full potion effect based Invisibility effect. Should render the being invisible and prevent
     * targeting by enemy monsters.
     */
    INVISIBILITY,
    /**
     * Prevents any damage being dealt to the Insentient being.
     */
    INVULNERABILITY,
    /**
     * Creates an item that can be dropped or granted to the Insentient being.
     */
    ITEM_CREATION,
    /**
     * Destroys an item, either from the inventory of the Insentient being, or those around it.
     */
    ITEM_DESTRUCTION,
    /**
     * Modifies an item in the inventory of the Insentient being. Usually used for customizing
     * enchantments, or preventing item durability.
     */
    ITEM_MODIFYICATION,
    /**
     * Grants a simple jump boost to the Insentient being.
     */
    JUMP_BOOST,
    /**
     * Mystical holy light elemental effect.
     */
    LIGHT,
    /**
     * A standard lightning effect, usually causes a Thunder particle or sound to be forced upon the
     * Insentient being.
     */
    LIGHTNING,
    /**
     * A mystical effect that ignores armor for attacking, defending, and sometimes ignores
     * physics!
     */
    MAGIC,
    /**
     * Decreases the mana available for the Insentient being.
     */
    MANA_DECREASING,
    /**
     * Restricts any mana changes on the Insentient being.
     */
    MANA_FREEZING,
    /**
     * Increases the mana available for the Insentient being.
     */
    MANA_INCREASING,
    /**
     * Decreases the mana regeneration for the Insentient being. Mana regeneration can never be
     * below zero.
     */
    MANA_REGEN_DECREASING,
    /**
     * Restricts any changes in the mana regeneration values for the Insentient being.
     */
    MANA_REGEN_FREEZING,
    /**
     * Increases the mana regeneration for the Insentient being.
     */
    MANA_REGEN_INCREASING,
    /**
     * Decreases the overall maximum health for the Insentient being.
     */
    MAX_HEALTH_DECREASING,
    /**
     * Increases the overall maximum health for the Insentient being.
     */
    MAX_HEALTH_INCREASING,
    /**
     * Decreases the maximum mana available for the Insentient being.
     */
    MAX_MANA_DECREASING,
    /**
     * Increases the maximum mana available for the Insentient being.
     */
    MAX_MANA_INCREASING,
    /**
     * Decreases the maximum available stamina for the Insentient being. The maximum stamina can
     * never be below zero.
     */
    MAX_STAMINA_DECREASING,
    /**
     * Increases the maximum available stamina for the Insentient being.
     */
    MAX_STAMINA_INCREASING,
    /**
     * Causes fatigue in the Insentient being's actions. Attacks are slower.
     */
    MINING_SLOWNESS,
    /**
     * Causes the nausea potion effect to show for the Insentient being.
     */
    NAUSEA,
    /**
     * Grants the Insentient being with full night vision. This can allow for players to see in
     * caves.
     */
    NIGHT_VISION,
    /**
     * A physically
     */
    PHYSICAL,
    /**
     * Grants the Insentient being with a generic poison potion effect
     */
    POISON,
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_AIR}.
     */
    RESIST_AIR(SkillType.ABILITY_PROPERTY_AIR),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_BLEED}.
     */
    RESIST_BLEED(SkillType.ABILITY_PROPERTY_BLEED),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_AIR}.
     */
    RESIST_DARK(SkillType.ABILITY_PROPERTY_DARK),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_DISEASE}.
     */
    RESIST_DISEASE(SkillType.ABILITY_PROPERTY_DISEASE),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_EARTH}.
     */
    RESIST_EARTH(SkillType.ABILITY_PROPERTY_EARTH),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_FIRE}.
     */
    RESIST_FIRE(SkillType.ABILITY_PROPERTY_FIRE),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_ICE}.
     */
    RESIST_ICE(SkillType.ABILITY_PROPERTY_ICE),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_ILLUSION}.
     */
    RESIST_ILLUSION(SkillType.ABILITY_PROPERTY_ILLUSION),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_LIGHT}.
     */
    RESIST_LIGHT(SkillType.ABILITY_PROPERTY_LIGHT),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_LIGHTNING}.
     */
    RESIST_LIGHTNING(SkillType.ABILITY_PROPERTY_LIGHTNING),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_MAGICAL}.
     */
    RESIST_MAGICAL(SkillType.ABILITY_PROPERTY_MAGICAL),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_POISON}.
     */
    RESIST_POISON(SkillType.ABILITY_PROPERTY_POISON),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_PHYSICAL}.
     */
    RESIST_PHYSICAL(SkillType.ABILITY_PROPERTY_PHYSICAL),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_PROJECTILE}.
     */
    RESIST_PROJECTILE(SkillType.ABILITY_PROPERTY_PROJECTILE),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_SONG}.
     */
    RESIST_SONG(SkillType.ABILITY_PROPERTY_SONG),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_WATER}.
     */
    RESIST_WATER(SkillType.ABILITY_PROPERTY_WATER),
    /**
     * Grants resistance to any Skill of the {@link SkillType#ABILITY_PROPERTY_VOID}.
     */
    RESIST_VOID(SkillType.ABILITY_PROPERTY_VOID),
    /**
     * Prevents the Insentient being from moving their legs or fly in place. Itis still possible for
     * the being to attack.
     */
    ROOT,
    /**
     * Grants the Insentient being the ability to withstand long falls to the ground without major
     * injuries.
     */
    SAFEFALL,
    /**
     * Renders the Insentient being silent. SkillCasters who are silenced are unable to cast
     * skills.
     */
    SILENCE,
    /**
     * Renders all actions, active effects, and skill casting to be silent (without broadcast).
     * Usually goes in hand with {@link #INVISIBILITY}
     */
    SILENT_ACTIONS,
    /**
     * Slows the movement speed of the Insentient being.
     */
    SLOW,
    /**
     * Causes the Insentient being to crouch. Sometimes able to move normally, and for Players, name
     * tags can be rendered invisible.
     */
    SNEAK,
    /**
     * A general movement speed boost is applied to the Insentient being.
     */
    SPEED,
    /**
     * Reduces the regeneration of stamina for the Insentient being.
     */
    STAMINA_REGEN_DECREASING,
    /**
     * Restricts the regeneration of stamina from changing.
     */
    STAMINA_REGEN_FREEZING,
    /**
     * Increases the regeneration rate of stamina for the Insentient being.
     */
    STAMINA_REGEN_INCREASING,
    /**
     * Decreases the currently available stamina.
     */
    STAMINA_DECREASING,
    /**
     * Restricts the current stamina from changing.
     */
    STAMINA_FREEZING,
    /**
     * Increases the currently available stamina for the Insentient being.
     */
    STAMINA_INCREASING,
    /**
     * Hidden from sight, sneaking and possibly vanished.
     */
    STEALTHY,
    /**
     * Prevents any movement whatsoever for the Insentient being.
     */
    STUN,
    /**
     * The insentient being is not sentient, but rather a summon for another Insentient being.
     */
    SUMMON,
    /**
     * Renders all durability affected tools, weapons, and armor from breaking.
     */
    UNBREAKABLE,
    /**
     * Causes all monsters currently targetting the Insentient being to targeting others. Monsters
     * are unable to target this being.
     */
    UNTARGETABLE,
    /**
     * Decreases the actual movement speed.
     */
    VELOCITY_DECREASING,
    /**
     * Increases the actual movement speed.
     */
    VELOCITY_INCREASING,
    /**
     * Decreases the normal walking speed of the Insentient being.
     */
    WALK_SPEED_DECREASING,
    /**
     * Increases the normal walking speed of the Insentient being.
     */
    WALK_SPEED_INCREASING,
    /**
     * Allows extended breathing under water.
     */
    WATER_BREATHING,
    /**
     * A simple element, but it's still worth the try. Elements are the building blocks of today's
     * society.
     */
    WATER,
    /**
     * Renders the Insentient being seen with an injury and can not justify a reason for th meeting
     * to break apart.
     */
    WEAKNESS,
    /**
     * Applies a vanilla withering damage over time effect to the Insentient being.
     */
    WITHER;

    @Nullable
    private final SkillType resistance;
    private final Set<EffectType> effectResists = new LinkedHashSet<>();

    EffectType() {
        this(null);
    }

    EffectType(@Nullable SkillType resistance) {
        this.resistance = resistance;
    }

    public static Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        for (EffectType type : EffectType.values()) {
            map.put(type.name(), type.effectResists);
        }
        return map;
    }

    public static void deserialize(DataView section) {
        for (DataQuery key : section.getKeys(false)) {
            EffectType type = EffectType.valueOf(key.asString('.'));
            if (type != null) {
                List<String> types = section.getStringList(key).get();
                for (String resistTypeString : types) {
                    EffectType resistType = EffectType.valueOf(resistTypeString);
                    if (resistType != null) {
                        type.addResistance(resistType);
                    }
                }
            }
        }
    }

    private EffectType addResistance(EffectType type) {
        this.effectResists.add(type);
        return this;
    }

    /**
     * Checks if this EffectType restricted the {@link SkillType} active to the {@link ISkill} and
     * {@link Insentient} being.
     *
     * @param being That is being checked
     * @param skill That is being checked.
     *
     * @return true if the skill requested th newest ticket.
     * @throws IllegalArgumentException If the being is null
     * @throws IllegalArgumentException If the skill is null
     */
    public boolean isSkillResisted(Insentient being, ISkill skill) {
        return (this.resistance != null && being.hasEffectType(this)
                && skill.isType(this.resistance));
    }

    /**
     * Returns true if the queried IEffect is of an EffectType that is resisted by this EffectType.
     * Since EffectTypes are configurable, it is impossible to know the state of the resistances
     * without manually checking each EffectType.
     *
     * @param being  The Insentient being to check if it has this effect type
     * @param effect The IEffect in question whether the queried being should resist the effect
     *
     * @return True if this EffectType resists the queried effect
     * @throws IllegalArgumentException If the being is null
     * @throws IllegalArgumentException If the effect is null
     */
    public boolean isEffectResisted(Insentient being, IEffect effect) {
        if (!being.hasEffectType(this)) {
            return false;
        }
        for (EffectType type : this.effectResists) {
            if (effect.isType(type)) {
                return true;
            }
        }
        return false;
    }

}

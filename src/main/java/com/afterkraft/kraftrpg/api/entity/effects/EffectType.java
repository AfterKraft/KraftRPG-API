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
package com.afterkraft.kraftrpg.api.entity.effects;


import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillType;

public enum EffectType {

    AIR,
    AREA_OF_EFFECT,
    ATTRIBUTE_INCREASING,
    ATTRIBUTE_DECREASING,
    BENEFICIAL,
    BLEED,
    BLIND,
    BLOCK_CREATION,
    BLOCK_MODIFYING,
    BLOCK_REMOVING,
    CONFUSION,
    DAMAGING,
    DARK,
    DISABLE,
    DISARM,
    DISEASE,
    DISPELLABLE,
    DURABILITY_INCREASING,
    DURABILITY_REDUCING,
    EARTH,
    FIRE,
    FORCE,
    FORM,
    HARMFUL,
    HEALING,
    HEALING_AMPLIFIER,
    HEALING_REDUCTION,
    HUNGER,
    ICE,
    IMBUE,
    INVIS, // Vanish based invis
    INVISIBILITY, // Potion effect based invis
    INVULNERABILITY,
    ITEM_CREATION,
    ITEM_DESTRUCTION,
    ITEM_MODIFYICATION,
    JUMP_BOOST,
    LIGHT,
    LIGHTNING,
    MAGIC,
    MANA_DECREASING,
    MANA_FREEZING,
    MANA_INCREASING,
    MANA_REGEN_DECREASING,
    MANA_REGEN_FREEZING,
    MANA_REGEN_INCREASING,
    MAX_HEALTH_DECREASING,
    MAX_HEALTH_INCREASING,
    MAX_MANA_DECREASING,
    MAX_MANA_INCREASING,
    MAX_STAMINA_DECREASING,
    MAX_STAMINA_INCREASING,
    MINING_SLOWNESS,
    NAUSEA,
    NIGHT_VISION,
    PHYSICAL,
    POISON,
    RESIST_AIR(SkillType.ABILITY_PROPERTY_AIR),
    RESIST_BLEED(SkillType.ABILITY_PROPERTY_BLEED),
    RESIST_DARK(SkillType.ABILITY_PROPERTY_DARK),
    RESIST_DISEASE(SkillType.ABILITY_PROPERTY_DISEASE),
    RESIST_EARTH(SkillType.ABILITY_PROPERTY_EARTH),
    RESIST_FIRE(SkillType.ABILITY_PROPERTY_FIRE),
    RESIST_ICE(SkillType.ABILITY_PROPERTY_ICE),
    RESIST_ILLUSION(SkillType.ABILITY_PROPERTY_ILLUSION),
    RESIST_LIGHT(SkillType.ABILITY_PROPERTY_LIGHT),
    RESIST_LIGHTNING(SkillType.ABILITY_PROPERTY_LIGHTNING),
    RESIST_MAGICAL(SkillType.ABILITY_PROPERTY_MAGICAL),
    RESIST_POISON(SkillType.ABILITY_PROPERTY_POISON),
    RESIST_PHYSICAL(SkillType.ABILITY_PROPERTY_PHYSICAL),
    RESIST_PROJECTILE(SkillType.ABILITY_PROPERTY_PROJECTILE),
    RESIST_SONG(SkillType.ABILITY_PROPERTY_SONG),
    RESIST_WATER(SkillType.ABILITY_PROPERTY_WATER),
    RESIST_VOID(SkillType.ABILITY_PROPERTY_VOID),
    ROOT,
    SAFEFALL,
    SILENCE,
    SILENT_ACTIONS,
    SLOW,
    SNEAK,
    SPEED,
    STAMINA_REGEN_DECREASING,
    STAMINA_REGEN_FREEZING,
    STAMINA_REGEN_INCREASING,
    STAMINA_DECREASING,
    STAMINA_FREEZING,
    STAMINA_INCREASING,
    STEALTHY,
    STUN,
    SUMMON,
    UNBREAKABLE,
    UNTARGETABLE,
    VELOCITY_DECREASING,
    VELOCITY_INCREASING,
    WALK_SPEED_DECREASING,
    WALK_SPEED_INCREASING,
    WATER_BREATHING,
    WATER,
    WEAKNESS,
    WITHER;

    private final SkillType resistance;

    EffectType() {
        this(null);
    }

    EffectType(SkillType resistance) {
        this.resistance = resistance;
    }

    public boolean isSkillResisted(Insentient being, ISkill skill) {
        return (this.resistance != null && being.hasEffectType(this) && skill.isType(this.resistance));
    }
}

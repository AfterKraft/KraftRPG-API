/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api;


import java.lang.reflect.Type;
import java.util.Set;

import static org.spongepowered.api.data.DataQuery.of;

import com.afterkraft.kraftrpg.api.role.ExperienceType;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.data.value.mutable.SetValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.api.entity.party.Party;
import com.afterkraft.kraftrpg.api.role.Role;
import com.afterkraft.kraftrpg.api.util.FixedPoint;
import org.spongepowered.api.item.ItemType;

@SuppressWarnings("unchecked")
public final class RpgKeys {


    public static final Key<Value<Party>> PARTY;
    public static final Key<MutableBoundedValue<Long>> SUMMON_DURATION;
    public static final Key<SetValue<Effect>> RPG_EFFECTS;
    public static final Key<Value<Double>> BASE_DAMAGE;
    public static final Key<Value<Double>> DAMAGE_MODIFIER;
    public static final Key<Value<Integer>> MANA;
    public static final Key<Value<Integer>> MAX_MANA;
    public static final Key<Value<Role>> PRIMARY_ROLE;
    public static final Key<Value<Role>> SECONDARY_ROLE;
    public static final Key<ListValue<Role>> ADDITIONAL_ROLES;
    public static final Key<Value<FixedPoint>> REWARDING_EXPERIENCE;

    public static final Key<Value<Double>> BASE_HEALTH;
    public static final Key<Value<Double>> HEALTH_PER_LEVEL; // Basically as above
    public static final Key<SetValue<ExperienceType>> PERMITTED_EXPERIENCE; // Basically a list of string values of the enum type
    public static final Key<MapValue<ItemType, Double>> BASE_ITEM_DAMAGE; // Basically, you have to serialize the item type id as a string, then the double as a value
    public static final Key<MapValue<ItemType, Double>> ITEM_DAMAGE_LEVELING; // Basically, you have to serialize the item type id as a string, and then the double as value
    public static final Key<SetValue<ItemType>> ACCEPTED_ARMOR; // Basically a list of string id's being serialized
    public static final Key<SetValue<ItemType>> ACCEPTED_WEAPONS; // Basically a List of string id's being serialized
    public static final Key<Value<Integer>> BASE_MANA; // Basically like the values from above
    public static final Key<Value<Integer>> MANA_PER_LEVEL;
    public static final Key<SetValue<Skill>> PROHIBITED_SKILLS; // Basically, string list
    public static final Key<MapValue<Skill, DataView>> SKILL_CONFIGURATIONS; // Just like for item type mapping, use the skill id as a string, then the DataView is just another view that is contained in the map.




    public static final TypeToken<SetValue<Effect>> SET_VALUE_TYPE_TOKEN = new TypeToken<SetValue<Effect>>() { };
    public static final TypeToken<Value<Double>> BASE_DAMAGE_TYPE_TOKEN = new TypeToken<Value<Double>>() {};
    public static final TypeToken<Value<Double>> DAMAGE_MODIFIER_TOKEN = new TypeToken<Value<Double>>() {};
    public static final TypeToken<Value<Integer>> MANA_TOKEN = new TypeToken<Value<Integer>>() {};
    public static final TypeToken<Value<Integer>> MAX_MANA_TOKEN = new TypeToken<Value<Integer>>() {};
    public static final TypeToken<Value<Party>> PARTY_TOKEN = new TypeToken<Value<Party>>() {};
    public static final TypeToken<Value<Role>> PRIMARY_ROLE_TOKEN = new TypeToken<Value<Role>>() {};
    public static final TypeToken<Value<Role>> SECONDARY_ROLE_TOKEN = new TypeToken<Value<Role>>() {};
    public static final TypeToken<ListValue<Role>> ADDITIONAL_ROLE_TOKEN = new TypeToken<ListValue<Role>>() {};
    public static final TypeToken<MutableBoundedValue<Long>> SUMMON_DURATION_TOKEN = new TypeToken<MutableBoundedValue<Long>>() {};
    public static final TypeToken<Value<FixedPoint>> REWARDING_EXPERIENCE_TOKEN = new TypeToken<Value<FixedPoint>>() {};
    public static final TypeToken<Value<Double>> BASE_HEALTH_TOKEN = new TypeToken<Value<Double>>() {};
    public static final TypeToken<Value<Double>> HEALTH_PER_LEVEL__TOKEN = new TypeToken<Value<Double>>() {};
    public static final TypeToken<SetValue<ExperienceType>> PERMITTED_EXPERIENCE_TOKEN = new TypeToken<SetValue<ExperienceType>>() {};
    public static final TypeToken<MapValue<ItemType, Double>> BASE_ITEM_DAMAGE_TOKEN = new TypeToken<MapValue<ItemType, Double>>() {};
    public static final TypeToken<MapValue<ItemType, Double>> ITEM_DAMAGE_LEVELING_TOKEN = new TypeToken<MapValue<ItemType, Double>>() {};
    public static final TypeToken<SetValue<ItemType>> ACCEPTED_ARMOR_TOKEN = new TypeToken<SetValue<ItemType>>() {};
    public static final TypeToken<SetValue<ItemType>> ACCEPTED_WEAPONS_TOKEN = new TypeToken<SetValue<ItemType>>() {};
    public static final TypeToken<Value<Integer>> BASE_MANA_TOKEN = new TypeToken<Value<Integer>>() {};
    public static final TypeToken<Value<Integer>> MANA_PER_LEVEL_TOKEN = new TypeToken<Value<Integer>>() {};
    public static final TypeToken<SetValue<Skill>> PROHIBITED_SKILLS_TOKEN = new TypeToken<SetValue<Skill>>() {};
    public static final TypeToken<MapValue<Skill, DataView>> SKILL_CONFIGURATION_TOKEN = new TypeToken<MapValue<Skill, DataView>>() {};



    private RpgKeys() {
    }

    static {

        RPG_EFFECTS = Key.builder()
                .type(SET_VALUE_TYPE_TOKEN)
                .id("kraftrpg:effects")
                .name("KraftRPG Effects")
                .query(of("KraftRPG", "Effects"))
                .build();

        BASE_DAMAGE = Key.builder()
                .type(BASE_DAMAGE_TYPE_TOKEN)
                .id("kraftrpg:base_damage")
                .name("KraftRPG Base Damage")
                .query(of("KraftRPG", "base_damage"))
                .build();


        DAMAGE_MODIFIER = Key.builder()
                .type(DAMAGE_MODIFIER_TOKEN)
                .id("kraftrpg:damage_modifier")
                .name("KraftRPG Damage Modifier")
                .query(of("KraftRPG", "damage_modifier"))
                .build();

        MANA = Key.builder()
                .type(MANA_TOKEN)
                .id("kraftrpg:mana")
                .name("KraftRPG Mana")
                .query(of("KraftRPG", "mana"))
                .build();

        MAX_MANA = Key.builder()
                .type(MAX_MANA_TOKEN)
                .id("kraftrpg:max_mana")
                .name("KraftRPG Max Mana")
                .query(of("KraftRPG", "max_mana"))
                .build();

        PARTY = Key.builder()
                .type(PARTY_TOKEN)
                .id("kraftrpg:party")
                .name("KraftRPG Party")
                .query(of("KraftRPG", "party"))
                .build();

        PRIMARY_ROLE = Key.builder()
                .type(PRIMARY_ROLE_TOKEN)
                .id("kraftrpg:primary_role")
                .name("KraftRPG Primary Role")
                .query(of("KraftRPG", "primary_role"))
                .build();

        SECONDARY_ROLE = Key.builder()
                .type(SECONDARY_ROLE_TOKEN)
                .id("kraftrpg:secondary_role")
                .name("KraftRPG Secondary Role")
                .query(of("KraftRPG", "secondary_role"))
                .build();

        ADDITIONAL_ROLES = Key.builder()
                .type(ADDITIONAL_ROLE_TOKEN)
                .id("kraftrpg:additional_role")
                .name("KraftRPG Additional_Role")
                .query(of("KraftRPG", "additional_role"))
                .build();

        SUMMON_DURATION = Key.builder()
                .type(SUMMON_DURATION_TOKEN)
                .id("kraftrpg:summon_duration")
                .name("KraftRPG Summon Duration")
                .query(of("KraftRPG", "summon_duration"))
                .build();

        REWARDING_EXPERIENCE = Key.builder()
                .type(REWARDING_EXPERIENCE_TOKEN)
                .id("kraftrpg:rewarding_experience")
                .name("KraftRPG Rewarding Experience")
                .query(of("KraftRPG", "rewarding_experience"))
                .build();

        BASE_HEALTH = Key.builder()
                .type(BASE_HEALTH_TOKEN)
                .id("kraftrpg:base_health")
                .name("KraftRPG Base Health")
                .query(of("KraftRPG", "base_health"))
                .build();

        HEALTH_PER_LEVEL = Key.builder()
                .type(HEALTH_PER_LEVEL__TOKEN)
                .id("kraftrpg:health_per_level")
                .name("KraftRPG Health Per Level")
                .query(of("KraftRPG", "Health Per Level"))
                .build();

        PERMITTED_EXPERIENCE = Key.builder()
                .type(PERMITTED_EXPERIENCE_TOKEN)
                .id("kraftrpg:permitted_experience")
                .name("KraftRPG Permitted Experience")
                .query(of("KraftRPG", "Permitted Experience"))
                .build();

        BASE_ITEM_DAMAGE = Key.builder()
                .type(BASE_ITEM_DAMAGE_TOKEN)
                .id("kraftrpg:base_damage")
                .name("KraftRPG Base Damage")
                .query(of("KraftRPG", "Base Item Damage"))
                .build();

        ITEM_DAMAGE_LEVELING = Key.builder()
                .type(ITEM_DAMAGE_LEVELING_TOKEN)
                .id("kraftrpg:item_damage_leveling")
                .name("KraftRPG Item Damage Leveling")
                .query(of("KraftRPG", "Item Damage Leveling"))
                .build();

        ACCEPTED_ARMOR = Key.builder()
                .type(ACCEPTED_ARMOR_TOKEN)
                .id("kraftrpg:accepted_armor")
                .name("KraftRPG Accepted Armor")
                .query(of("KraftRPG", "Accepted Armor"))
                .build();

        ACCEPTED_WEAPONS = Key.builder()
                .type(ACCEPTED_WEAPONS_TOKEN)
                .id("kraftrpg:accepted_weapons")
                .name("KraftRPG Accepted Weapons")
                .query(of("KraftRPG", "Accepted Weapons"))
                .build();

        BASE_MANA = Key.builder()
                .type(BASE_MANA_TOKEN)
                .id("kraftrpg:base_mana")
                .name("KraftRPG Base Mana")
                .query(of("KraftRPG", "Base Mana"))
                .build();

        MANA_PER_LEVEL = Key.builder()
                .type(MANA_PER_LEVEL_TOKEN)
                .id("kraftrpg:mana_per_level")
                .name("KraftRPG Mana Per Level")
                .query(of("KraftRPG", "Manage Per Level"))
                .build();

        PROHIBITED_SKILLS = Key.builder()
                .type(PROHIBITED_SKILLS_TOKEN)
                .id("kraftrpg:prohibited_skills")
                .name("KraftRPG Prohibited Skills")
                .query(of("KraftRPG", "Prohibited Skills"))
                .build();

        SKILL_CONFIGURATIONS = Key.builder()
                .type(SKILL_CONFIGURATION_TOKEN)
                .id("kraftrpg:Skill Configuration")
                .name("KraftRPG Skill Configuration")
                .query(of("KraftRPG", "Skill Configuration"))
                .build();

    }

}

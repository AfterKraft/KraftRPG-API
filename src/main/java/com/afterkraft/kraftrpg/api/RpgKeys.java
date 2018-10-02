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


import java.util.Set;

import static org.spongepowered.api.data.DataQuery.of;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.data.value.mutable.SetValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.api.entity.party.Party;
import com.afterkraft.kraftrpg.api.role.Role;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

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
    public static final TypeToken<Value<Double>> COOLDOWN_TOKEN = new TypeToken<Value<Double>>() {};



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
    }

}

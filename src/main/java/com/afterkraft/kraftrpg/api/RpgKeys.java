/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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

import java.util.Optional;

import static org.spongepowered.api.data.DataQuery.of;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.data.value.mutable.OptionalValue;
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


    private RpgKeys() {
    }

    static {
        RPG_EFFECTS = KeyFactory.makeSetKey(Effect.class, of("Kraf<tRPG", "Effects"));
        BASE_DAMAGE = KeyFactory.makeSingleKey(Double.class, Value.class, of("BaseDamage"));
        DAMAGE_MODIFIER = KeyFactory.makeSingleKey(Double.class, Value.class, of("DamageModifier"));
        MANA = KeyFactory.makeSingleKey(Integer.class, Value.class, of("Mana"));
        MAX_MANA = KeyFactory.makeSingleKey(Integer.class, Value.class, of("MaxMana"));
        PARTY = KeyFactory.makeSingleKey(Party.class, Value.class, of("Party"));
        PRIMARY_ROLE = KeyFactory.makeSingleKey(Role.class, Value.class, of("PrimaryRole"));
        SECONDARY_ROLE = KeyFactory.makeSingleKey(Role.class, Value.class, of("SecondaryRole"));
        ADDITIONAL_ROLES = KeyFactory.makeListKey(Role.class, of("AdditionalRoles"));
        SUMMON_DURATION = KeyFactory.makeSingleKey(Long.class, MutableBoundedValue.class, of("SummonDuration"));
        REWARDING_EXPERIENCE = KeyFactory.makeSingleKey(FixedPoint.class, Value.class, of
                ("RewardingExperience"));
    }

}

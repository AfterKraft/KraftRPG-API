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
package com.afterkraft.kraftrpg.api;

import static org.spongepowered.api.data.DataQuery.of;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.SetValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.afterkraft.kraftrpg.api.effect.Effect;

@SuppressWarnings("unchecked")
public final class RpgKeys {

    private RpgKeys() {
    }

    public static final Key<SetValue<Effect>> RPG_EFFECTS;
    public static final Key<Value<Double>> BASE_DAMAGE;
    public static final Key<Value<Double>> DAMAGE_MODIFIER;
    public static final Key<Value<Integer>> MANA;
    public static final Key<Value<Integer>> MAX_MANA;

    static {
        RPG_EFFECTS = KeyFactory.makeSetKey(Effect.class, of("Kraf<tRPG", "Effects"));
        BASE_DAMAGE = KeyFactory.makeSingleKey(Double.class, Value.class, of("BaseDamage"));
        DAMAGE_MODIFIER = KeyFactory.makeSingleKey(Double.class, Value.class, of("DamageModifier"));
        MANA = KeyFactory.makeSingleKey(Integer.class, Value.class, of("Mana"));
        MAX_MANA = KeyFactory.makeSingleKey(Integer.class, Value.class, of("MaxMana"));
    }

}

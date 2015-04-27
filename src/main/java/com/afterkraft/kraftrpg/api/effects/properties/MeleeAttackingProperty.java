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
package com.afterkraft.kraftrpg.api.effects.properties;

import org.spongepowered.api.item.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.effects.EffectOperationResult;
import com.afterkraft.kraftrpg.api.effects.EffectProperty;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Standard imbuing effect that applies specifically to melee/ranged weapons that affect any being
 * when that being is hit by said weapon.
 */
public interface MeleeAttackingProperty extends EffectProperty<MeleeAttackingProperty> {

    /**
     * Gets the number of hits left for this effect before expiration.
     *
     * @return The number of hits before expiration
     */
    int getHitsLeft();

    interface MeleeAttackingOperation {

        /**
         * Performs an operation on the {@link Insentient} victim when the victim is afflicted by
         * the {@link Insentient} attacker affected by an effect with a {@link
         * MeleeAttackingProperty} with the given weapon {@link ItemStack}.
         *
         * @param victim   The victim being damaged
         * @param attacker The attacker having an active effect with this operation
         * @param weapon   The weapon used to attack the victim
         *
         * @return The operation result
         */
        EffectOperationResult onDamage(Insentient victim, Insentient attacker, ItemStack weapon);

    }
}

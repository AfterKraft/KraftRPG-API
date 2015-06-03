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
package com.afterkraft.kraftrpg.api.effect.operation;

import org.spongepowered.api.item.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.api.effect.EffectOperation;
import com.afterkraft.kraftrpg.api.effect.EffectOperationResult;
import com.afterkraft.kraftrpg.api.effect.property.MeleeAttackingProperty;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * A type of {@link EffectOperation} that will perform an action when the {@link Insentient}
 * afflicted with the owning {@link Effect} attacks another {@link Insentient} with the
 * "imbued" weapon. Consider that the {@link Insentient} attacker passed in to the
 * {@link #onDamage(Insentient, Insentient, ItemStack)} method is the {@link Insentient}
 * being afflicted by the {@link Effect} with the {@link MeleeAttackingProperty}. Once the
 * {@link Insentient} attacker attacks the {@link Insentient} victim, it is possible to
 * "cancel" the operation without reducing the {@link MeleeAttackingProperty#getHitsLeft()}
 * value by returning {@link EffectOperationResult#CANCELLED}.
 */
public interface MeleeAttackingOperation {

    /**
     * Performs an operation on the {@link Insentient} victim when the victim is afflicted by
     * the {@link Insentient} attacker affected by an effect with a
     * {@link MeleeAttackingProperty} with the given weapon {@link ItemStack}.
     *
     * @param victim   The victim being damaged
     * @param attacker The attacker having an active effect with this operation
     * @param weapon   The weapon used to attack the victim
     *
     * @return The operation result
     */
    EffectOperationResult onDamage(Insentient victim, Insentient attacker, ItemStack weapon);

}

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
package com.afterkraft.kraftrpg.common.effect;

import java.util.Collection;
import java.util.Set;

import org.spongepowered.api.data.manipulators.PotionEffectData;
import org.spongepowered.api.potion.PotionEffect;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.effect.operation.ApplyEffectOperation;
import com.afterkraft.kraftrpg.api.effect.EffectOperation;
import com.afterkraft.kraftrpg.api.effect.EffectOperationResult;
import com.afterkraft.kraftrpg.api.effect.property.PotionAppliedProperty;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * A standard implementation of {@link PotionAppliedProperty} that can be used to apply {@link
 * PotionEffect}s on an insentient being.
 */
public class StandardPotionAppliedProperty implements PotionAppliedProperty {

    private final Set<PotionEffect> effects;

    /**
     * Constructs a new {@link PotionAppliedProperty} with the given potion effects.
     *
     * @param effects The potion effects to apply when an effect is applied
     */
    public StandardPotionAppliedProperty(Set<PotionEffect> effects) {
        this.effects = ImmutableSet.copyOf(effects);
    }

    /**
     * Constructs a new {@link PotionAppliedProperty} with the given potion effects.
     *
     * @param effects The potion effects to apply when an effect is applied
     */
    public StandardPotionAppliedProperty(PotionEffect... effects) {
        this.effects = ImmutableSet.copyOf(effects);
    }

    @Override
    public Set<EffectOperation> getOperations() {
        return ImmutableSet.<EffectOperation>of(new ApplyEffectOperation() {
            @Override
            public boolean isApplicableTo(Insentient being) {
                return being.getEntity().isPresent() && !being.getEntity().get().isRemoved();
            }

            @Override
            public EffectOperationResult apply(Insentient being) {
                PotionEffectData data = being.getData(PotionEffectData.class).get();
                for (PotionEffect effect : StandardPotionAppliedProperty.this.effects) {
                    data.addPotionEffect(effect, true);
                }
                being.offer(data);
                return EffectOperationResult.SUCCESS;
            }
        });
    }

    @Override
    public Collection<PotionEffect> getPotionEffects() {
        return this.effects;
    }

    @Override
    public int compareTo(PotionAppliedProperty o) {
        Set<PotionEffect> otherEffects = Sets.newHashSet(o.getPotionEffects());
        for (PotionEffect effect : this.effects) {
            if (otherEffects.contains(effect)) {
                otherEffects.remove(effect);
            } else {
                return -1;
            }
        }
        return otherEffects.size();
    }
}

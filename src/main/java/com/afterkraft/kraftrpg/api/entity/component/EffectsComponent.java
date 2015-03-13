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

package com.afterkraft.kraftrpg.api.entity.component;

import java.util.Set;

import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.potion.PotionEffectType;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.effects.Effect;
import com.afterkraft.kraftrpg.api.effects.EffectType;

public interface EffectsComponent extends Component<EffectsComponent> {

    /**
     * Returns (if available) the named {@link Effect}
     *
     * @param name the name of the desired Effect
     *
     * @return the named Effect if not null
     */
    Optional<Effect> getEffect(String name);

    /**
     * Returns an unmodifiable set of Effects this Insentient being has active.
     *
     * @return an unmodifiable set of Effects this Insentient being has active.
     */
    Set<Effect> getEffects();

    /**
     * Adds the given Effect to this Insentient being. Added Effects will be applied on the next
     * tick so as to avoid
     *
     * @param effect to apply to this Insentient
     *
     * @throws IllegalArgumentException If the effect is null
     */
    void addEffect(Effect effect);

    /**
     * Add the {@link PotionEffect} to this Insentient being.  This method is provided with the
     * assurance that the entity would not have a {@link java.util.ConcurrentModificationException}
     * caused by multiple sources.
     *
     * @param potion the effect to be applied
     *
     * @throws IllegalArgumentException If the potion effect is null
     */
    void addPotionEffect(PotionEffect potion);

    /**
     * Remove the {@link PotionEffectType} from this Insentient.  This method is provided with the
     * assurance that the entity would not have a {@link java.util.ConcurrentModificationException}
     * caused by multiple sources.
     *
     * @param type of PotionEffect to remove
     *
     * @throws IllegalArgumentException If the potioneffect type is null
     */
    void removePotionEffect(PotionEffectType type);

    /**
     * Checks if this being has a specific PotionEffectType
     *
     * @param type to check
     *
     * @return true if the being has the queried type of potion effect
     * @throws IllegalArgumentException If the potion effect type is null
     */
    boolean hasPotionEffect(PotionEffectType type);

    /**
     * Check if this Insentient being has an {@link Effect} with the given name.
     *
     * @param name of the effect
     *
     * @return true if there is an active effect by the queried name
     * @throws IllegalArgumentException If the name is null or empty
     */
    boolean hasEffect(String name);

    /**
     * Check if this being has an effect of the given {@link com.afterkraft.kraftrpg.api.effects.EffectType}.
     *
     * @param type of effect
     *
     * @return true if this being has effect of the queried type
     * @throws IllegalArgumentException If the type is null
     */
    boolean hasEffectType(EffectType type);

    /**
     * Safely removes the {@link Effect} from this being. This will perform all necessary removal
     * operations from the effect on this entity.
     *
     * @param effect to be removed.
     *
     * @throws IllegalArgumentException If the effect is null
     */
    void removeEffect(Effect effect);

    /**
     * Unsafely removes the IEffect from this being. It will not perform any removal operations and
     * may leave behind some unintended {@link PotionEffect}s on the being.
     *
     * @param effect being removed.
     *
     * @throws IllegalArgumentException If the effect is null
     */
    void manualRemoveEffect(Effect effect);

    /**
     * Safely removes all active effects on this being.
     */
    void clearEffects();

    /**
     * Unsafely removes all active effects without calling any removal operations from the active
     * effects.
     */
    void manualClearEffects();


}

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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.world.Location;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.api.effect.EffectOperation;
import com.afterkraft.kraftrpg.api.effect.EffectProperty;
import com.afterkraft.kraftrpg.api.effect.EffectType;
import com.afterkraft.kraftrpg.api.effect.operation.ApplyEffectOperation;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Standard implementation of an {@link Effect}. The standard effect can be used to create effects
 * without worrying about extending implementing many methods.
 *
 * As the contract of {@link Effect} dictates, the standard effect is immutable upon creation. The
 * effect should not change once created, and can not be modified in any way.
 */
public class StandardEffect implements Effect {
    protected final Set<EffectProperty<?>> properties;
    protected final Set<EffectType> types;
    private final Set<ApplyEffectOperation> applyOperations;
    private final String name;
    private final Text applyText;
    protected long applyTime;

    /**
     * Creates a new {@link StandardEffect} with the desired operations, name and effect types.
     *
     * @param name            The name of this standard effect
     * @param applyOperations The many standard operations when the effect is applied
     * @param types           The various effect types
     */
    public StandardEffect(String name,
                          Set<ApplyEffectOperation> applyOperations,
                          Collection<EffectType> types) {
        this(name, applyOperations, types, Texts.of(""), Sets.<EffectProperty<?>>newHashSet());
    }

    /**
     * Creates a new {@link StandardEffect} with the desired operations, name, effect types,
     * application message, and effect properties.
     *
     * <p>The application properties are copied and can not be changed once added to the effect
     * .</p>
     *
     * @param name            The name of this standard effect
     * @param applyOperations The many operations that will be performed when this effect is
     *                        applied
     * @param types           The effect types
     * @param applyText       The message that is shown on application
     * @param properties      The effect properties, further customizing what this effect does
     */
    public StandardEffect(String name,
                          Set<ApplyEffectOperation> applyOperations,
                          Collection<EffectType> types,
                          Text applyText,
                          Set<EffectProperty<?>> properties) {
        checkArgument(!name.isEmpty(),
                      "Cannot create an effect with an empty name!");
        checkNotNull(applyOperations);
        checkArgument(!applyOperations.isEmpty(), "Effects must have some application operation!");
        checkNotNull(types);
        checkNotNull(applyText);
        this.name = name;
        this.properties = ImmutableSet.copyOf(properties);
        this.types = ImmutableSet.copyOf(types);
        this.applyText = applyText;
        ImmutableSet.Builder<ApplyEffectOperation> builder = ImmutableSet.builder();
        builder.addAll(applyOperations);
        for (EffectProperty<?> property : this.properties) {
            for (EffectOperation operation : property.getOperations()) {
                if (operation instanceof ApplyEffectOperation) {
                    builder.add((ApplyEffectOperation) operation);
                }
            }
        }
        this.applyOperations = builder.build();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final boolean isType(EffectType queryType) {
        return this.types.contains(queryType);
    }

    @Override
    public final long getApplyTime() {
        return this.applyTime;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T extends EffectProperty<?>> Optional<T> getProperty(Class<T> propertyClass) {
        for (EffectProperty<?> property : this.properties) {
            if (propertyClass.equals(property.getClass())) {
                return Optional.of((T) property);
            }
        }
        return Optional.absent();
    }

    @Override
    public final Set<ApplyEffectOperation> getApplicationOperations() {
        return this.applyOperations;
    }

    @Override
    public final Text getApplyText() {
        return this.applyText;
    }

    /**
     * Checks if the {@link Insentient} being is compatible with this effect. All operations must
     * return true if the effect is compatible.
     *
     * @param being The insentient being the effect is attempting to be applied to
     *
     * @return If this effect is compatible being applied to the target
     */
    public final boolean isCompatible(Insentient being) {
        for (ApplyEffectOperation operation : this.applyOperations) {
            if (!operation.isApplicableTo(being)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Performs all {@link ApplyEffectOperation}s on the given being as this effect is applied.
     *
     * @param being The being this effect is being applied to
     */
    public final void apply(Insentient being) {
        this.applyTime = System.currentTimeMillis();
        for (ApplyEffectOperation operation : this.applyOperations) {
            operation.apply(being);
        }
        // TODO send application message
    }

    private boolean isInMsgRange(Location loc1, Location loc2) {
        return (Math.abs(loc1.getPosition().getFloorX()
                                 - loc2.getPosition().getFloorX()) < 25)
                && (Math.abs(loc1.getPosition().getFloorY()
                                     - loc2.getPosition().getFloorY()) < 25)
                && (Math.abs(loc1.getPosition().getFloorZ()
                                     - loc2.getPosition().getFloorZ()) < 25);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.properties, this.types, this.applyOperations, this.name,
                                this.applyText, this.applyTime);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final StandardEffect other = (StandardEffect) obj;
        return Objects.equal(this.properties, other.properties)
                && Objects.equal(this.types, other.types)
                && Objects.equal(this.applyOperations, other.applyOperations)
                && Objects.equal(this.name, other.name)
                && Objects.equal(this.applyText, other.applyText)
                && Objects.equal(this.applyTime, other.applyTime);
    }
}

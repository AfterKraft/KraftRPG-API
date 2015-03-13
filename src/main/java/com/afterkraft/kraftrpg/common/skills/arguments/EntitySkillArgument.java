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
package com.afterkraft.kraftrpg.common.skills.arguments;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.message.Message;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.common.skills.AbstractSkillArgument;

/**
 * {@inheritDoc} A SkillArgument that pre-selects entities based on
 *
 * @param <E> The type of Entity to isolate
 */
public class EntitySkillArgument<E extends Entity> extends
        AbstractSkillArgument<E> {
    protected final double maxDistance;
    protected final Predicate<E> condition;
    private final Class<E> clazz;

    protected WeakReference<E> matchedEntity = new WeakReference<E>(null);

    /**
     * Creates a new EntitySkillArgument with a desired predicate and requirements.
     *
     * @param maxDistance The maximum distance to ray cast
     * @param clazz       The class to match the entities
     */
    public EntitySkillArgument(double maxDistance, Class<E> clazz) {
        this(maxDistance, clazz, new Predicate<E>() {
            @Override
            public boolean apply(
                    @Nullable
                    E input) {
                return true;
            }
        });
    }

    /**
     * Creates a new EntitySkillArgument with a desired predicate and requirements.
     *
     * @param maxDistance The maximum distance to ray cast
     * @param clazz       The class to match the entities
     * @param condition   A special predicate to require as well
     */
    public EntitySkillArgument(double maxDistance, Class<E> clazz,
                               Predicate<E> condition) {
        this(true, maxDistance, clazz, condition);
    }

    /**
     * Creates a new EntitySkillArgument with a desired predicate and requirements.
     *
     * @param required    Whether the argument is required or not
     * @param maxDistance The maximum distance to ray cast
     * @param clazz       The class to match the entities
     * @param condition   A special predicate to require as well
     */
    protected EntitySkillArgument(boolean required, double maxDistance,
                                  Class<E> clazz,
                                  Predicate<E> condition) {
        super(required);
        this.maxDistance = maxDistance;
        this.condition = condition;
        this.clazz = clazz;
    }

    @Override
    public String getUsageString(boolean optional) {
        return "";
    }

    @Override
    public int matches(SkillCaster caster, String[] allArgs,
                       int startPosition) {
        return 0;
    }

    @Override
    public void parse(final SkillCaster caster, final String[] allArgs,
                      final int startPosition) {
        Collection<Entity> nearby = caster.getWorld().getEntities(
                new Predicate<Entity>() {
                    @Override
                    public boolean apply(
                            @Nullable
                            Entity input) {
                        if (input == null) {
                            return false;
                        }
                        Vector3d targetCoords =
                                input.getLocation().getPosition();
                        Vector3d casterCoords = caster.getLocation()
                                .getPosition();
                        return targetCoords.distance(casterCoords)
                                > EntitySkillArgument.this.maxDistance;
                    }
                });
        Living actor = caster.getEntity().get();
        Vector3f middle = actor.getEyeLocation();

        double closestDistance = this.maxDistance;
        @Nullable
        E closest = null;

        for (Entity entity : nearby) {
            if (!this.clazz.isInstance(entity)) {
                continue;
            }
            @SuppressWarnings("unchecked")
            E ent = (E) entity;

            Vector3d otherMiddle = entity.getLocation().getPosition();
            if (entity instanceof Living) {
                Vector3f eyeVec = ((Living) entity).getEyeLocation();
                otherMiddle = new Vector3d(eyeVec.getX(), eyeVec.getY(),
                                           eyeVec.getZ());
            }
            Vector3d diff = otherMiddle.sub(new Vector3d(middle.getX(),
                                                         middle.getY(),
                                                         middle.getZ()));
            // Algorithm: Make a triangle
            Vector3d doubleDirection = new Vector3d(middle.getX(),
                                                    middle.getY(),
                                                    middle.getZ());
            double b = diff.dot(doubleDirection);
            double c = middle.distanceSquared(new Vector3f(otherMiddle.getX()
                    , otherMiddle.getY(), otherMiddle.getZ()));

            double a = Math.sqrt(c - b * b);
            if (a < closestDistance) {
                if (this.condition.apply(ent)) {
                    closestDistance = a;
                    closest = ent;
                }
            }
        }

        if (closestDistance < this.maxDistance) {
            this.matchedEntity = new WeakReference<>(closest);
        }
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<E> getValue() {
        return Optional.fromNullable(this.matchedEntity.get());
    }

    @Override
    public void clean() {
        this.matchedEntity = new WeakReference<>(null);
    }

    @Override
    public List<Message> tabComplete(SkillCaster caster, String[] allArgs,
                                    int startPosition) {
        return Lists.newArrayList();
    }

    /**
     * Gets the maximum distance set for this argument.
     *
     * @return The maximum distance
     */
    public double getMaxDistance() {
        return this.maxDistance;
    }
}

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
package com.afterkraft.kraftrpg.api.skills.arguments;

import java.lang.ref.WeakReference;
import java.util.List;

import org.spongepowered.api.entity.Entity;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;

/**
 * {@inheritDoc} A SkillArgument that pre-selects entities based on
 *
 * @param <E> The type of Entity to isolate
 */
public class EntitySkillArgument<E extends Entity> extends SkillArgument<E> {
    protected final double maxDistance;
    protected final Predicate<E> condition;
    private final Class<E> clazz;

    protected WeakReference<E> matchedEntity = null;

    public EntitySkillArgument(double maxDistance, Class<E> clazz) {
        this(maxDistance, clazz, null);
    }

    public EntitySkillArgument(double maxDistance, Class<E> clazz, Predicate<E> condition) {
        this(true, maxDistance, clazz, condition);
    }

    protected EntitySkillArgument(boolean required, double maxDistance, Class<E> clazz,
                                  Predicate<E> condition) {
        super(required);
        this.maxDistance = maxDistance;
        this.condition = condition;
        this.clazz = clazz;
    }

    @Override
    public Optional<E> getValue() {
        return Optional.fromNullable(this.matchedEntity.get());
    }

    // --------------------------------------------------------------

    @Override
    public String getUsageString(boolean optional) {
        return "";
    }

    @Override
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        return 0;
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        /*
        List<Entity> nearby = caster.getEntity().getNearbyEntities(this.maxDistance,
                this.maxDistance, this.maxDistance);
        Living actor = caster.getEntity().get();
        Vector3f middle = actor.getEyeLocation();
        Vector direction = middle.getDirection();

        double closestDistance = this.maxDistance;
        E closest = null;

        for (Entity entity : nearby) {
            if (!this.clazz.isInstance(entity)) {
                continue;
            }
            @SuppressWarnings("unchecked")
            // just checked it
                    E ent = (E) entity;

            // TODO change to middle for livings??
            Location otherMiddle = entity.getLocation();
            if (entity instanceof Living) {
                otherMiddle = ((Living) entity).getEyeLocation();
            }
            Location diff = otherMiddle.subtract(middle);
            // Algorithm: Make a triangle
            double b = diff.toVector().dot(direction);
            double c = middle.distanceSquared(otherMiddle);

            double a = Math.sqrt(c - b * b);
            if (a < closestDistance) {
                if (this.condition == null || this.condition.apply(ent)) {
                    closestDistance = a;
                    closest = ent;
                }
            }
        }

        if (closestDistance < this.maxDistance) {
            this.matchedEntity = new WeakReference<>(closest);
        }
        */
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clean() {
        this.matchedEntity = null;
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition) {
        return null;
    }

    public double getMaxDistance() {
        return this.maxDistance;
    }
}

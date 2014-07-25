/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.skills.arguments;

import java.util.List;

import com.google.common.base.Predicate;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;

public class EntitySkillArgument<E extends Entity> extends SkillArgument {
    protected final double maxDistance;
    protected final Predicate<E> condition;
    private final Class<E> clazz;

    protected E matchedEntity = null;

    public EntitySkillArgument(double maxDistance, Class<E> clazz, Predicate<E> condition) {
        super(true);
        this.maxDistance = maxDistance;
        this.condition = condition;
        this.clazz = clazz;
    }

    protected EntitySkillArgument(boolean required, double maxDistance, Class<E> clazz, Predicate<E> condition) {
        super(required);
        this.maxDistance = maxDistance;
        this.condition = condition;
        this.clazz = clazz;
    }

    public E getMatchedEntity() {
        return this.matchedEntity;
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
        List<Entity> nearby = caster.getEntity().getNearbyEntities(this.maxDistance, this.maxDistance, this.maxDistance);
        LivingEntity actor = caster.getEntity();
        Location middle = actor.getEyeLocation();
        Vector direction = middle.getDirection();

        double closestDistance = this.maxDistance;
        E closest = null;

        for (Entity entity : nearby) {
            if (!entity.getClass().isAssignableFrom(this.clazz)) {
                continue;
            }
            @SuppressWarnings("unchecked")
            // just checked it
            E ent = (E) entity;

            // TODO change to middle for livings??
            Location otherMiddle = entity.getLocation();
            if (entity instanceof LivingEntity) {
                otherMiddle = ((LivingEntity) entity).getEyeLocation();
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
            this.matchedEntity = closest;
        }
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
}

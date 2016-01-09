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
package com.afterkraft.kraftrpg.common.skill.argument;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.source.LocatedSource;
import org.spongepowered.api.data.property.entity.EyeLocationProperty;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.common.skill.AbstractSkillArgument;

/**
 * {@inheritDoc} A SkillArgument that pre-selects entities based on
 *
 * @param <E> The type of Entity to isolate
 */
public class EntitySkillArgument<E extends Entity> extends CommandElement {
    protected final double maxDistance;
    protected final Predicate<E> condition;
    private final Class<E> clazz;

    /**
     * Creates a new EntitySkillArgument with a desired predicate and requirements.
     *
     * @param maxDistance The maximum distance to ray cast
     * @param clazz       The class to match the entities
     */
    public EntitySkillArgument(Text key, double maxDistance, Class<E> clazz) {
        this(key, maxDistance, clazz, input -> true);
    }

    /**
     * Creates a new EntitySkillArgument with a desired predicate and requirements.
     *
     * @param key    Whether the argument is required or not
     * @param maxDistance The maximum distance to ray cast
     * @param clazz       The class to match the entities
     * @param condition   A special predicate to require as well
     */
    protected EntitySkillArgument(Text key, double maxDistance,
                                  Class<E> clazz,
                                  Predicate<E> condition) {
        super(key);
        this.maxDistance = maxDistance;
        this.condition = condition;
        this.clazz = clazz;
    }

    /**
     * Gets the maximum distance set for this argument.
     *
     * @return The maximum distance
     */
    public double getMaxDistance() {
        return this.maxDistance;
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws
            ArgumentParseException {
        if (!(source instanceof Living)) {
            throw new ArgumentParseException(Texts.of("The command source is not located!"),
                                             source.toString(), 0);
        }
        final Living callingEntity = (Living) source;
        Location<World> location = callingEntity.getLocation();
        Collection<Entity> nearby = callingEntity.getWorld().getEntities(
                input -> {
                    if (input == null) {
                        return false;
                    }
                    Vector3d targetCoords = input.getLocation().getPosition();
                    Vector3d casterCoords = location.getPosition();
                    return targetCoords.distance(casterCoords)
                            > EntitySkillArgument.this.maxDistance;
                });
        Vector3d middle = callingEntity.getProperty(EyeLocationProperty.class).get().getValue();

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
                otherMiddle = entity.getProperty(EyeLocationProperty.class).get().getValue();
            }
            final Vector3d diff = otherMiddle.sub(middle);
            // Algorithm: Make a triangle
            final Vector3d doubleDirection = middle.clone();
            final double b = diff.dot(doubleDirection);
            final double c = middle.distanceSquared(otherMiddle);

            final double a = Math.sqrt(c - b * b);
            if (a < closestDistance) {
                if (this.condition.test(ent)) {
                    closestDistance = a;
                    closest = ent;
                }
            }
        }

        if (closestDistance < this.maxDistance) {
            return closest;
        }
        return null;
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return Collections.emptyList();
    }
}

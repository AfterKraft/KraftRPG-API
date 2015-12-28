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
package com.afterkraft.kraftrpg.api.entity;

import java.util.Optional;

import org.spongepowered.api.world.Location;


import com.afterkraft.kraftrpg.api.role.ExperienceType;
import com.afterkraft.kraftrpg.api.role.Role;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * Represents a being that is able to obtain experience and advance through {@link Role}s. A
 * Sentient being can gain experience and have various effects applied to it. Usually, this type of
 * being is applied to entities that are to be interacted with but not interfered with in regards to
 * {@link Skill}.
 */
public interface Sentient extends Insentient {

    /**
     * Fetches the current experience of the given Role.  If this sentient being has no experience
     * in the given Role, this will return a FixedPoint with value 0
     *
     * @param role to check the current experience of
     *
     * @return The FixedPoint value of the designated role.
     * @throws IllegalArgumentException If the role is null
     */
    Optional<FixedPoint> getExperience(Role role);

    /**
     * Check if this being can gain experience of the designated type. This will check with all
     * active {@link com.afterkraft.kraftrpg.api.role.Role}s
     *
     * @param type of experience
     *
     * @return If the experience type can be gained
     * @throws IllegalArgumentException If the type is null
     */
    boolean canGainExperience(ExperienceType type);

    /**
     * Tells this being to gain experience of the determined {@link ExperienceType} It will also
     * return the final {@link FixedPoint} value of experience gained by this method.
     *
     * @param exp      The amount of experience being gained
     * @param type     The type of experience
     * @param location The location of the experience source
     *
     * @return The final amount of experience gained
     * @throws IllegalArgumentException If the experience is null
     * @throws IllegalArgumentException If the type is null
     * @throws IllegalArgumentException If the location is null
     */
    FixedPoint gainExperience(FixedPoint exp, ExperienceType type,
                              Location location);

    /**
     * Tells this being to lose the prescribed experience from all {@link
     * com.afterkraft.kraftrpg.api.role.Role}s of which if configured, looses experience on death.
     *
     * @param multiplier Percentage of the current level to lose in experience
     * @param byPVP      If true, has some alternate modifications to the total loss
     *
     * @throws IllegalArgumentException If the multiplier is negative
     */
    void loseExperienceFromDeath(double multiplier, boolean byPVP);

}

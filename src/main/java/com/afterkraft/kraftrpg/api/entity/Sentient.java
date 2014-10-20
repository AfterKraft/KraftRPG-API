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
package com.afterkraft.kraftrpg.api.entity;

import java.util.List;
import java.util.Set;

import org.bukkit.Location;

import com.afterkraft.kraftrpg.api.roles.ExperienceType;
import com.afterkraft.kraftrpg.api.roles.Role;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * Represents a being that is able to obtain experience and advance through {@link Role}s. A
 * Sentient being can gain experience and have various effects applied to it. Usually, this type of
 * being is applied to entities that are to be interacted with but not interfered with in regards to
 * {@link com.afterkraft.kraftrpg.api.skills.ISkill}.
 */
public interface Sentient extends Insentient {

    /**
     * Fetches the current experience of the given Role.
     * <p/>
     * If this sentient being has no experience in the given Role, this will return a FixedPoint
     * with value 0
     *
     * @param role to check the current experience of
     *
     * @return the FixedPoint value of the designated role.
     * @throws IllegalArgumentException If the role is null
     */
    FixedPoint getExperience(Role role);

    /**
     * Check if this being can gain experience of the designated type. This will check with all
     * active {@link com.afterkraft.kraftrpg.api.roles.Role}s
     *
     * @param type of experience
     *
     * @return true if the experience type can be gained
     * @throws IllegalArgumentException If the type is null
     */
    boolean canGainExperience(ExperienceType type);

    /**
     * Tells this being to gain experience of the determined {@link com.afterkraft.kraftrpg.api.roles.ExperienceType}
     * It will also return the final {@link com.afterkraft.kraftrpg.api.util.FixedPoint} value of
     * experience gained by this method.
     *
     * @param exp      to gain
     * @param type     of experience
     * @param location of experience
     *
     * @return the final experience gained
     * @throws IllegalArgumentException If the experience is null
     * @throws IllegalArgumentException If the type is null
     * @throws IllegalArgumentException If the location is null
     */
    FixedPoint gainExperience(FixedPoint exp, ExperienceType type, Location location);

    /**
     * Tells this being to lose the prescribed experience from all {@link
     * com.afterkraft.kraftrpg.api.roles.Role}s of which if configured, looses experience on death.
     *
     * @param multiplier percentage of the current level to lose in experience
     * @param byPVP      if true, has some alternate modifications to the total loss
     *
     * @throws IllegalArgumentException If the multiplier is negative
     */
    void loseExperienceFromDeath(double multiplier, boolean byPVP);

    /**
     * Get the currently active Primary {@link Role} that this sentient being has
     *
     * @return the current primary role this sentient being has activated
     */
    Role getPrimaryRole();

    /**
     * Get the currently active Secondary {@link Role} that this sentient being has
     *
     * @return the current secondary role this sentient being has activated
     */
    Role getSecondaryRole();

    /**
     * Set the current {@link com.afterkraft.kraftrpg.api.roles.Role.RoleType#PRIMARY} {@link Role}
     * . The {@link com.afterkraft.kraftrpg.api.roles.Role.RoleType} is checked prior to setting. A
     * sentient being may only have one Primary active role at any given time.
     *
     * @param role the Primary role to set this sentient being's primary role to
     *
     * @throws IllegalArgumentException If the role is not of the Primary type
     */
    boolean setPrimaryRole(Role role);

    /**
     * Set the current {@link com.afterkraft.kraftrpg.api.roles.Role.RoleType#SECONDARY} {@link
     * Role}. The {@link com.afterkraft.kraftrpg.api.roles.Role.RoleType} is checked prior to
     * setting. A sentient being may only have one Secondary active role at any given time.
     *
     * @param role the Secondary role to set this sentient being's secondary role to
     *
     * @return true if the set was successful
     * @throws IllegalArgumentException If the role is not of the Secondary type
     */
    boolean setSecondaryRole(Role role);

    /**
     * Get a Set of {@link Role}s that are marked as {@link com.afterkraft.kraftrpg.api.roles.Role.RoleType#ADDITIONAL}
     * that are active on this sentient being.
     *
     * @return an unmodifiable set of additional Roles this sentient being has activated
     */
    Set<Role> getAdditionalRoles();

    /**
     * Attempts to add the given {@link Role} provided that the {@link
     * com.afterkraft.kraftrpg.api.roles.Role.RoleType} is ADDITIONAL.
     *
     * @param role the additional role to add to this sentient being
     *
     * @return true if the role was added successfully
     * @throws IllegalArgumentException If the role is null
     * @throws IllegalArgumentException If the role is not of the type Additional
     */
    boolean addAdditionalRole(Role role);

    /**
     * Attempts to remove the given {@link Role} provided that the {@link
     * com.afterkraft.kraftrpg.api.roles.Role.RoleType} is ADDITIONAL. This does NOT perform any
     * skill permission refreshes, nor does it check for any passive skills refreshes. This would
     * need to be checked manually by the caller of this method.
     *
     * @param role the additional role to remove from this sentient being
     *
     * @return true if the role was removed successfully
     * @throws IllegalArgumentException If the role is null
     */
    boolean removeAdditionalRole(Role role);

    /**
     * Get a Set of all {@link Role}s active on this sentient being.
     * <p/>
     * The set should be slightly ordered, with primary first, profession second (if present),
     * followed by all additional roles (additional roles are in an unspecified order). (This can be
     * accomplished with an ImmutableSet.)
     *
     * @return an immutable list of all roles
     */
    List<Role> getAllRoles();

    /**
     * Fetches the current calculated level of the designated Role. If the Role is not leveled by
     * this sentient being, the level will return 0
     *
     * @param role to get the current level
     *
     * @return the current calculated level of this Role if not 0
     * @throws IllegalArgumentException If the role is null
     */
    int getLevel(Role role);
}

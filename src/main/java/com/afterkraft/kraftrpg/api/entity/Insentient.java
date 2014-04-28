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
package com.afterkraft.kraftrpg.api.entity;

import java.util.Set;

import org.bukkit.Location;

import com.afterkraft.kraftrpg.api.entity.roles.ExperienceType;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.util.FixedPoint;


public interface Insentient {

    /**
     * Fetches the current experience of the given Role.
     * <p/>
     * If this Champion has no experience in the given Role, this will return a
     * FixedPoint with value 0
     *
     * @param role to check the current experience of
     * @return the FixedPoint value of the designated role.
     */
    public FixedPoint getExperience(Role role);

    public boolean canGainExperience(ExperienceType type);

    public FixedPoint gainExperience(FixedPoint exp, ExperienceType type, Location location);

    /**
     * Get the currently active Primary {@link Role} that this Champion has
     *
     * @return the current primary role this Champion has activated
     */
    public Role getPrimaryRole();

    /**
     * Get the currently active Secondary {@link Role} that this Champion has
     *
     * @return the current secondary role this Champion has activated
     */
    public Role getSecondaryRole();

    /**
     * Set the current {@link com.afterkraft.kraftrpg.api.entity.roles.RoleType#PRIMARY}
     * {@link Role}. The {@link com.afterkraft.kraftrpg.api.entity.roles.RoleType}
     * is checked prior to setting. A Champion may only have one Primary active
     * role at any given time.
     *
     * @param role the Primary role to set this Champion's primary role to
     */
    public boolean setPrimaryRole(Role role);

    /**
     * Set the current {@link com.afterkraft.kraftrpg.api.entity.roles.RoleType#SECONDARY}
     * {@link Role}. The {@link com.afterkraft.kraftrpg.api.entity.roles.RoleType}
     * is checked prior to setting. A Champion may only have one Secondary
     * active role at any given time.
     *
     * @param role the Secondary role to set this Champion's secondary role to
     * @return true if the set was successful
     */
    public boolean setSecondaryRole(Role role);

    /**
     * Get a Set of {@link Role}s that are marked as {@link
     * com.afterkraft.kraftrpg.api.entity.roles.RoleType#ADDITIONAL} that are
     * active on this Champion.
     *
     * @return an unmodifiable set of additional Roles this Champion has
     * activated
     */
    public Set<Role> getAdditionalRoles();

    /**
     * Attempts to add the given {@link Role} provided that the {@link
     * com.afterkraft.kraftrpg.api.entity.roles.RoleType} is ADDITIONAL.
     *
     * @param role the additional role to add to this Champion
     * @return true if the role was added successfully
     */
    public boolean addAdditionalRole(Role role);

    /**
     * Attempts to remove the given {@link Role} provided that the {@link
     * com.afterkraft.kraftrpg.api.entity.roles.RoleType} is ADDITIONAL.
     *
     * @param role the additional role to remove from this Champion
     * @return true if the role was removed successfully
     */
    public boolean removeAdditionalRole(Role role);

    /**
     * Fetches the current calculated level of the designated Role. If the Role
     * is not leveled by this Champion, the level will return 0
     *
     * @param role to get the current level
     * @return the current calculated level of this Role if not 0
     */
    public int getLevel(Role role);

}

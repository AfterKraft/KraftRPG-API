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
package com.afterkraft.kraftrpg.api.role;

import javax.annotation.Nullable;
import java.util.List;

import com.afterkraft.kraftrpg.api.util.graph.CircularDependencyException;
import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.role.Role.RoleType;
import com.afterkraft.kraftrpg.api.util.math.FixedPoint;

/**
 * Manages all interactions with roles and entities. Roles can be assigned, created, and swapped
 * with this manager.
 */
public interface RoleManager extends Service {

    /**
     * Gets the current default primary role assigned to champions upon initial creation.
     *
     * @return The default primary role, if available
     */
    Role getDefaultPrimaryRole();

    /**
     * Sets the default primary role assigned to newly created champions.
     *
     * @param role The default primary role
     *
     * @return True if successful
     */
    boolean setDefaultPrimaryRole(Role role);

    /**
     * Gets the default secondary role assigned to newly created champions.
     *
     * <p>It is not a requirement for a secondary role to exist, unlike primary roles.</p>
     *
     * @return The secondary role, if available
     */
    Optional<Role> getDefaultSecondaryRole();

    /**
     * Sets the default secondary role for newly created champions.
     *
     * <p>It is not a requirement for a secondary role to exist, unlike primary roles.</p>
     *
     * @param role The secondary role, can be null
     */
    void setDefaultSecondaryRole(
            @Nullable
            Role role);

    /**
     * Gets the role by name.
     *
     * @param roleName The role name
     *
     * @return The role, if available
     */
    Optional<Role> getRole(String roleName);

    /**
     * Adds the given role to this manager.
     *
     * @param role The role to register
     *
     * @return True if successful
     */
    boolean addRole(Role role);

    /**
     * Removes the given role from availability. All existing insentient entities and offline player
     * data may be affected by the removal of the role if assigned. Depending on the implementation,
     * this process may be fully synchronized with the server.
     *
     * @param role The role to remove
     *
     * @return True if successful
     */
    boolean removeRole(Role role);

    /**
     * Gets a list of all available roles.
     *
     * @return A list of all available roles
     */
    List<Role> getRoles();

    /**
     * Gets a list of all available roles by the specific {@link RoleType}.
     *
     * @param type The type of role
     *
     * @return A list of all roles with the specified type
     */
    List<Role> getRolesByType(RoleType type);

    /**
     * Gets the required experience for the specified level for the given {@link Role}.
     *
     * @param role  The role
     * @param level The level
     *
     * @return The experience requirement
     */
    FixedPoint getRoleLevelExperience(Role role, int level);

    /**
     * Attempts to add a dependency for the two involving roles. This may cause a re-assignment of
     * roles for all sentient beings with either of the roles being swapped out.
     *
     * <p>Note that it is inherently illegal for any instance of a circular dependency to exist with
     * roles. This is designed to avoid issues of a player attempting to select a role that has a
     * dependency of other roles that depend on the given role, preventing the player from actually
     * selecting the given role. </p>
     *
     * @param parent The parent role adding the dependency
     * @param child  The child role adding the parent dependency
     *
     * @return True if successful
     * @throws IllegalArgumentException    If any roles are null
     * @throws CircularDependencyException If the resulting dependency results in a circular
     *                                     dependency
     */
    boolean addRoleDependency(Role parent, Role child);

    /**
     * Removes the given role dependencies between the parent {@link Role} and the child {@link
     * Role}.
     *
     * @param parent The role becoming a parent
     * @param child  The role becoming dependent on the parent
     *
     * @return True if successful
     */
    boolean removeRoleDependency(Role parent, Role child);

    /**
     * This is a simple check for all registered Roles. This should not be called often, but it is
     * used when adding role dependencies.
     *
     * @return True if the dependencies are cyclic
     */
    boolean areRoleDependenciesCyclic();
}

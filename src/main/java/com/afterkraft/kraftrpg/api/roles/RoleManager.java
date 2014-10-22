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
package com.afterkraft.kraftrpg.api.roles;

import java.util.Map;

import com.afterkraft.kraftrpg.api.CircularDependencyException;
import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * Manages all interactions with roles and entities. Roles can be assigned, created, and swapped
 * with this manager.
 */
public interface RoleManager extends Manager {

    Role getDefaultPrimaryRole();

    boolean setDefaultPrimaryRole(Role role);

    Role getDefaultSecondaryRole();

    void setDefaultSecondaryRole(Role role);

    Role getRole(String roleName);

    boolean addRole(Role role);

    boolean removeRole(Role role);

    Map<String, Role> getRoles();

    Map<String, Role> getRolesByType(Role.RoleType type);

    FixedPoint getRoleLevelExperience(Role role, int level);

    /**
     * Attempts to add a dependency for the two involving roles. This method is here to
     *
     * @param parent The parent role adding the dependency
     * @param child  The child role adding the parent dependency
     *
     * @return True if successful
     * @throws IllegalArgumentException    If any roles are null
     * @throws CircularDependencyException If the resulting dependency results in a circular
     *                                     dependency
     */
    boolean addRoleDependency(Role parent, Role child) throws CircularDependencyException;

    boolean removeRoleDependency(Role parent, Role child);

    /**
     * This is a simple check for all registered Roles. This should not be called often, but it is
     * used when adding role dependencies.
     *
     * @return True if the dependencies are cyclic
     */
    boolean areRoleDependenciesCyclic();
}

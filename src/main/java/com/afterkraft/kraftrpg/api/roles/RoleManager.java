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


public interface RoleManager extends Manager {

    public Role getDefaultPrimaryRole();

    public boolean setDefaultPrimaryRole(Role role);

    public Role getDefaultSecondaryRole();

    public void setDefaultSecondaryRole(Role role);

    public Role getRole(String roleName);

    public boolean addRole(Role role);

    public boolean removeRole(Role role);

    public Map<String, Role> getRoles();

    public Map<String, Role> getRolesByType(Role.RoleType type);

    public FixedPoint getRoleLevelExperience(Role role, int level);

    /**
     * Attempts to add a dependency for the two involving roles. This method
     * is here to
     *
     * @param parent
     * @param child
     * @return
     * @throws IllegalArgumentException
     * @throws CircularDependencyException
     */
    public boolean addRoleDependency(Role parent, Role child) throws CircularDependencyException;

    public boolean removeRoleDependency(Role parent, Role child);

    /**
     * This is a simple check for all registered Roles. This should not be
     * called often, but it is used when adding role dependencies.
     *
     * @return
     */
    public boolean areRoleDependenciesCyclic();
}

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
package com.afterkraft.kraftrpg.api.roles;

import java.util.Map;

import com.afterkraft.kraftrpg.api.CircularDependencyException;
import com.afterkraft.kraftrpg.api.Manager;


public interface RoleManager extends Manager {

    public Role getDefaultPrimaryRole();

    public boolean setDefaultPrimaryRole(Role role) throws IllegalArgumentException;

    public Role getDefaultSecondaryRole();

    public void setDefaultSecondaryRole(Role role) throws IllegalArgumentException;

    public Role getRole(String roleName) throws IllegalArgumentException;

    public boolean addRole(Role role) throws IllegalArgumentException;

    public boolean removeRole(Role role) throws IllegalArgumentException;

    public Map<String, Role> getRoles();

    public Map<String, Role> getRolesByType(Role.RoleType type) throws IllegalArgumentException;

    /**
     * Attempts to add a dependency for the two involving roles. This method
     * is here to
     * @param parent
     * @param child
     * @return
     * @throws IllegalArgumentException
     * @throws CircularDependencyException
     */
    public boolean addRoleDependency(Role parent, Role child) throws IllegalArgumentException, CircularDependencyException;

    public boolean removeRoleDependency(Role parent, Role child) throws IllegalArgumentException;

    /**
     * This is a simple check for all registered Roles. This should not be
     * called often, but it is used when adding role dependencies.
     * 
     * @return
     */
    public boolean areRoleDependenciesCyclic();
}

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
package com.afterkraft.kraftrpg.api.entity.roles;

import java.util.Map;

import com.afterkraft.kraftrpg.api.Manager;


public interface RoleManager extends Manager {

    public Role getDefaultPrimaryRole();

    public boolean setDefaultPrimaryRole(Role role);

    public Role getDefaultSecondaryRole();

    public boolean setDefaultSecondaryRole(Role role);

    public Role getRole(String roleName);

    public boolean addRole(Role role);

    public boolean removeRole(Role role);

    public Map<String, Role> getRoles();

    public Map<String, Role> getRolesByType(RoleType type);

    public void queueRoleRefresh(Role role, RoleRefreshReason reason);

    public boolean addRoleDependency(Role parent, Role child);

    public void removeRoleDependency(Role parent, Role child);

    public enum RoleRefreshReason {
        SKILL_REMOVAL,
        SKILL_ADDITION,
        RELOAD
    }
}

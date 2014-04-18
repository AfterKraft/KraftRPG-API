package com.afterkraft.kraftrpg.api.entity.roles;

import java.util.Map;

import com.afterkraft.kraftrpg.api.Manager;

/**
 * @author gabizou
 */
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
}

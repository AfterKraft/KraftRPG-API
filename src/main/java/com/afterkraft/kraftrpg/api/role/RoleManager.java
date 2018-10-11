/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
import java.util.Optional;

import com.afterkraft.kraftrpg.api.CircularDependencyException;
import com.afterkraft.kraftrpg.api.role.Role.RoleType;
import com.afterkraft.kraftrpg.api.util.FixedPoint;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;

/**
 * Manages all interactions with roles and entities. Roles can be assigned, created, and swapped
 * with this manager.
 */
public interface RoleManager extends AdditionalCatalogRegistryModule<Role> {

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
    void setDefaultSecondaryRole(@Nullable Role role);


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

    void registerKeySupport(Key<?> key);

    boolean supportsKey(Key<?> key);
}

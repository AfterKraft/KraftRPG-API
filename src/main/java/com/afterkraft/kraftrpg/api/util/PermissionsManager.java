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
package com.afterkraft.kraftrpg.api.util;

import org.spongepowered.api.world.World;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.entity.Being;

/**
 * Standard permissions manager. This is provided to reduce dependency on Vault if and when
 * necessary. This is meant to be provided through {@link RpgCommon} and should be considered to be
 * used
 * instead of being managed by Vault.
 */
public interface PermissionsManager extends Manager {

    static final String ADMIN_INVENTORY_BYPASS_PERMISSION =
            "kraftrpg.admin.bypass.inventory";
    static final String ADMIN_NO_GLOBAL_COOLDOWN_PERMISSION =
            "kraftrpg.admin.bypass.cooldown";

    boolean isOp(final Being being);

    boolean hasPermission(final Being being, final String permission);

    boolean hasWorldPermission(final Being being, final World world,
                               final String permission);

    boolean hasWorldPermission(final Being being, final String worldName,
                               final String permission);

    void addGlobalPermission(final Being being, final String permission);

    void addWorldPermission(final Being being, final World world,
                            final String permission);

    void addWorldPermission(final Being being, final String worldName,
                            final String permission);

    void addTransientGlobalPermission(final Being being,
                                      final String permission);

    void addTransientWorldPermission(final Being being, final World world,
                                     final String permission);

    void addTransientWorldPermission(final Being being,
                                     final String worldName,
                                     final String permission);

    void removeGlobalPermission(final Being being, final String permission);

    void removeWorldPermission(final Being being, final World world,
                               final String permission);

    void removeWorldPermission(final Being being, final String worldName,
                               final String permission);

    void removeTransientGlobalPermission(final Being being,
                                         final String permission);

    void removeTransientWorldPermission(final Being being, final World world,
                                        final String permission);

    void removeTransientWorldPermission(final Being being,
                                        final String worldName,
                                        final String permission);

}
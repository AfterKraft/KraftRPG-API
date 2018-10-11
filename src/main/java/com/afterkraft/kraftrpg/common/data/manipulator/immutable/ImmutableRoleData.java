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
package com.afterkraft.kraftrpg.common.data.manipulator.immutable;

import java.util.Optional;

import com.afterkraft.kraftrpg.api.role.Role;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import com.afterkraft.kraftrpg.common.data.manipulator.mutable.RoleData;
import org.spongepowered.api.data.value.immutable.ImmutableListValue;
import org.spongepowered.api.data.value.immutable.ImmutableOptionalValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public interface ImmutableRoleData extends ImmutableDataManipulator<ImmutableRoleData, RoleData> {

    /**
     * Gets the {@link ImmutableValue} of the primary {@link Role}
     * as represented with {@link com.afterkraft.kraftrpg.api.RpgKeys#PRIMARY_ROLE}.
     *
     * @return
     */
    ImmutableValue<Role> primary();

    ImmutableOptionalValue<Role> secondary();

    ImmutableListValue<Role> additionalRoles();


    interface Builder extends DataBuilder<ImmutableRoleData> {

        ImmutableRoleData.Builder primary(ImmutableValue<Role> primary);

        ImmutableRoleData.Builder secondary(ImmutableValue<Role> secondary);

        ImmutableRoleData.Builder additionals(ImmutableListValue<Role> additionals);

        ImmutableRoleData build();

        @Override
        Optional<ImmutableRoleData> build(DataView container) throws InvalidDataException;

    }
}
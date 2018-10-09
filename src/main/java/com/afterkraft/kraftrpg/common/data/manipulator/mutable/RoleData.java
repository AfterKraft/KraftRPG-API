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
package com.afterkraft.kraftrpg.common.data.manipulator.mutable;

import com.afterkraft.kraftrpg.api.role.Role;
import com.afterkraft.kraftrpg.common.data.manipulator.immutable.ImmutableRoleData;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.DataManipulator;

import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.Value;


import java.util.List;
import java.util.Optional;

public interface RoleData extends DataManipulator<RoleData, ImmutableRoleData> {



    Value<Role> primary();

    Value<Role> secondary();

    ListValue<Role> additionals();


    void registerGettersAndSetters();


    Optional<RoleData> fill(DataHolder dataHolder, MergeFunction overlap);


    Optional<RoleData> from(DataContainer container);

    RoleData copy();

    ImmutableRoleData asImmutable();


    int compareTo(RoleData o);

    int getContentVersion();


    DataContainer toContainer();

    Role getPrimary();

    void setPrimary(Role primary);

    Role getSecondary();

    void setSecondary(Role secondary);

    List<Role> getAdditional();


    void setAdditional(List<Role> additional);

    @Override
    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}

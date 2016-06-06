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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.api.role.Role;
import com.afterkraft.kraftrpg.common.data.manipulator.immutable.ImmutableRoleData;

public final class RoleData extends AbstractData<RoleData, ImmutableRoleData> {

    private Role primary;
    private Role secondary;
    private List<Role> additional;

    public RoleData() {
        this(Role.DEFAULT_PRIMARY, Role.DEFAULT_SECONDARY);
    }

    public RoleData(Role primary, Role secondary) {
        this(primary, secondary, Collections.EMPTY_LIST);
    }

    public RoleData(Role primary, Role secondary, List<Role> additional) {
        this.primary = checkNotNull(primary, "primary");
        this.secondary = checkNotNull(secondary, "Secondary");
        this.additional = new ArrayList<>(additional);
        registerGettersAndSetters();
    }

    public Value<Role> primary() {
        return Sponge.getRegistry().getValueFactory().createValue(RpgKeys.PRIMARY_ROLE, this
                .primary, Role.DEFAULT_PRIMARY);
    }

    public Value<Role> secondary() {
        return Sponge.getRegistry().getValueFactory().createValue(RpgKeys.SECONDARY_ROLE,
                                                                  this.secondary,
                                                                  Role.DEFAULT_SECONDARY);
    }

    public ListValue<Role> additionals() {
        return Sponge.getRegistry().getValueFactory().createListValue(RpgKeys.ADDITIONAL_ROLES,
                                                                      this.additional);
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(RpgKeys.PRIMARY_ROLE, this::getPrimary);
        registerFieldSetter(RpgKeys.PRIMARY_ROLE, this::setPrimary);
        registerKeyValue(RpgKeys.PRIMARY_ROLE, this::primary);

        registerFieldGetter(RpgKeys.SECONDARY_ROLE, this::getSecondary);
        registerFieldSetter(RpgKeys.SECONDARY_ROLE, this::setSecondary);
        registerKeyValue(RpgKeys.SECONDARY_ROLE, this::secondary);

        registerFieldGetter(RpgKeys.ADDITIONAL_ROLES, this::getAdditional);
        registerFieldSetter(RpgKeys.ADDITIONAL_ROLES, this::setAdditional);
        registerKeyValue(RpgKeys.ADDITIONAL_ROLES, this::additionals);
    }

    @Override
    public Optional<RoleData> fill(DataHolder dataHolder, MergeFunction overlap) {
        final Optional<RoleData> roleData = dataHolder.get(RoleData.class);
        if (roleData.isPresent()) {
            final RoleData holderOne = roleData.get();
            final RoleData merged = overlap.merge(this, holderOne);
            setPrimary(checkNotNull(merged.primary));
            setSecondary(checkNotNull(merged.secondary));
            setAdditional(checkNotNull(merged.additional));
            return Optional.of(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<RoleData> from(DataContainer container) {
        if (container.contains(RpgKeys.PRIMARY_ROLE.getQuery(), RpgKeys.SECONDARY_ROLE.getQuery(),
                               RpgKeys.ADDITIONAL_ROLES.getQuery())) {
            final String primaryId = container.getString(RpgKeys.PRIMARY_ROLE.getQuery()).get();


        }
        return Optional.empty();
    }

    @Override
    public RoleData copy() {
        return new RoleData();
    }

    @Override
    public ImmutableRoleData asImmutable() {
        return null;
    }

    @Override
    public int compareTo(RoleData o) {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(RpgKeys.PRIMARY_ROLE.getQuery(), this.primary.getName())
                .set(RpgKeys.SECONDARY_ROLE.getQuery(), this.secondary.getName())
                .set(RpgKeys.ADDITIONAL_ROLES.getQuery(), this.additional.stream()
                    .map(Role::getName).collect(Collectors.toList()));
    }

    private Role getPrimary() {
        return this.primary;
    }

    private void setPrimary(Role primary) {
        this.primary = checkNotNull(primary, "primary");
    }

    private Role getSecondary() {
        return this.secondary;
    }

    private void setSecondary(Role secondary) {
        this.secondary = checkNotNull(secondary, "Secondary");
    }

    private List<Role> getAdditional() {
        return this.additional;
    }

    private void setAdditional(List<Role> additional) {
        this.additional = new ArrayList<>(additional);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("primary", this.primary)
                .add("secondary", this.secondary)
                .add("additionals", this.additional)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleData that = (RoleData) o;

        return Objects.equals(this.primary, that.primary) &&
                Objects.equals(this.secondary, that.secondary) &&
                Objects.equals(this.additional, that.additional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.primary, this.secondary, this.additional);
    }
}

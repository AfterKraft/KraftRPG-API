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
package com.afterkraft.kraftrpg.api.storage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.spongepowered.api.data.DataQuery.of;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.item.ItemType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.roles.Role;
import com.afterkraft.kraftrpg.api.skills.SkillBind;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * This class is not a stable API.
 */
public final class PlayerData implements DataSerializable, Cloneable {
    /**
     * Extra roles - these grant skills, but no hp/mana.
     */
    public final Set<Role> additionalRoles = Sets.newHashSet();
    /**
     * Inactive roles - these have experience numbers, but no benefits.
     */
    public final Set<Role> pastRoles = Sets.newHashSet();
    /**
     * Experience data.
     */
    public final Map<Role, FixedPoint> exp = Maps.newHashMap();
    /**
     * Raw bind data.
     */
    public final Map<ItemType, SkillBind> binds = Maps.newHashMap();
    /**
     * Map from cooldown name to expiry time, in UNIX time.
     */
    public final Map<String, Long> cooldowns = Maps.newHashMap();
    public final Set<String> ignoredSkills = Sets.newHashSet();
    /**
     * Main Roles.
     */
    public Role primary;
    public Role profession;
    public final Map<String, DataContainer> componentContainers = Maps.newConcurrentMap();
    public String lastKnownName;
    public UUID playerID;
    public final Map<String, Boolean> ignoring = Maps.newHashMap();

    private Collection<Role> allRoles = Sets.newHashSet();

    public PlayerData() {
    }

    @Override
    public final PlayerData clone() {
        try {
            PlayerData ret = (PlayerData) super.clone();

            ret.primary = this.primary;
            ret.profession = this.profession;
            ret.additionalRoles.addAll(this.additionalRoles);
            ret.pastRoles.addAll(this.pastRoles);
            ret.exp.putAll(this.exp);
            ret.binds.putAll(this.binds);
            ret.cooldowns.putAll(this.cooldowns);
            ret.lastKnownName = this.lastKnownName;
            ret.ignoredSkills.addAll(this.ignoredSkills);
            return ret;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return a collection of all active roles in the order Primary, Secondary, Additional. Past
     * roles are excluded.
     *
     * @return all active roles
     */
    public Collection<Role> getAllRoles() {
        if (this.allRoles != null) {
            return this.allRoles;
        }

        ImmutableList.Builder<Role> b = ImmutableList.builder();
        if (this.primary != null) {
            b.add(this.primary);
        }
        if (this.profession != null) {
            b.add(this.profession);
        }
        b.addAll(this.additionalRoles);
        this.allRoles = b.build();

        return this.allRoles;
    }

    @Override
    public DataContainer toContainer() {
        DataContainer container = new MemoryDataContainer();
        container.set(of("UUID"), this.playerID.toString());
        List<DataView> roles = Lists.newArrayList();
        for (Map.Entry<Role, FixedPoint> entry : this.exp.entrySet()) {
            DataContainer roleContainer = new MemoryDataContainer();
            roleContainer.set(of(entry.getKey().getName()), entry.getValue().longValue());
            roles.add(roleContainer);
        }
        container.set(of("Roles"), roles);
        List<DataView> cooldowns = Lists.newArrayList();
        for (Map.Entry<String, Long> entry : this.cooldowns.entrySet()) {
            DataContainer cooldownContainer = new MemoryDataContainer();
            cooldownContainer.set(of(entry.getKey()), entry.getValue());
        }
        container.set(of("Cooldowns"), cooldowns);
        container.set(of("SilcencedSkills"), this.ignoredSkills);
        container.set(of("PrimaryRole"), this.primary.getName());
        container.set(of("SecondaryRole"), this.profession != null ? this.profession.getName() :
                      "NONE");

        container.set(of("Components"), null);
        return container;
    }
}

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
package com.afterkraft.kraftrpg.api.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ImmutableList;

import org.bukkit.Material;

import com.afterkraft.kraftrpg.api.roles.Role;
import com.afterkraft.kraftrpg.api.skills.SkillBind;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * This class is not a stable API.
 */
public class PlayerData implements Cloneable {
    /**
     * Extra roles - these grant skills, but no hp/mana.
     */
    public final Set<Role> additionalRoles = new HashSet<Role>();
    /**
     * Inactive roles - these have experience numbers, but no benefits.
     */
    public final Set<Role> pastRoles = new HashSet<Role>();
    /**
     * Experience data.
     */
    public final Map<Role, FixedPoint> exp = new HashMap<Role, FixedPoint>();
    /**
     * Raw bind data.
     */
    public final Map<Material, SkillBind> binds = new HashMap<Material, SkillBind>();
    /**
     * Map from cooldown name to expiry time, in UNIX time.
     */
    public final Map<String, Long> cooldowns = new HashMap<String, Long>();
    /**
     * Main Roles.
     */
    public Role primary, profession;
    public int currentMana;
    public String lastKnownName;
    public UUID playerID;

    private Collection<Role> allRoles = null;

    public PlayerData() {
    }

    @Override
    public PlayerData clone() {
        PlayerData ret = new PlayerData();

        ret.primary = this.primary;
        ret.profession = this.profession;
        ret.additionalRoles.addAll(this.additionalRoles);
        ret.pastRoles.addAll(this.pastRoles);
        ret.exp.putAll(this.exp);
        ret.binds.putAll(this.binds);
        ret.cooldowns.putAll(this.cooldowns);
        ret.lastKnownName = this.lastKnownName;
        ret.currentMana = this.currentMana;

        return ret;
    }

    /**
     * Return a collection of all active roles in the order Primary,
     * Secondary, Additional. Past roles are excluded.
     *
     * @return all active roles
     */
    public Collection<Role> allRoles() {
        if (this.allRoles != null) return this.allRoles;

        ImmutableList.Builder<Role> b = ImmutableList.builder();
        if (this.primary != null) b.add(this.primary);
        if (this.profession != null) b.add(this.profession);
        b.addAll(this.additionalRoles);
        this.allRoles = b.build();

        return this.allRoles;
    }
}

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

import com.google.common.collect.ImmutableList;

import org.bukkit.Material;

import com.afterkraft.kraftrpg.api.entity.roles.Role;
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
    public String lastKnownName;
    private Collection<Role> allRoles = null;

    public PlayerData() {
    }

    public PlayerData clone() {
        PlayerData ret = new PlayerData();

        ret.primary = primary;
        ret.profession = profession;
        ret.additionalRoles.addAll(additionalRoles);
        ret.exp.putAll(exp);
        ret.binds.putAll(binds);
        ret.cooldowns.putAll(cooldowns);
        ret.lastKnownName = lastKnownName;

        return ret;
    }

    public Collection<Role> allRoles() {
        if (allRoles != null) return allRoles;

        ImmutableList.Builder<Role> b = ImmutableList.<Role> builder();
        if (primary != null) b.add(primary);
        if (profession != null) b.add(profession);
        b.addAll(additionalRoles);
        allRoles = b.build();

        return allRoles;
    }
}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
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
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.item.ItemType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.roles.Role;
import com.afterkraft.kraftrpg.api.skills.SkillBind;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * This class is not a stable API.
 */
public final class PlayerData implements Cloneable {
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
    public int currentMana;
    public int maxStamina;
    public int currentStamina;
    public String lastKnownName;
    public UUID playerID;
    public boolean displayPrimaryRole;
    public boolean isManaVerbose;
    public boolean isStaminaVerbose;
    public boolean isSkillVerbose;

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
            ret.currentMana = this.currentMana;
            ret.currentStamina = this.currentStamina;
            ret.maxStamina = this.maxStamina;
            ret.isManaVerbose = this.isManaVerbose;
            ret.isSkillVerbose = this.isSkillVerbose;
            ret.isStaminaVerbose = this.isStaminaVerbose;
            ret.displayPrimaryRole = this.displayPrimaryRole;
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
}

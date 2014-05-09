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
package com.afterkraft.kraftrpg.api.entity.roles;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.ISkill;


/**
 * A Role is a single node in a tree-like structure for granting Skills and other
 * bonuses to {@link SkillCaster}s.
 */
public class Role {
    protected final RPGPlugin plugin;
    protected final String name;
    protected final RoleType type;
    private final TreeMap<RoleSkill, ConfigurationSection> skills = new TreeMap<RoleSkill, ConfigurationSection>();
    private final Map<Material, Double> itemDamages = new EnumMap<Material, Double>(Material.class);
    private final Map<Material, Double> itemDamagePerLevel = new EnumMap<Material, Double>(Material.class);

    public Role(RPGPlugin plugin, String name, RoleType type) {
        this.plugin = plugin;
        this.name = name;
        this.type = type;
    }

    /**
     * Get the type of Role this is.
     *
     * @return the {@link com.afterkraft.kraftrpg.api.entity.roles.RoleType}
     */
    public final RoleType getType() {
        return this.type;
    }

    /**
     * Return the configured name for this Role
     *
     * @return the name for this role
     */
    public String getName() {
        return this.name;
    }

    public ConfigurationSection getSkillConfigIfAvailable(ISkill skill, int level) {
        NavigableMap<RoleSkill, ConfigurationSection> subMap = skills.headMap(new RoleSkill(skill, level), true);
        for (RoleSkill rs : subMap.descendingKeySet()) {
            if (rs.skillEquals(skill)) {
                return subMap.get(rs);
            }
        }
        return null;
    }

    public boolean hasSkillAtLevel(ISkill skill, int level) {
        return getSkillConfigIfAvailable(skill, level) != null;
    }

    public boolean hasSkillEver(ISkill skill) {
        for (RoleSkill rs : skills.keySet()) {
            if (rs.skillEquals(skill)) {
                return true;
            }
        }
        return false;
    }

    public Set<ISkill> getAllSkills() {
        Set<ISkill> ret = new HashSet<ISkill>();

        for (RoleSkill rs : skills.keySet()) {
            ret.add(rs.getSkill());
        }

        return ret;
    }

    public Set<ISkill> getAllSkillsAtLevel(int level) {
        NavigableMap<RoleSkill, ConfigurationSection> subMap = skills.headMap(new RoleSkill(null, level), true);
        Set<ISkill> ret = new HashSet<ISkill>();
        for (RoleSkill rs : subMap.descendingKeySet()) {
            ret.add(rs.getSkill());
        }

        return ret;
    }

    public double getItemDamage(Material type) {
        return this.itemDamages.get(type) != null ? this.itemDamages.get(type) : 0.0D;
    }

    public void setItemDamage(Material type, double damage) {
        this.itemDamages.put(type, damage);
    }

    public double getItemDamagePerLevel(Material type) {
        return this.itemDamagePerLevel.get(type) != null ? this.itemDamagePerLevel.get(type) : 0.0D;
    }

    public void setItemDamagePerLevel(Material type, double damage) {
        this.itemDamagePerLevel.put(type, damage);
    }

}

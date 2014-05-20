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

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;


/**
 * A Role is a single node in a tree-like structure for granting Skills and
 * other bonuses to {@link SkillCaster}s.
 */
public class Role {
    protected final RPGPlugin plugin;
    protected final String name;
    protected final RoleType type;
    private final Map<ISkill, RoleSkill> skills = new HashMap<ISkill, RoleSkill>();
    private final Map<Material, Double> itemDamages = new EnumMap<Material, Double>(Material.class);
    private final Map<Material, Double> itemDamagePerLevel = new EnumMap<Material, Double>(Material.class);
    private final Set<Role> children = new HashSet<Role>();
    private final Set<Role> parents = new HashSet<Role>();
    private boolean choosable = true;

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

    public void addSkill(ISkill skill, ConfigurationSection config) {
        if (config.getInt(SkillSetting.LEVEL.node(), -1) == -1) {
            throw new IllegalArgumentException("Level not specified in configuration");
        }
        RoleSkill rs = new RoleSkill(skill, config);
        skills.put(skill, rs);
    }

    public boolean hasSkillAtLevel(ISkill skill, int level) {
        RoleSkill rs = skills.get(skill);

        return rs != null && rs.getLevel() <= level;
    }

    public ConfigurationSection getSkillConfigIfAvailable(ISkill skill, int level) {
        RoleSkill rs = skills.get(skill);
        if (rs == null) return null;

        if (rs.getLevel() <= level) {
            return rs.getConfig();
        }
        return null;
    }

    public boolean hasSkillEver(ISkill skill) {
        return skills.get(skill) != null;
    }

    public int getLevelRequired(ISkill skill) {
        RoleSkill rs = skills.get(skill);
        if (rs == null) {
            return -1;
        }
        return rs.getLevel();
    }

    public Set<ISkill> getAllSkills() {
        return skills.keySet();
    }

    public Set<ISkill> getAllSkillsAtLevel(int level) {
        Set<ISkill> ret = new HashSet<ISkill>();
        for (Map.Entry<ISkill, RoleSkill> entry : skills.entrySet()) {
            if (entry.getValue().getLevel() <= level) {
                ret.add(entry.getKey());
            }
        }
        return ret;
    }

    public void addParent(Role newParent) {
        if (newParent == null) {
            return;
        }
        parents.add(newParent);
        if (plugin.getRoleManager().areRoleDependenciesCyclic()) {
            parents.remove(newParent);
        }
    }

    public void removeParent(Role parent) {
        if (parent == null) {
            return;
        }
        this.parents.remove(parent);
    }

    public Set<Role> getParents() {
        return ImmutableSet.copyOf(parents);
    }

    public void addChild(Role newChild) {
        if (newChild == null) {
            return;
        }
        parents.add(newChild);
    }

    public void removeChild(Role child) {
        if (child == null) {
            return;
        }
        this.parents.remove(child);
    }

    public Set<Role> getChildren() {
        return ImmutableSet.copyOf(children);
    }


    /**
     * Check if this role can by chosen by players. A non-choosable role must
     * be granted through some means other than role tree advancement.
     * 
     * @return can be chosen
     */
    public boolean isChoosable() {
        return choosable;
    }

    public void setChoosable(boolean canChoose) {
        choosable = canChoose;
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

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
package com.afterkraft.kraftrpg.api.roles;

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
    private final Map<ISkill, RoleSkill> skills = new HashMap<ISkill, RoleSkill>();
    private final Map<Material, Double> itemDamages = new EnumMap<Material, Double>(Material.class);
    private final Map<Material, Double> itemDamagePerLevel = new EnumMap<Material, Double>(Material.class);
    private final Set<Role> children = new HashSet<Role>();
    private final Set<Role> parents = new HashSet<Role>();
    private RoleType type;
    private int advancementLevel;
    private boolean choosable = true;
    private String description;
    private double hpAt0, hpPerLevel, mpAt0, mpPerLevel, mpRegenAt0,
            mpRegenPerLevel;

    public Role(RPGPlugin plugin, String name, RoleType type) {
        this.plugin = plugin;
        this.name = name;
        this.type = type;
    }

    /**
     * Get the type of Role this is.
     * 
     * @return the {@link com.afterkraft.kraftrpg.api.roles.RoleType}
     */
    public final RoleType getType() {
        return this.type;
    }

    public void setType(RoleType type) {
        this.type = type;
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
        if (plugin.getRoleManager().addRoleDependency(newParent, this)) {
            parents.add(newParent);
        }
    }

    public void removeParent(Role parent) {
        if (parent == null) {
            return;
        }
        plugin.getRoleManager().removeRoleDependency(parent, this);
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

    public boolean isDefault() {
        if (type == RoleType.PRIMARY) {
            return this == plugin.getRoleManager().getDefaultPrimaryRole();
        } else if (type == RoleType.SECONDARY) {
            return this == plugin.getRoleManager().getDefaultSecondaryRole();
        }
        return false;
    }

    public void setHpMpProperties(double hpAt0, double hpPerLevel, double mpAt0, double mpPerLevel, double mpRegenAt0, double mpRegenPerLevel) {
        this.hpAt0 = hpAt0;
        this.hpPerLevel = hpPerLevel;
        this.mpAt0 = mpAt0;
        this.mpPerLevel = mpPerLevel;
        this.mpRegenAt0 = mpRegenAt0;
        this.mpRegenPerLevel = mpRegenPerLevel;
    }

    public double getHp(int level) {
        return hpAt0 + hpPerLevel * level;
    }

    public int getMp(int level) {
        return (int) (mpAt0 + mpPerLevel * level);
    }

    public double getMpRegen(int level) {
        return mpRegenAt0 + mpRegenPerLevel * level;
    }

    public double getHpAt0() {
        return hpAt0;
    }

    public void setHpAt0(double hpAt0) {
        this.hpAt0 = hpAt0;
    }

    public double getHpPerLevel() {
        return hpPerLevel;
    }

    public void setHpPerLevel(double hpPerLevel) {
        this.hpPerLevel = hpPerLevel;
    }

    public double getMpAt0() {
        return mpAt0;
    }

    public void setMpAt0(double mpAt0) {
        this.mpAt0 = mpAt0;
    }

    public double getMpPerLevel() {
        return mpPerLevel;
    }

    public void setMpPerLevel(double mpPerLevel) {
        this.mpPerLevel = mpPerLevel;
    }

    public double getMpRegenAt0() {
        return mpRegenAt0;
    }

    public void setMpRegenAt0(double mpRegenAt0) {
        this.mpRegenAt0 = mpRegenAt0;
    }

    public double getMpRegenPerLevel() {
        return mpRegenPerLevel;
    }

    public void setMpRegenPerLevel(double mpRegenPerLevel) {
        this.mpRegenPerLevel = mpRegenPerLevel;
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

    public int getAdvancementLevel() {
        return advancementLevel;
    }

    public void setAdvancementLevel(int advancementLevel) {
        this.advancementLevel = advancementLevel;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

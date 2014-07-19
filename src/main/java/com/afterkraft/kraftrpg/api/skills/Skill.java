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
package com.afterkraft.kraftrpg.api.skills;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler;

/**
 * Represents an intended implementation of ISkill.
 */
public abstract class Skill implements ISkill {

    public final RPGPlugin plugin;
    private final Map<SkillSetting, Object> settings = new HashMap<SkillSetting, Object>();
    private final Set<SkillType> skillTypes = EnumSet.noneOf(SkillType.class);
    private final String name;
    private String description = "";
    private boolean isEnabled = false;
    private ConfigurationSection defaultConfig;

    public Skill(RPGPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    // Configuration Defaults

    public static void knockback(LivingEntity target, LivingEntity attacker, double damage) {
        CraftBukkitHandler.getInterface().knockBack(target, attacker, damage);
    }

    public static boolean damageEntity(ISkill skill, LivingEntity target, LivingEntity attacker, double damage) {
        return damageEntity(target, attacker, damage, skill.isType(SkillType.ABILITY_PROPERTY_AIR) || skill.isType(SkillType.ABILITY_PROPERTY_PHYSICAL) ? DamageCause.ENTITY_ATTACK : DamageCause.MAGIC);
    }

    public static boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, DamageCause cause) {
        return damageEntity(target, attacker, damage, cause, true);
    }

    public static boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, DamageCause cause, boolean knockback) {
        return CraftBukkitHandler.getInterface().damageEntity(target, attacker, damage, cause, knockback);
    }

    public static boolean damageEntity(LivingEntity target, SkillCaster attacker, double damage, DamageCause cause) {
        return damageEntity(target, attacker.getEntity(), damage, cause, true);
    }

    /**
     * Transform the name of a skill to a normal form. The results of this
     * method should not be compared with anything other than other results of
     * this method.
     * 
     * @param skillName skill.getName() to check
     * @return normalized name
     */
    public static String getNormalizedName(String skillName) {
        return skillName.toLowerCase().replace("skill", "");
    }

    protected void setDefault(SkillSetting node, boolean value) {
        if (!SkillSetting.BOOLEAN_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set boolean default of a non-boolean SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
    }

    protected void setDefault(SkillSetting node, double value) {
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        if (node.scalingNode() != null) {
            section.set(node.scalingNode(), 0);
        }
    }

    protected void setDefault(SkillSetting node, double value, double valuePerLevel) {
        if (node.scalingNode() == null) {
            throw new IllegalArgumentException("Attempt to set scaling default of a non-scaling SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        section.set(node.scalingNode(), valuePerLevel);
    }

    protected void setDefault(SkillSetting node, String value) {
        if (!SkillSetting.STRING_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set string default of a non-string SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
    }

    protected void setDefault(SkillSetting node, ItemStack value) {
        if (node != SkillSetting.REAGENT) {
            throw new IllegalArgumentException("Attempt to set item default of a non-item SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
    }

    /**
     * Return whether this Skill is enabled or not
     * 
     * @return whether this skill is enabled
     */
    public final boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * Sets the enabled state of this Skill
     * 
     * @param enabled whether or not to set this skill as enabled or not
     */
    public final void setEnabled(final boolean enabled) {
        if (isEnabled != enabled) {
            isEnabled = enabled;

            if (isEnabled) {
                initialize();
            } else {
                shutdown();
            }
        }
    }

    @Override
    public final String getPermissionNode() {
        return "kraftrpg.skill." + this.getName();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    public ConfigurationSection getDefaultConfig() {
        if (defaultConfig == null) {
            defaultConfig = new MemoryConfiguration();
        }
        return defaultConfig;
    }

    @Override
    public final String getDescription() {
        return this.description;
    }

    @Override
    public final void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean addSkillTarget(Entity entity, SkillCaster caster) {
        if (entity == null || caster == null || caster.isEntityValid()) {
            return false;
        }
        plugin.getSkillManager().addSkillTarget(entity, caster, this);
        return true;
    }

    @Override
    public final boolean isType(SkillType type) {
        return this.skillTypes.contains(type);
    }

    @Override
    public boolean isInMessageRange(SkillCaster broadcaster, Champion receiver) {
        return broadcaster.getLocation().distanceSquared(receiver.getLocation()) < (20 * 20);
    }

    @Override
    public boolean damageCheck(Insentient attacking, LivingEntity defenderLE) {
        if (attacking == null || attacking.getEntity() == null || defenderLE == null) {
            return false;
        }
        if (attacking.getEntity().equals(defenderLE)) {
            return false;
        }
        if (defenderLE instanceof Player && attacking instanceof Champion) {
            if (!attacking.getWorld().getPVP()) {
                attacking.sendMessage(ChatColor.RED + "PVP is disabled!");
                return false;
            }
        }
        // Create the base map for the damage event.
        Map<DamageModifier, Double> damage = new HashMap<DamageModifier, Double>();
        damage.put(DamageModifier.BASE, 1D);

        EntityDamageByEntityEvent damageEntityEvent = new EntityDamageByEntityEvent(attacking.getEntity(), defenderLE, DamageCause.CUSTOM, damage);
        Bukkit.getServer().getPluginManager().callEvent(damageEntityEvent);

        return damageEntityEvent.isCancelled();
    }

    public final boolean needsConfiguredCustomData() {
        return getUsedConfigNodes().contains(SkillSetting.CUSTOM);
    }

    public boolean needsCustomDataStorage() {
        return getUsedConfigNodes().contains(SkillSetting.CUSTOM_PER_CHAMPION);
    }

    /**
     * Set this Skill's skill types.
     * 
     * @param types the SkillTypes to set
     */
    protected final void setSkillTypes(SkillType... types) {
        this.skillTypes.addAll(Arrays.asList(types));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        if (!(obj instanceof Skill)) return false;
        Skill other = (Skill) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }
}

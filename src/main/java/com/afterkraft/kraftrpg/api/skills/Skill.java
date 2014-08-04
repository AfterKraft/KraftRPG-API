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
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang.Validate;

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
    private Set<SkillSetting> usedSettings = new HashSet<SkillSetting>();
    private Set<String> usedNodes = new HashSet<String>();

    protected Skill(RPGPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

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

    /**
     * Attempts to damage the defending LivingEntity, this allows for various
     * protection plugins to cancel damage events.
     *
     * @param attacking attempting to deal the damage
     * @param defenderLE entity being damaged
     * @return true if the damage check was successful
     */
    public static boolean damageCheck(Insentient attacking, LivingEntity defenderLE) {
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

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, Object value) {
        Validate.notNull(node, "Cannot set a default null node!");
        Validate.notNull(value, "Cannot set a default null value!");
        if (node.getClass().equals(SkillSetting.class) && !SkillSetting.LIST_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set string default of a non-string SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, Object value) {
        Validate.notNull(node, "Cannot set a default null node!");
        Validate.notNull(value, "Cannot set a default null value!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, boolean value) {
        Validate.notNull(node, "Cannot set a default null node!");
        if (node.getClass().equals(SkillSetting.class) && !SkillSetting.BOOLEAN_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set boolean default of a non-boolean SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, boolean value) {
        Validate.notNull(node, "Cannot set a default null node!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, double value) {
        Validate.notNull(node, "Cannot set a default null node!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
        if (node.scalingNode() != null) {
            section.set(node.scalingNode(), 0);
        }
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, double value) {
        Validate.notNull(node, "Cannot set a default null node!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, double value, double valuePerLevel) {
        Validate.notNull(node, "Cannot set a default null node!");
        if (node.scalingNode() == null) {
            throw new IllegalArgumentException("Attempt to set scaling default of a non-scaling SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        section.set(node.scalingNode(), valuePerLevel);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, double value, double valuePerLevel) {
        Validate.notNull(node, "Cannot set a default null node!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        section.set(node + "-per-level", valuePerLevel);
        this.usedNodes.add(node);
        this.usedNodes.add(node + "-per-level");
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, String value) {
        Validate.notNull(node, "Cannot set a default null node!");
        Validate.notNull(value, "Cannot set a default null value!");
        if (node.getClass().equals(SkillSetting.class) && !SkillSetting.STRING_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set string default of a non-string SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, String value) {
        Validate.notNull(node, "Cannot set a default null node!");
        Validate.notNull(value, "Cannot set a default null value!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }



    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, List<?> value) {
        Validate.notNull(node, "Cannot set a default null node!");
        Validate.notNull(value, "Cannot set a default null value!");
        if (node.getClass().equals(SkillSetting.class) && !SkillSetting.LIST_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set string default of a non-list SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, List<?> value) {
        Validate.notNull(node, "Cannot set a default null node!");
        Validate.notNull(value, "Cannot set a default null value!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided SkillSetting node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, ItemStack value) {
        Validate.notNull(node, "Cannot set a default null node!");
        Validate.notNull(value, "Cannot set a default null ItemStack!");
        if (node.getClass().equals(SkillSetting.class) && node != SkillSetting.REAGENT) {
            throw new IllegalArgumentException("Attempt to set item default of a non-item SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), new ItemStack(value));
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically
     * registers the provided String node to the used settings.
     * This is useful for not having to override {@link #getDefaultConfig()}
     * and {@link #getUsedConfigNodes()}
     *
     * @param node The skill setting node
     * @param value The value to set the setting at
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, ItemStack value) {
        Validate.notNull(node, "Cannot set a default null node!");
        Validate.notNull(value, "Cannot set a default null ItemStack!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, new ItemStack(value));
        this.usedNodes.add(node);
    }

    @Override
    public Collection<SkillSetting> getUsedConfigNodes() {
        for (String string : this.usedNodes) {
            this.usedSettings.add(new SkillSetting(string));
        }
        return Sets.newHashSet(this.usedSettings);
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
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;

            if (this.isEnabled) {
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

    @Override
    public ConfigurationSection getDefaultConfig() {
        if (this.defaultConfig == null) {
            this.defaultConfig = new MemoryConfiguration();
        }
        return this.defaultConfig;
    }

    @Override
    public final String getDescription() {
        return this.description;
    }

    @Override
    public final void setDescription(String description) {
        Validate.notNull(description, "Cannot set the description to null!");
        this.description = description;
    }

    @Override
    public boolean addSkillTarget(Entity entity, SkillCaster caster) {
        Validate.notNull(entity, "Cannot add a null entity skill target!");
        Validate.notNull(caster, "Cannot add a null caster targetting an entity!");
        this.plugin.getSkillManager().addSkillTarget(entity, caster, this);
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
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        if (!(obj instanceof Skill)) return false;
        Skill other = (Skill) obj;
        if (this.name == null) {
            if (other.name != null) return false;
        } else if (!this.name.equals(other.name)) return false;
        return true;
    }
}

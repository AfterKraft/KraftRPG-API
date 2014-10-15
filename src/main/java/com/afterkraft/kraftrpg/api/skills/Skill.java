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
package com.afterkraft.kraftrpg.api.skills;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

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

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.events.entity.damage.InsentientDamageEvent;
import com.afterkraft.kraftrpg.api.events.entity.damage.InsentientDamageEvent.DamageType;
import com.afterkraft.kraftrpg.api.handler.ServerInternals;

/**
 * Represents an intended implementation of ISkill.
 */
public abstract class Skill implements ISkill {

    private static final Function<? super Double, Double> ZERO = Functions.constant(-0.0);


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

    public static void knockback(Insentient target, Insentient attacker, double damage) {
        knockback(target.getEntity(), attacker.getEntity(), damage);
    }

    public static void knockback(LivingEntity target, LivingEntity attacker, double damage) {
        ServerInternals.getInterface().knockBack(target, attacker, damage);
    }

    public static boolean damageEntity(LivingEntity target, SkillCaster attacker, ISkill skill,
                                       double damage, DamageCause cause) {
        return damageEntity(target, attacker, skill, damage, cause, true);
    }

    public static boolean damageEntity(LivingEntity target, SkillCaster attacker, ISkill skill,
                                       double damage, DamageCause cause, boolean knockback) {
        return damageEntity(target, attacker, skill, damage, cause, knockback, false);
    }

    public static boolean damageEntity(LivingEntity target, SkillCaster attacker, ISkill skill,
                                       double damage, DamageCause cause, boolean knockback,
                                       boolean ignoreDamageCheck) {
        return ServerInternals.getInterface().damageEntity(target, attacker, skill, damage,
                cause, knockback);
    }

    public static boolean damageEntity(Insentient target, SkillCaster attacker, ISkill skill,
                                       double damage, DamageCause cause) {
        return damageEntity(target, attacker, skill, damage, cause, true);
    }

    public static boolean damageEntity(Insentient target, SkillCaster attacker, ISkill skill,
                                       double damage, DamageCause cause, boolean knockback) {
        return damageEntity(target, attacker, skill, damage, cause, knockback, false);
    }

    public static boolean damageEntity(Insentient target, SkillCaster attacker, ISkill skill,
                                       double damage, DamageCause cause, boolean knockback,
                                       boolean ignoreDamageCheck) {
        Map<InsentientDamageEvent.DamageType, Double> modifiers =
                new EnumMap<InsentientDamageEvent.DamageType, Double>(
                        ImmutableMap.of(DamageType.PHYSICAL, damage));
        return damageEntity(target, attacker, skill, modifiers, cause, knockback,
                ignoreDamageCheck);
    }

    public static boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                       Map<DamageType, Double> modifiers, DamageCause cause,
                                       boolean knockback, boolean ignoreDamageCheck) {
        return ServerInternals.getInterface().damageEntity(target, attacker, skill, modifiers,
                cause, knockback);
    }

    public static boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                       Map<DamageType, Double> modifiers, DamageCause cause) {
        return damageEntity(target, attacker, skill, modifiers, cause, true);
    }

    public static boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                       Map<DamageType, Double> modifiers, DamageCause cause,
                                       boolean knockback) {
        return damageEntity(target, attacker, skill, modifiers, cause, knockback, false);
    }

    /**
     * Transform the name of a skill to a normal form. The results of this method should not be
     * compared with anything other than other results of this method.
     *
     * @param skillName skill.getName() to check
     *
     * @return normalized name
     */
    public static String getNormalizedName(String skillName) {
        return skillName.toLowerCase().replace("skill", "");
    }

    public static boolean damageCheck(Insentient attacking, Insentient victim) {
        return damageCheck(attacking, victim.getEntity());
    }

    /**
     * Attempts to damage the defending LivingEntity, this allows for various protection plugins to
     * cancel damage events.
     *
     * @param attacking  attempting to deal the damage
     * @param defenderLE entity being damaged
     *
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
        EntityDamageByEntityEvent damageEntityEvent =
                new EntityDamageByEntityEvent(attacking.getEntity(), defenderLE,
                        DamageCause.CUSTOM, new EnumMap<DamageModifier, Double>(
                        ImmutableMap.of(DamageModifier.BASE, 1.0D)),
                        new EnumMap<DamageModifier, Function<? super Double, Double>>(
                                ImmutableMap.of(DamageModifier.BASE, ZERO)));
        Bukkit.getServer().getPluginManager().callEvent(damageEntityEvent);

        return damageEntityEvent.isCancelled();
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, Object value) {
        checkArgument(node != null, "Cannot set a default null node!");
        checkArgument(value != null, "Cannot set a default null value!");
        if (node.getClass().equals(SkillSetting.class)
                && !SkillSetting.LIST_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set string default "
                    + "of a non-string SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, Object value) {
        checkArgument(node != null, "Cannot set a default null node!");
        checkArgument(value != null, "Cannot set a default null value!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, boolean value) {
        checkArgument(node != null, "Cannot set a default null node!");
        if (node.getClass().equals(SkillSetting.class)
                && !SkillSetting.BOOLEAN_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set boolean "
                    + "default of a non-boolean SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, boolean value) {
        checkArgument(node != null, "Cannot set a default null node!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, double value) {
        checkArgument(node != null, "Cannot set a default null node!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
        if (node.scalingNode() != null) {
            section.set(node.scalingNode(), 0);
        }
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, double value) {
        checkArgument(node != null, "Cannot set a default null node!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, double value, double valuePerLevel) {
        checkArgument(node != null, "Cannot set a default null node!");
        if (node.scalingNode() == null) {
            throw new IllegalArgumentException("Attempt to set scaling default of "
                    + "a non-scaling SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        section.set(node.scalingNode(), valuePerLevel);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, double value, double valuePerLevel) {
        checkArgument(node != null, "Cannot set a default null node!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        section.set(node + "-per-level", valuePerLevel);
        this.usedNodes.add(node);
        this.usedNodes.add(node + "-per-level");
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, String value) {
        checkArgument(node != null, "Cannot set a default null node!");
        checkArgument(value != null, "Cannot set a default null value!");
        if (node.getClass().equals(SkillSetting.class)
                && !SkillSetting.STRING_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set string default of "
                    + "a non-string SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, String value) {
        checkArgument(node != null, "Cannot set a default null node!");
        checkArgument(value != null, "Cannot set a default null value!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }


    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, List<?> value) {
        checkArgument(node != null, "Cannot set a default null node!");
        checkArgument(value != null, "Cannot set a default null value!");
        if (node.getClass().equals(SkillSetting.class)
                && !SkillSetting.LIST_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set string default of "
                    + "a non-list SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), value);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, List<?> value) {
        checkArgument(node != null, "Cannot set a default null node!");
        checkArgument(value != null, "Cannot set a default null value!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, value);
        this.usedNodes.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(SkillSetting node, ItemStack value) {
        checkArgument(node != null, "Cannot set a default null node!");
        checkArgument(value != null, "Cannot set a default null ItemStack!");
        if (node.getClass().equals(SkillSetting.class) && node != SkillSetting.REAGENT) {
            throw new IllegalArgumentException("Attempt to set item default of "
                    + "a non-item SkillSetting");
        }
        ConfigurationSection section = getDefaultConfig();
        section.set(node.node(), new ItemStack(value));
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * String node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node  The skill setting node
     * @param value The value to set the setting at
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected void setDefault(String node, ItemStack value) {
        checkArgument(node != null, "Cannot set a default null node!");
        checkArgument(value != null, "Cannot set a default null ItemStack!");
        ConfigurationSection section = getDefaultConfig();
        section.set(node, new ItemStack(value));
        this.usedNodes.add(node);
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
    public Collection<SkillSetting> getUsedConfigNodes() {
        for (String string : this.usedNodes) {
            this.usedSettings.add(new SkillSetting(string));
        }
        return Sets.newHashSet(this.usedSettings);
    }

    @Override
    public final String getDescription() {
        return this.description;
    }

    @Override
    public final void setDescription(String description) {
        checkArgument(description != null, "Cannot set the description to null!");
        this.description = description;
    }

    @Override
    public boolean addSkillTarget(Entity entity, SkillCaster caster) {
        checkArgument(entity != null, "Cannot add a null entity skill target!");
        checkArgument(caster != null, "Cannot add a null caster targetting an entity!");
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!(obj instanceof Skill)) {
            return false;
        }
        Skill other = (Skill) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}

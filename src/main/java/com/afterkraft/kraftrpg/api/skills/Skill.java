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
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.entity.roles.ExperienceType;
import com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * Represents an intended implementation of ISkill.
 */
public abstract class Skill implements ISkill {

    public final RPGPlugin plugin;
    private final Map<SkillSetting, Object> settings = new HashMap<SkillSetting, Object>();
    private final Set<SkillType> skillTypes = EnumSet.noneOf(SkillType.class);
    private final EnumSet<SkillSetting> usedConfigNodes = EnumSet.noneOf(SkillSetting.class);
    private final String name;
    private String description = "";
    private boolean isEnabled = false;

    public Skill(RPGPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
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

    @Override
    public final String getDescription() {
        return this.description;
    }

    @Override
    public final void setDescription(String description) {
        this.description = description;
    }

    public EnumSet<SkillSetting> getUsedConfigNodes() {
        return usedConfigNodes;
    }

    public final boolean needsConfiguredCustomData() {
        return usedConfigNodes.contains(SkillSetting.CUSTOM);
    }

    public boolean needsCustomDataStorage() {
        return usedConfigNodes.contains(SkillSetting.CUSTOM_PER_CHAMPION);
    }

    @Override
    public boolean addSkillTarget(Entity entity, SkillCaster caster) {
        if (entity == null || caster == null || (caster instanceof IEntity && !((IEntity) caster).isEntityValid())) {
            return false;
        }
        plugin.getSkillManager().addSkillTarget(entity, caster, this);
        return true;
    }

    @Override
    public final boolean isType(SkillType type) {
        return this.skillTypes.contains(type);
    }

    public static final void knockback(LivingEntity target, LivingEntity attacker, double damage) {
        CraftBukkitHandler.getInterface().knockBack(target, attacker, damage);
    }

    public static final boolean damageEntity(ISkill skill, LivingEntity target, LivingEntity attacker, double damage) {
        return damageEntity(target, attacker, damage, skill.isType(SkillType.ABILITY_PROPERTY_AIR) || skill.isType(SkillType.ABILITY_PROPERTY_PHYSICAL) ? EntityDamageEvent.DamageCause.ENTITY_ATTACK : EntityDamageEvent.DamageCause.MAGIC);
    }

    public static final boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause) {
        return damageEntity(target, attacker, damage, cause, true);
    }

    public static final boolean damageEntity(LivingEntity target, SkillCaster attacker, double damage, EntityDamageEvent.DamageCause cause) {
        return damageEntity(target, attacker.getEntity(), damage, cause, true);
    }

    public static final boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause, boolean knockback) {
        return CraftBukkitHandler.getInterface().damageEntity(target, attacker, damage, cause, knockback);
    }

    @Override
    public boolean damageCheck(IEntity attacking, LivingEntity defenderLE) {
        if (attacking.getEntity().equals(defenderLE)) {
            return false;
        }

        EntityDamageByEntityEvent damageEntityEvent = new EntityDamageByEntityEvent(attacking.getEntity(), defenderLE, EntityDamageEvent.DamageCause.CUSTOM, 0D);
        Bukkit.getServer().getPluginManager().callEvent(damageEntityEvent);

        return damageEntityEvent.isCancelled();
    }

    @Override
    public void awardExperience(SkillCaster caster) {
        if (caster.canGainExperience(ExperienceType.SKILL)) {
            caster.gainExperience(FixedPoint.valueOf(plugin.getSkillConfigManager().getUseSetting(caster, this, SkillSetting.EXP, 0, false)), ExperienceType.SKILL, caster.getLocation());
        }
    }

    @Override
    public boolean isInMessageRange(Champion broadcaster, Champion receiver) {
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(ISkill other) {
        return equals((Object) other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Skill other = (Skill) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }
}

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
package com.afterkraft.kraftrpg.api.skills.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.Validate;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.Skill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;

/**
 * PermissionSkill allows setting any other Plugin's permission to become a
 * Skill through permission restrictions. A PermissionSkill does not allow any
 * type of SkillArguments and it will always return true on Skill use.
 * <p/>
 * PermissionSkill can apply Permissions with both true and false
 */
public final class PermissionSkill extends Skill implements Permissible {
    private Map<String, Boolean> permissions;
    private Permission permission;

    public PermissionSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public Configuration getDefaultConfig() {
        return null;
    }

    @Override
    public boolean addSkillTarget(Entity entity, SkillCaster caster) {
        return true;
    }

    @Override
    public boolean isInMessageRange(SkillCaster broadcaster, Champion receiver) {
        return false;
    }

    @Override
    public Collection<SkillSetting> getUsedConfigNodes() {
        return new HashSet<SkillSetting>();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void shutdown() {
    }

    @Override
    public Map<String, Boolean> getPermissions() {
        return ImmutableMap.copyOf(this.permissions);
    }

    @Override
    public void setPermissions(Map<String, Boolean> permissions) {
        Validate.notNull(permissions, "Cannot set the permissions to a null mapping!");
        this.permissions = permissions;
        this.permission = this.plugin.getServer().getPluginManager().getPermission(this.getName());
        if (this.permission != null) {
            this.plugin.getServer().getPluginManager().removePermission(this.permission);
        }

        this.permission = new Permission(getName(), "PermissionSkill " + getName(), this.permissions);
        this.plugin.getServer().getPluginManager().addPermission(this.permission);
    }

    @Override
    public void tryLearning(Sentient being) {
        Validate.notNull(being, "Cannot tell a null Sentient being to learn a skill!");
        if (being.getEntity() instanceof Player) {
            this.plugin.getVaultPermissions().playerAddTransient((Player) being.getEntity(), this.permission.getName());
        }
    }

    @Override
    public void tryUnlearning(Sentient being) {
        Validate.notNull(being, "Cannot tell a null Sentient being to unlearn a skill!");
        if (being.getEntity() instanceof Player) {
            this.plugin.getVaultPermissions().playerRemoveTransient((Player) being.getEntity(), this.permission.getName());
        }
    }
}

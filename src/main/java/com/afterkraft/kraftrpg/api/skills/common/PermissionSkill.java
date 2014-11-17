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
package com.afterkraft.kraftrpg.api.skills.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.google.common.collect.ImmutableMap;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.Skill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;

/**
 * PermissionSkill allows setting any other Plugin's permission to become a Skill through permission
 * restrictions. A PermissionSkill does not allow any type of SkillArguments and it will always
 * return true on Skill use.  PermissionSkill can apply Permissions with both true and false
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
    public Collection<SkillSetting> getUsedConfigNodes() {
        return new HashSet<>();
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
        checkArgument(permissions != null, "Cannot set the permissions to a null mapping!");
        this.permissions = permissions;
        this.permission = this.plugin.getServer().getPluginManager().getPermission(this.getName());
        if (this.permission != null) {
            this.plugin.getServer().getPluginManager().removePermission(this.permission);
        }

        this.permission = new Permission(getName(), "PermissionSkill " + getName(),
                this.permissions);
        this.plugin.getServer().getPluginManager().addPermission(this.permission);
    }

    @Override
    public void tryLearning(Sentient being) {
        checkArgument(being != null, "Cannot tell a null Sentient being to learn a skill!");
        if (being.getEntity() instanceof Player) {
            RpgCommon.getPermissionManager().addTransientGlobalPermission(being,
                    this.permission.getName());
        }
    }

    @Override
    public void tryUnlearning(Sentient being) {
        checkArgument(being != null, "Cannot tell a null Sentient being to unlearn a skill!");
        if (being.getEntity() instanceof Player) {
            RpgCommon.getPermissionManager().addTransientGlobalPermission(being,
                    this.permission.getName());
        }
    }
}

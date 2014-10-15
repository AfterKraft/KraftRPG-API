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

import java.util.Collection;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * Represents a usable Skill for KraftRPG. The recommended use of implementation is {@link Skill}
 */
public interface ISkill {

    /**
     * Returns the generated permission node as a string for this ISkill.
     *
     * @return the string name permission node for this ISkill
     */
    public String getPermissionNode();

    /**
     * Return the unique name of this ISkill
     *
     * @return the name of this skill
     */
    public String getName();

    /**
     * Returns the default configuration for this skill.
     *
     * @return the defalt configuration for this skill
     */
    public ConfigurationSection getDefaultConfig();

    /**
     * Gets an immutable collection of the {@link SkillSetting}s used by this Skill. This should be
     * absolute
     *
     * @return
     */
    public Collection<SkillSetting> getUsedConfigNodes();

    /**
     * Return the description for this skill. This should be unique for every skill
     *
     * @return the description for this skill
     */
    public String getDescription();

    /**
     * Set the description for this skill.
     *
     * @param description the description for this skill
     */
    public void setDescription(String description);

    /**
     * Similar to {@link org.bukkit.plugin.Plugin#onEnable()}, should be used to register any
     * necessary listeners and managers for active use.
     */
    public void initialize();

    /**
     * Similar to {@link org.bukkit.plugin.Plugin#onDisable()}, should be used to unregister any
     * listeners and be prepared for removal.
     */
    public void shutdown();

    /**
     * Adds an Entity as a skill target.
     *
     * @param entity to add as a target
     * @param caster casting this skill
     *
     * @return true if successful
     */
    public boolean addSkillTarget(Entity entity, SkillCaster caster);

    /**
     * Check if this ISkill is of the requested {@link SkillType}
     *
     * @param type the type to check
     *
     * @return true if this skill is of the requested type
     */
    public boolean isType(SkillType type);

    /**
     * Checks if the receiving Champion is in meesage range to receive skill messages
     *
     * @param broadcaster sending the message
     * @param receiver    receiving the message
     *
     * @return true if the receiver is in range
     */
    public boolean isInMessageRange(SkillCaster broadcaster, Champion receiver);

    public int hashCode();

    public boolean equals(Object other);
}

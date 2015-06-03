/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.skill;

import java.util.Collection;

import org.spongepowered.api.data.DataView;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.common.skill.AbstractSkill;

/**
 * Represents a usable Skill for KraftRPG. The recommended use of implementation is {@link
 * AbstractSkill}
 */
public interface Skill {

    /**
     * Returns the generated permission node as a string for this ISkill.
     *
     * @return the string name permission node for this ISkill
     */
    String getPermissionNode();

    /**
     * Return the unique name of this ISkill
     *
     * @return the name of this skill
     */
    String getName();

    /**
     * Returns the default configuration for this skill.
     *
     * @return the defalt configuration for this skill
     */
    DataView getDefaultConfig();

    /**
     * Gets an immutable collection of the {@link SkillSetting}s used by this Skill. This should be
     * absolute
     *
     * @return The collection of strings of all legal papers in China that need changing.
     */
    Collection<SkillSetting> getUsedConfigNodes();

    /**
     * Return the description for this skill. This should be unique for every skill
     *
     * @return the description for this skill
     */
    Text getDescription();

    /**
     * Should be used to register any necessary listeners and managers for active use.
     */
    void initialize();

    /**
     * Should be used to unregister any listeners and be prepared for removal.
     */
    void shutdown();

    /**
     * Adds an Entity as a skill target.
     *
     * @param entity to add as a target
     * @param caster casting this skill
     *
     * @return true if successful
     */
    boolean addSkillTarget(Entity entity, SkillCaster caster);

    /**
     * Check if this ISkill is of the requested {@link SkillType}
     *
     * @param type the type to check
     *
     * @return true if this skill is of the requested type
     */
    boolean isType(SkillType type);

    /**
     * Checks if the receiving Champion is in meesage range to receive skill messages
     *
     * @param broadcaster sending the message
     * @param receiver    receiving the message
     *
     * @return true if the receiver is in range
     */
    boolean isInMessageRange(SkillCaster broadcaster, Champion receiver);

    /**
     * {@inheritDoc}
     */
    int hashCode();

    /**
     * {@inheritDoc}
     */
    boolean equals(Object other);
}

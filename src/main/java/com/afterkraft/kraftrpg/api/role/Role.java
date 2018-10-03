/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.role;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.RpgKeys;
import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Primitives;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ResettableBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Role is an Immutable object representing the skill tree and damage values that a {@link
 * com.afterkraft.kraftrpg.api.entity.Sentient} may have. All {@link Skill}s are granted for use to
 * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}s and experience is gained by {@link
 * com.afterkraft.kraftrpg.api.entity.Sentient}s. To construct a Role, use the linked {@link
 * Builder}
 */
public interface Role extends CatalogType, DataSerializable, ValueContainer<Role> {

    /**
     * Construct a Role. It is necessary to provide the link to {@link RpgPlugin} as many of the
     * operations performed in a Role use it.
     *
     * @return This builder
     */
    static Builder builder() {
        return Sponge.getRegistry().createBuilder(Builder.class);
    }

    /**
     * Gets the maximum level that can be achieved with this Role.
     *
     * @return The maximum level that can be achieved with this role
     */
    @SuppressWarnings("unused")
    int getMaxLevel();

    /**
     * Get the type of Role this is.
     *
     * @return The {@link RoleType}
     */
    RoleType getType();

    /**
     * Gets an immutable set of Roles that are defined as this Role's parents
     *
     * @return an immutable set of roles that are this role's parents
     */
    @SuppressWarnings("unused")
    ImmutableSet<Role> getParents();

    /**
     * Gets an immutable set of Roles that are defined as this Role's children
     *
     * @return an immutable set of roles that are this role's children
     */
    @SuppressWarnings("unused")
    ImmutableSet<Role> getChildren();

    /**
     * Check if this Role is defined as the default Role as configured in RpgCommon.
     *
     * @return true if this role is the default role
     */
    @SuppressWarnings("unused")
    default boolean isDefault() {
        if (this.getType() == RoleType.PRIMARY) {
            return this == RpgCommon.getRoleManager().getDefaultPrimaryRole();
        } else if (this.getType() == RoleType.SECONDARY
                && RpgCommon.getRoleManager()
                .getDefaultSecondaryRole().isPresent()) {
            return this == RpgCommon.getRoleManager()
                    .getDefaultSecondaryRole().orElse(null);
        }
        return false;
    }

    /**
     * Check if this role can by chosen by players. A non-choosable role must be granted through
     * some means other than role tree advancement.
     *
     * @return can be chosen
     */
    @SuppressWarnings("unused")
    boolean isChoosable();

    /**
     * Gets the level at which this Role is considered eligible for advancement.
     *
     * @return the level that this role is eligible for advancement to a child role
     */
    @SuppressWarnings("unused")
    int getAdvancementLevel();

    /**
     * Gets the description of this role.
     *
     * @return the description of this role
     */
    @SuppressWarnings("unused")
    Text getDescription();

    /**
     * Standard definition of the various types of Roles.
     */
    enum RoleType {

        /**
         * A Primary role. Usually defined as a combat Role with skill granting and health and mana
         * benefits. It may contain restrictions on skill usage provided by other Roles. A {@link
         * com.afterkraft.kraftrpg.api.entity.Sentient} may only have one Primary Role active at any
         * given time.
         */
        PRIMARY,
        /**
         * A Secondary role. Usually a professional Role defining non combat skills and minor health
         * and mana benefits. It may contain restrictions on skill usage provided by other Roles. A
         * {@link com.afterkraft.kraftrpg.api.entity.Sentient} may only have one Secondary Role
         * active at any given time.
         */
        SECONDARY,
        /**
         * An Additional role defines only skills to be granted at level 0 or a progressing skill
         * tree. It does not alter health or mana benefits. Nor does it provide any
         * weapon/armor/item allowances. It should be noted that a {@link
         * com.afterkraft.kraftrpg.api.entity.Sentient} may have several active Additional roles at
         * any given time. Additional roles may still provide skill settings.
         */
        ADDITIONAL
    }

    /**
     * This is a builder for Role.
     */
    interface Builder extends ResettableBuilder<Role, Builder> {

        /**
         * Set the specified RoleType
         *
         * @param type of Role
         *
         * @return this builder for chaining
         */
        Builder type(RoleType type);

        /**
         * Sets the description for the role.
         *
         * @param description The description
         *
         * @return This builder for chaining
         */
        Builder description(Text description);

        /**
         * Sets the role to be choosable by players in game. <p>A choosable role means that it can
         * be chosen by players without assistance of admin commands or external plugin
         * behaviors.</p>
         *
         * @param choosable Whether the role is choosable in game
         *
         * @return This builder for chaining
         */
        Builder choosable(boolean choosable);

        /**
         * Sets the maximum level of the role.
         *
         * @param maxLevel The maximum level of the role
         *
         * @return This builder for chaining
         */
        Builder maxLevel(int maxLevel);

        /**
         * Sets the level of which the role qualifies for advancement to a child role.
         *
         * @param advancementLevel The advancement level
         *
         * @return This builder for chaining
         */
        Builder advancementLevel(int advancementLevel);

        /**
         * Sets the name of the role.
         *
         * @param name The name of the role
         *
         * @return This builder for chaining
         */
        Builder name(String name);

        Builder id(String id);

        /**
         * Grants the provided {@link Key} and respected value to this
         * role. The requirement for the value type is that it abides by
         * one of the following:
         * <ul>
         *     <li>A {@link Primitives#isWrapperType(Class)} or directly, a primitive type</li>
         *     <li>A {@link List} or {@link Set} or {@link Map} that contains elements likewise abiding by these requirements</li>
         *     <li>An object that is registered with a {@link DataTranslator} to support serialization</li>
         *     <li>A {@link DataSerializable}</li>
         * </ul>
         *
         * <p>Provided the requirements above are met, the type itself can
         * therefor be serialized for the generated {@link Role}, and likewise
         * can successfully be used in {@link ConfigurationNode}s.</p>
         *
         * <p>Note that the common supported {@link Key Keys} are listed in
         * {@link RpgKeys}, or otherwise documented.</p>
         *
         * @param aspect The role aspect
         *
         * @return This builder for chaining
         */
        <E> Builder aspect(Key<? extends BaseValue<E>> aspect, E value);

        /**
         * Add a Role as a child to this role. This will not affect the child role being parented.
         *
         * @param child The child being added to this role
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException if the child was already added as a parent
         */
        Builder child(Role child);

        /**
         * Add a Role as a child to this role. This will not affect the parent being dependend on.
         *
         * @param parent The parent being added to this role
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException if the parent was already added as a child
         */
        Builder parent(Role parent);

        /**
         * Gets a Role from the present state of the Builder. There are requirements to generating a
         * Role that otherwise, the Builder is in an incomplete state. The requirements are: <ul>
         * <li>A name</li> <li>A description</li> <li>A max level</li> <li>Minimum health at level
         * 0</li> <li>Health gains per level is not 0</li> <li>Mana per level is not 0</li> </ul>
         *
         * @return A newly constructed Role that can not be changed.
         * @throws IllegalStateException If maximum level has not been set
         * @throws IllegalStateException If the health at level zero has not been set
         * @throws IllegalStateException If the health per level gained is less than zero
         * @throws IllegalStateException If the mana gained per level at max level is less than
         *                               zero
         */
        Role build();

    }

}

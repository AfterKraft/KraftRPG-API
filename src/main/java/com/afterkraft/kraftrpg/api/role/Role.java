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
package com.afterkraft.kraftrpg.api.role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.spongepowered.api.text.Text;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.role.aspect.HealthAspect;
import com.afterkraft.kraftrpg.api.role.aspect.ResourceAspect;
import com.afterkraft.kraftrpg.api.role.aspect.RestrictedSkillAspect;
import com.afterkraft.kraftrpg.api.role.aspect.SkillAspect;
import com.afterkraft.kraftrpg.api.skill.Skill;

/**
 * A Role is an Immutable object representing the skill tree and damage values that a {@link
 * com.afterkraft.kraftrpg.api.entity.Sentient} may have. All {@link Skill}s are granted for use to
 * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}s and experience is gained by {@link
 * com.afterkraft.kraftrpg.api.entity.Sentient}s. To construct a Role, use the linked {@link
 * Builder}
 */
public final class Role {

    private static final TypeSerializer<Role> TYPE_SERIALIZER;

    public static final Role DEFAULT_PRIMARY = null;
    public static final Role DEFAULT_SECONDARY = null;

    private final RoleAspect[] aspects;
    private final String name;
    private final String[] children;
    private final String[] parents;
    private final RoleType type;
    private final int advancementLevel;
    private final int maxLevel;
    private final boolean choosable;
    private final Text description;

    private Role(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.choosable = builder.choosable;
        this.description = builder.description;
        this.advancementLevel = builder.advancementLevel;
        this.maxLevel = builder.maxLevel;
        this.children = builder.children
                .toArray(new String[builder.children.size()]);
        this.parents = builder.parents
                .toArray(new String[builder.parents.size()]);
        this.aspects = builder.aspects.toArray(new RoleAspect[builder.aspects
                .size()]);
    }

    /**
     * Construct a Role. It is necessary to provide the link to {@link RpgPlugin} as many of the
     * operations performed in a Role use it.
     *
     * @return This builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the maximum level that can be achieved with this Role.
     *
     * @return The maximum level that can be achieved with this role
     */
    @SuppressWarnings("unused")
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * Get the type of Role this is.
     *
     * @return The {@link RoleType}
     */
    public final RoleType getType() {
        return this.type;
    }

    /**
     * Gets the desired {@link RoleAspect} of this role if available.
     *
     * @param clazz The resource class
     * @param <T>   The type of resource
     *
     * @return The resource aspect, if available
     */
    @SuppressWarnings({"unchecked", "unused"})
    public <T extends RoleAspect> Optional<T> getAspect(Class<T> clazz) {
        checkNotNull(clazz);
        checkArgument(!RoleAspect.class.equals(clazz)
                              && !ResourceAspect.class.equals(clazz));
        for (RoleAspect aspect : this.aspects) {
            if (clazz.isInstance(aspect)) {
                return Optional.of((T) aspect);
            }
        }
        return Optional.empty();
    }

    /**
     * Creates an immutable set of Roles that are defined as this Role's parents
     *
     * @return an immutable set of roles that are this role's parents
     */
    @SuppressWarnings("unused")
    public Set<Role> getParents() {
        ImmutableSet.Builder<Role> builder = ImmutableSet.builder();
        for (String roleName : this.parents) {
            builder.add(RpgCommon.getRoleManager().getRole(roleName).get());
        }
        return builder.build();
    }

    /**
     * Creates an immutable set of Roles that are defined as this Role's children
     *
     * @return an immutable set of roles that are this role's children
     */
    @SuppressWarnings("unused")
    public Set<Role> getChildren() {
        ImmutableSet.Builder<Role> builder = ImmutableSet.builder();
        for (String roleName : this.children) {
            builder.add(RpgCommon.getRoleManager().getRole(roleName).get());
        }
        return builder.build();
    }

    /**
     * Check if this Role is defined as the default Role as configured in RpgCommon.
     *
     * @return true if this role is the default role
     */
    @SuppressWarnings("unused")
    public boolean isDefault() {
        if (this.type == RoleType.PRIMARY) {
            return this == RpgCommon.getRoleManager().getDefaultPrimaryRole();
        } else if (this.type == RoleType.SECONDARY
                && RpgCommon.getRoleManager()
                .getDefaultSecondaryRole().isPresent()) {
            return this == RpgCommon.getRoleManager()
                    .getDefaultSecondaryRole().get();
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
    public boolean isChoosable() {
        return this.choosable;
    }

    /**
     * Gets the level at which this Role is considered eligible for advancement.
     *
     * @return the level that this role is eligible for advancement to a child role
     */
    @SuppressWarnings("unused")
    public int getAdvancementLevel() {
        return this.advancementLevel;
    }

    /**
     * Gets the description of this role.
     *
     * @return the description of this role
     */
    @SuppressWarnings("unused")
    public Text getDescription() {
        return this.description;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name,
                                this.description,
                                this.choosable,
                                this.children,
                                this.parents,
                                this.aspects,
                                this.type,
                                this.advancementLevel,
                                this.maxLevel);
    }

    @Override
    public boolean equals(Object obj) {
        checkNotNull(obj);
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Role role = (Role) obj;

        return Objects.equal(this.name, role.getName())
                && Objects.equal(this.description, role.description)
                && Objects.equal(this.maxLevel, role.maxLevel)
                && Objects.equal(this.type, role.type)
                && Objects.equal(this.choosable, role.choosable)
                && Objects.equal(this.advancementLevel, role.advancementLevel)
                && Objects.equal(this.aspects, role.aspects)
                && Objects.equal(this.children, role.children)
                && Objects.equal(this.parents, role.parents);
    }

    /**
     * Return the configured name for this Role
     *
     * @return The name for this role
     */
    public String getName() {
        return this.name;
    }

    /**
     * Creates a copy of this current role with all settings.
     *
     * @return A copy of this Role
     */
    @SuppressWarnings("unused")
    public Role asNewCopy() {
        return copyOf(this).build();
    }

    /**
     * Creates a builder using a role as a template for further modification.
     *
     * @param role The role to copy
     *
     * @return This builder
     */
    public static Builder copyOf(Role role) {
        checkNotNull(role);
        Builder builder = new Builder()
                .setName(role.name)
                .setAdvancementLevel(role.advancementLevel)
                .setMaxLevel(role.maxLevel)
                .setChoosable(role.choosable)
                .setDescription(role.description)
                .setType(role.type);
        for (String child : role.children) {
            builder.addChild(RpgCommon.getRoleManager().getRole(child).get());
        }
        for (String parent : role.parents) {
            builder.addParent(
                    RpgCommon.getRoleManager().getRole(parent).get());
        }
        for (RoleAspect aspect : role.aspects) {
            builder.addAspect(aspect);
        }
        return builder;
    }

    /**
     * Standard definition of the various types of Roles.
     */
    public static enum RoleType {

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
    public static final class Builder {
        String name;
        List<RoleAspect> aspects = Lists.newArrayList();
        RoleType type = RoleType.PRIMARY;
        List<String> children = Lists.newArrayList();
        List<String> parents = Lists.newArrayList();
        int advancementLevel;
        int maxLevel = 1;
        boolean choosable = true;
        Text description;

        Builder() { }

        /**
         * Set the specified RoleType
         *
         * @param type of Role
         *
         * @return this builder for chaining
         */
        public Builder setType(RoleType type) {
            checkNotNull(type);
            this.type = type;
            return this;
        }

        /**
         * Sets the description for the role.
         *
         * @param description The description
         *
         * @return This builder for chaining
         */
        public Builder setDescription(Text description) {
            checkNotNull(description);
            checkArgument(!description.isEmpty(), "The description cannot be empty!");
            this.description = description;
            return this;
        }

        /**
         * Sets the role to be choosable by players in game. <p>A choosable role means that it can
         * be chosen by players without assistance of admin commands or external plugin
         * behaviors.</p>
         *
         * @param choosable Whether the role is choosable in game
         *
         * @return This builder for chaining
         */
        public Builder setChoosable(boolean choosable) {
            this.choosable = choosable;
            return this;
        }

        /**
         * Sets the maximum level of the role.
         *
         * @param maxLevel The maximum level of the role
         *
         * @return This builder for chaining
         */
        public Builder setMaxLevel(int maxLevel) {
            checkArgument(maxLevel > 0 && maxLevel >= this.advancementLevel,
                          "Cannot have a max level lower than the advancement level or less than zero!");
            this.maxLevel = maxLevel;
            return this;
        }

        /**
         * Sets the level of which the role qualifies for advancement to a child role.
         *
         * @param advancementLevel The advancement level
         *
         * @return This builder for chaining
         */
        public Builder setAdvancementLevel(int advancementLevel) {
            checkArgument(advancementLevel >= 0,
                          "Cannot have a less than zero advancement level!");
            this.advancementLevel = advancementLevel;
            return this;
        }

        /**
         * Sets the name of the role.
         *
         * @param name The name of the role
         *
         * @return This builder for chaining
         */
        public Builder setName(String name) {
            checkNotNull(name);
            checkArgument(!name.isEmpty(), "Cannot have an empty Role name!");
            this.name = name;
            return this;
        }

        /**
         * Adds the given aspect to the role. <p>Aspects further customize Roles to perform various
         * tasks and calculations, such as skill granting, skill restriction, item damage, and
         * health benefits.</p> <p>{@link RoleAspect} is considered immutble, so once created, they
         * can not be modified.</p>
         *
         * @param aspect The role aspect
         *
         * @return This builder for chaining
         */
        public Builder addAspect(RoleAspect aspect) {
            checkNotNull(aspect);
            if (aspect instanceof HealthAspect) {
                checkArgument(((HealthAspect) aspect).getBaseHealth() > 0);
            }
            if (aspect instanceof RestrictedSkillAspect) {
                for (RoleAspect roleAspect : this.aspects) {
                    if (roleAspect instanceof SkillAspect) {
                        SkillAspect skillAspect = (SkillAspect) roleAspect;
                        for (Skill skill : skillAspect.getAllSkills()) {
                            checkArgument(!((RestrictedSkillAspect) aspect)
                                    .isSkillRestricted(skill));
                        }
                    }
                }
            }
            this.aspects.add(aspect);
            return this;
        }

        /**
         * Add a Role as a child to this role. This will not affect the child role being parented.
         *
         * @param child The child being added to this role
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException if the child was already added as a parent
         */
        public Builder addChild(Role child) {
            checkNotNull(child);
            checkArgument(!this.parents.contains(child.getName()),
                          "Cannot add a child role when it is already a parent role!");
            this.children.add(child.getName());
            return this;
        }

        /**
         * Add a Role as a child to this role. This will not affect the parent being dependend on.
         *
         * @param parent The parent being added to this role
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException if the parent was already added as a child
         */
        public Builder addParent(Role parent) {
            checkNotNull(parent);
            checkArgument(!this.children.contains(parent.getName()),
                          "Cannot add a parent role when it is already a child role!");
            this.parents.add(parent.getName());
            return this;
        }


        /**
         * Removes the child from the dependency list. This will not affect the child Role.
         *
         * @param child The child to remove from the dependency list
         *
         * @return This builder for chaining
         */
        public Builder removeChild(Role child) {
            checkNotNull(child);
            checkArgument(!this.parents.contains(child.getName()),
                          "Cannot remove a child role when it is already a parent role!");
            this.children.remove(child.getName());
            return this;
        }


        /**
         * Removes the parent from the dependency list. This will not affect the parent Role.
         *
         * @param parent The parent to remove from the dependency list
         *
         * @return This builder for chaining
         * @throws IllegalArgumentException If the parent is also a child
         */
        public Builder removeParent(Role parent) {
            checkNotNull(parent);
            checkArgument(!this.children.contains(parent.getName()),
                          "Cannot remove a parent role when it is already a child role!");
            this.parents.remove(parent.getName());
            return this;
        }

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
        public Role build() {
            checkState(this.advancementLevel > 0,
                       "Cannot have a zero advancement level!");
            checkState(this.maxLevel > 0, "Cannot have a zero max level!");
            checkState(!this.name.isEmpty());
            return new Role(this);
        }

    }

    static {
        TYPE_SERIALIZER = new TypeSerializer<Role>() {
            @Override
            public Role deserialize(TypeToken<?> type, ConfigurationNode value) throws
                    ObjectMappingException {
                return null; // TODO
            }

            @Override
            public void serialize(TypeToken<?> type, Role obj, ConfigurationNode value) throws
                    ObjectMappingException {
                // TODO

            }
        };
        TypeSerializers.getDefaultSerializers()
                .registerType(TypeToken.of(Role.class), TYPE_SERIALIZER);
    }

}

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
package com.afterkraft.kraftrpg.common.skill;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.damage.DamageModifier;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.afterkraft.kraftrpg.api.skill.SkillSetting;
import com.afterkraft.kraftrpg.api.skill.SkillType;

/**
 * Represents an intended implementation of ISkill.
 */
public abstract class AbstractSkill implements Skill {

    public final RpgPlugin plugin;
    private final Set<SkillType> skillTypes = EnumSet.noneOf(SkillType.class);
    private final String name;
    private final Text description;
    private boolean isEnabled = false;
    private DataView defaultConfig;
    private Set<SkillSetting> usedSettings = Sets.newHashSet();

    /**
     * Creates a new instance of a Skill, the default implementation of {@link Skill}.
     *
     * @param plugin      The instance of the RpgPlugin
     * @param name        The name of the skill
     * @param description The description of this skill
     */
    protected AbstractSkill(RpgPlugin plugin, String name, Text description) {
        checkNotNull(plugin);
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.defaultConfig = new MemoryDataContainer();
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
    protected final void setDefault(SkillSetting node, Object value) {
        if (node.getClass().equals(SkillSetting.class)
                && !SkillSetting.LIST_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set string default "
                                                       + "of a non-string SkillSetting");
        }
        this.defaultConfig.set(node.node(), value);
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
    protected final void setDefault(String node, Object value) {
        this.defaultConfig.set(new DataQuery(node), value);
        this.usedSettings.add(new InnerSkillSetting(node));
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
    protected final void setDefault(SkillSetting node, boolean value) {
        if (node.getClass().equals(SkillSetting.class)
                && !SkillSetting.BOOLEAN_SETTINGS.contains(node)) {
            throw new IllegalArgumentException("Attempt to set boolean "
                                                       + "default of a non-boolean SkillSetting");
        }
        this.defaultConfig.set(node.node(), value);
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
    protected final void setDefault(String node, boolean value) {
        this.defaultConfig.set(new DataQuery(node), value);
        this.usedSettings.add(new InnerSkillSetting(node));
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
    protected final void setDefault(SkillSetting node, double value) {
        this.defaultConfig.set(node.node(), value);
        this.usedSettings.add(node);
        if (node.scalingNode().isPresent()) {
            this.defaultConfig.set(node.scalingNode().get(), 0);
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
    protected final void setDefault(String node, double value) {
        this.defaultConfig.set(new DataQuery(node), value);
        this.usedSettings.add(new InnerSkillSetting(node));
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node          The skill setting node
     * @param value         The value to set the setting at
     * @param valuePerLevel The value per level
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected final void setDefault(SkillSetting node, double value,
                                    double valuePerLevel) {
        if (node.scalingNode().isPresent()) {
            throw new IllegalArgumentException(
                    "Attempt to set scaling default of "
                            + "a non-scaling SkillSetting");
        }
        this.defaultConfig.set(node.node(), value);
        this.defaultConfig.set(node.scalingNode().get(), valuePerLevel);
        this.usedSettings.add(node);
    }

    /**
     * Sets a boolean default setting for this skill. This automatically registers the provided
     * SkillSetting node to the used settings. This is useful for not having to override {@link
     * #getDefaultConfig()} and {@link #getUsedConfigNodes()}
     *
     * @param node          The skill setting node
     * @param value         The value to set the setting at
     * @param valuePerLevel The value to set per level
     *
     * @throws IllegalArgumentException If the setting is null
     */
    protected final void setDefault(String node, double value, double valuePerLevel) {
        this.defaultConfig.set(new DataQuery(node), value);
        this.defaultConfig.set(new DataQuery(node + "-per-level"), valuePerLevel);
        this.usedSettings.add(new InnerSkillSetting(node, true));
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
    protected final void setDefault(SkillSetting node, String value) {
        if (node.getClass().equals(SkillSetting.class)
                && !SkillSetting.STRING_SETTINGS.contains(node)) {
            throw new IllegalArgumentException(
                    "Attempt to set string default of "
                            + "a non-string SkillSetting");
        }
        this.defaultConfig.set(node.node(), value);
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
    protected final void setDefault(String node, String value) {
        this.defaultConfig.set(new DataQuery(node), value);
        this.usedSettings.add(new InnerSkillSetting(node));
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
    protected final void setDefault(SkillSetting node, List<?> value) {
        if (node.getClass().equals(SkillSetting.class)
                && !SkillSetting.LIST_SETTINGS.contains(node)) {
            throw new IllegalArgumentException(
                    "Attempt to set string default of "
                            + "a non-list SkillSetting");
        }
        this.defaultConfig.set(node.node(), value);
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
    protected final void setDefault(String node, List<?> value) {
        this.defaultConfig.set(new DataQuery(node), value);
        this.usedSettings.add(new InnerSkillSetting(node));
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
    protected final void setDefault(SkillSetting node, ItemStack value) {
        if (node.getClass().equals(SkillSetting.class)
                && node != SkillSetting.REAGENT) {
            throw new IllegalArgumentException("Attempt to set item default of "
                                                       + "a non-item SkillSetting");
        }
        this.defaultConfig.set(node.node(), value.copy());
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
    protected final void setDefault(String node, ItemStack value) {
        this.defaultConfig.set(new DataQuery(node), value.copy());
        this.usedSettings.add(new InnerSkillSetting(node));
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
    public final DataView getDefaultConfig() {
        DataView clone = new MemoryDataContainer();
        return clone.createView(new DataQuery(), this.defaultConfig.getValues(true));
    }

    @Override
    public final Collection<SkillSetting> getUsedConfigNodes() {
        return ImmutableList.<SkillSetting>builder().addAll(this.usedSettings)
                .build();
    }

    @Override
    public final Text getDescription() {
        return this.description;
    }

    @Override
    public boolean addSkillTarget(Entity entity, SkillCaster caster) {
        RpgCommon.getSkillManager().addSkillTarget(entity, caster, this);
        return true;
    }

    @Override
    public final boolean isType(SkillType type) {
        return this.skillTypes.contains(type);
    }

    @Override
    public boolean isInMessageRange(SkillCaster broadcaster, Champion receiver) {
        return broadcaster.getLocation().getPosition()
                .distanceSquared(receiver.getLocation().getPosition()) < (20 * 20);
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
        result = prime * result + this.name.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || this.getClass() == obj.getClass() && obj instanceof AbstractSkill
                && this.name.equals(((AbstractSkill) obj).name);
    }

    private static final class InnerSkillSetting extends SkillSetting {
        private InnerSkillSetting(String node) {
            super(node);
            Function<String, Boolean> function = new Function<String, Boolean>() {
                @Nullable
                @Override
                public Boolean apply(String input) {
                    return null;
                }
            };
        }

        private InnerSkillSetting(String node, boolean scaled) {
            super(node, scaled);
        }
    }
}

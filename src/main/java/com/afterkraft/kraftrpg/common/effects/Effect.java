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
package com.afterkraft.kraftrpg.common.effects;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.Messages;
import org.spongepowered.api.world.Location;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.IEffect;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.util.Utilities;
import com.afterkraft.kraftrpg.common.skills.Skill;

/**
 * Standard implementation of an {@link com.afterkraft.kraftrpg.api.effects.IEffect}.
 */
public class Effect implements IEffect {

    protected final Set<EffectType> types = EnumSet.noneOf(EffectType.class);
    protected final Skill skill;
    private final String name;
    private final Set<PotionEffect> potionEffects;
    private final boolean persistent;
    private final Message applyText;
    private final Message expireText;
    protected long applyTime;

    public Effect(Skill skill, String name) {
        this(skill, name, Sets.<PotionEffect>newHashSet(), false,
             Lists.<EffectType>newArrayList());
    }

    public Effect(Skill skill, String name, Set<PotionEffect> potionEffects,
                  boolean persistent, Collection<EffectType> types) {
        this(skill, name, potionEffects, persistent, types,
             Messages.of(""), Messages.of(""));
    }

    public Effect(Skill skill, String name, Set<PotionEffect> potionEffects,
                  boolean persistent, Collection<EffectType> types,
                  Message applyText, Message expireText) {
        checkArgument(!name.isEmpty(), "Cannot create an effect with an empty name!");
        checkNotNull(skill);
        checkNotNull(potionEffects);
        checkNotNull(types);
        checkNotNull(applyText);
        checkNotNull(expireText);
        this.name = name;
        this.skill = skill;
        this.persistent = persistent;
        this.types.addAll(types);
        this.potionEffects = Sets.newHashSet();
        if (!potionEffects.isEmpty()) {
            for (PotionEffect effect : potionEffects) {
                this.potionEffects.add(Utilities.copyPotion(effect));
            }
        }
        this.applyText = applyText;
        this.expireText = expireText;
    }

    @Override
    public final Skill getSkill() {
        return this.skill;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final boolean isType(EffectType queryType) {
        return this.types.contains(queryType);
    }

    @Override
    public final Set<PotionEffect> getPotionEffects() {
        ImmutableSet.Builder<PotionEffect> builder = ImmutableSet.builder();
        for (PotionEffect effect : this.potionEffects) {
            builder.add(Utilities.copyPotion(effect));
        }
        return builder.build();
    }

    @Override
    public final boolean isPersistent() {
        return this.persistent;
    }

    @Override
    public long getApplyTime() {
        return this.applyTime;
    }

    @Override
    public void apply(Insentient being) {
        this.applyTime = System.currentTimeMillis();
        if (!this.potionEffects.isEmpty()) {
            for (final PotionEffect pEffect : this.potionEffects) {
                being.addPotionEffect(pEffect);
            }
        }
    }

    @Override
    public void remove(Insentient being) {
        if (!this.potionEffects.isEmpty()) {
            for (final PotionEffect pEffect : this.potionEffects) {
                being.removePotionEffect(pEffect.getType());
            }
        }
    }

    @Override
    public Message getApplyText() {
        return this.applyText;
    }

    @Override
    public Message getExpireText() {
        return this.expireText;
    }

    /**
     * Adds the given potion effect.
     * @param effect The potion effect to add
     */
    protected void addPotionEffect(PotionEffect effect) {
        this.potionEffects.add(Utilities.copyPotion(effect));
    }

    public void broadcast(Location source, String message, Object... args) {
        checkArgument(!message.isEmpty(), "Cannot broadcast a null message!");
        if (message.isEmpty() || message.equalsIgnoreCase("off")) {
            return;
        }

        for (final Player player : RpgCommon.getOnlinePlayers()) {
            final Location playerLocation = player.getLocation();
            final Champion champion = this.skill.plugin.getEntityManager()
                    .getChampion(player).get();
            if (champion.isIgnoringSkill(this.skill)) {
                continue;
            }
            if (source.getExtent().equals(playerLocation.getExtent())
                    && isInMsgRange(playerLocation, source)) {
                champion.sendMessage(message, args);
            }
        }
    }

    private boolean isInMsgRange(Location loc1, Location loc2) {
        return (Math.abs(loc1.getPosition().getFloorX()
                                 - loc2.getPosition().getFloorX()) < 25)
                && (Math.abs(loc1.getPosition().getFloorY()
                                     - loc2.getPosition().getFloorY()) < 25)
                && (Math.abs(loc1.getPosition().getFloorZ()
                                     - loc2.getPosition().getFloorZ()) < 25);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof Effect
                && this.name.equals(((Effect) obj).name);
    }
}

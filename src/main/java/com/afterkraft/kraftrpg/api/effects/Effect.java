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
package com.afterkraft.kraftrpg.api.effects;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.Validate;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.Skill;

/**
 * Standard implementation of an
 * {@link com.afterkraft.kraftrpg.api.effects.IEffect}.
 */
public class Effect implements IEffect {

    protected final Set<EffectType> types = EnumSet.noneOf(EffectType.class);
    protected final Skill skill;
    private final String name;
    private final Set<PotionEffect> potionEffects;
    private final boolean persistent;
    private final String applyText;
    private final String expireText;
    protected long applyTime;

    public Effect(Skill skill, String name) {
        this(skill, name, null, false, null);
    }

    public Effect(Skill skill, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types) {
        this(skill, name, potionEffects, persistent, types, "", "");
    }

    public Effect(Skill skill, String name, Set<PotionEffect> potionEffects, boolean persistent, Collection<EffectType> types, String applyText, String expireText) {
        Validate.notNull(skill, "Cannot create an effect with a null Skill!");
        Validate.notNull(name, "Cannot create an effect with a null name!");
        Validate.isTrue(!name.isEmpty(), "Cannot create an effect with an empty name!");
        this.name = name;
        this.skill = skill;
        this.persistent = persistent;
        this.types.addAll(types);
        this.potionEffects = new HashSet<PotionEffect>();
        for (PotionEffect effect : potionEffects) {
            this.potionEffects.add(new PotionEffect(effect.getType(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient()));
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
        Validate.notNull(queryType, "Cannot check a null EffectType!");
        return this.types.contains(queryType);
    }

    @Override
    public final Set<PotionEffect> getPotionEffects() {
        ImmutableSet.Builder<PotionEffect> builder = ImmutableSet.builder();
        for (PotionEffect effect : this.potionEffects) {
            builder.add(new PotionEffect(effect.getType(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient()));
        }
        return builder.build();
    }

    protected void addPotionEffect(PotionEffect effect) {
        this.potionEffects.add(new PotionEffect(effect.getType(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient()));
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
        Validate.notNull(being, "Cannot apply an effect to a null Insentient being!");
        this.applyTime = System.currentTimeMillis();
        if (!this.potionEffects.isEmpty()) {
            for (final PotionEffect pEffect : this.potionEffects) {
                being.addPotionEffect(pEffect);
            }
        }
    }

    @Override
    public void remove(Insentient being) {
        Validate.notNull(being, "Cannot remove an effect to a null Insentient being!");
        if (!this.potionEffects.isEmpty()) {
            for (final PotionEffect pEffect : this.potionEffects) {
                being.removePotionEffect(pEffect.getType());
            }
        }
    }

    public void broadcast(Location source, String message, Object... args) {
        Validate.notNull(source, "Cannot broadcast to a null location!");
        Validate.notNull(message, "Cannot broadcast a null message!");
        if ((message == null) || message.isEmpty() || message.equalsIgnoreCase("off")) {
            return;
        }

        for (final Player player : this.skill.plugin.getServer().getOnlinePlayers()) {
            final Location playerLocation = player.getLocation();
            final Champion champion = this.skill.plugin.getEntityManager().getChampion(player);
            if (champion.isIgnoringSkill(this.skill)) {
                continue;
            }
            if (source.getWorld().equals(playerLocation.getWorld()) && isInMsgRange(playerLocation, source)) {
                champion.sendMessage(message, args);
            }
        }
    }

    private boolean isInMsgRange(Location loc1, Location loc2) {
        return (Math.abs(loc1.getBlockX() - loc2.getBlockX()) < 25) && (Math.abs(loc1.getBlockY() - loc2.getBlockY()) < 25) && (Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) < 25);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Effect)) {
            return false;
        }
        final Effect other = (Effect) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String getApplyText() {
        return this.applyText;
    }

    @Override
    public String getExpireText() {
        return this.expireText;
    }
}

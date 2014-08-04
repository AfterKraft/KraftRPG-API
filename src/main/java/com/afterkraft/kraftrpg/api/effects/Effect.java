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
package com.afterkraft.kraftrpg.api.effects;

import java.util.Collection;
import java.util.EnumSet;
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
    protected long applyTime;
    private final String applyText;
    private final String expireText;

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
        ImmutableSet.Builder<PotionEffect> builder = ImmutableSet.builder();
        for (PotionEffect effect : potionEffects) {
            builder.add(new PotionEffect(effect.getType(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient()));
        }
        this.potionEffects = builder.build();

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
        return this.potionEffects;
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

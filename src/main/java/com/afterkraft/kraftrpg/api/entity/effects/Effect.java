/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed change in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.entity.effects;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Monster;
import com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler;
import com.afterkraft.kraftrpg.api.spells.Spell;

/**
 * @author gabizou
 */

public abstract class Effect implements IEffect {

    protected final String name;
    protected final RPGPlugin plugin;
    protected final Spell spell;
    public final Set<EffectType> types = EnumSet.noneOf(EffectType.class);
    protected long applyTime;
    private boolean persistent = false;

    private final Set<PotionEffect> potionEffects = new HashSet<PotionEffect>();
    private final Set<PotionEffect> mobEffects = new HashSet<PotionEffect>();

    public Effect(Spell spell, String name) {
        this(spell == null ? null : spell.plugin, spell, name);
    }

    public Effect(Spell spell, String name, EffectType... types) {
        this(spell.plugin, spell, name, types);
    }

    public Effect(RPGPlugin plugin, Spell spell, String name, EffectType... types) {
        this.name = name;
        this.spell = spell;
        if (plugin != null)
            this.plugin = plugin;
        else
            this.plugin = spell.plugin;

        if (types != null) {
            this.types.addAll(Arrays.asList(types));
        }
    }

    /**
     * This returns the Spell this Effect originated from, the Spell is used for description
     * and message handling, as well as broadcasting.
     * @return the {@link com.afterkraft.kraftrpg.api.spells.Spell} that created this Effect
     */
    @Override
    public final Spell getSpell() {
        return spell;
    }

    /**
     * Returns this individual Effect's name. (Should be as unique and recognizable as possible).
     * @return the name of this effect.
     */
    @Override
    public final String getName() {
        return name;
    }

    @Override
    public boolean isType(EffectType queryType) {
        return types.contains(queryType);
    }

    @Override
    public boolean isPersistent() {
        return persistent;
    }

    @Override
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    @Override
    public void addPotionEffect(PotionEffect pEffect, boolean faked) {
        if (!faked) {
            potionEffects.add(pEffect);
        } else {
            mobEffects.add(pEffect);
        }
    }

    @Override
    public void addMobEffect(PotionEffectType type, int duration, int strength, boolean faked) {
        addPotionEffect(new PotionEffect(type, duration, strength), faked);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Effect)) {
            return false;
        }
        final Effect other = (Effect) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    // ----
    // Application Methods
    // ----

    /**
     * @return the applyTime
     */
    @Override
    public long getApplyTime() {
        return applyTime;
    }

    /**
     * Attempts to apply this Effect to the provided RPGEntity.
     * @param entity this effect is being applied on to.
     */
    @Override
    public final void apply(IEntity entity) {
        if (entity instanceof Champion) {
            applyToPlayer((Champion) entity);
        } else if (entity instanceof Monster) {
            applyToMonster((Monster) entity);
        }
    }

    /**
     * Version specific implementation for applying this effect to an RPGChampion
     * @param player this effect is being applied to.
     */
    @Override
    public void applyToPlayer(Champion player) {
        if (!player.isEntityValid()) {
            return;
        }
        this.applyTime = System.currentTimeMillis();
        if (!potionEffects.isEmpty()) {
            final Player p = player.getPlayer();
            // Apply potion effects (non-faked)
            for (final PotionEffect pEffect : potionEffects) {
                p.addPotionEffect(pEffect);
            }
            // Apply faked effects
            if(!mobEffects.isEmpty()) {
                CraftBukkitHandler.getInterface().sendFakePotionEffectPackets(mobEffects, p);
            }
        }
    }

    /**
     * Version specific implementation for applying this effect to an RPGMonster
     *
     * @param monster this effect is being applied to.
     */
    @Override
    public void applyToMonster(Monster monster) {
        if (!monster.isEntityValid()) {
            return;
        }
        this.applyTime = System.currentTimeMillis();
        if (!potionEffects.isEmpty()) {
            final LivingEntity entity = monster.getEntity();
            for (final PotionEffect effect : potionEffects) {
                entity.addPotionEffect(effect);
            }
        }
    }

    /**
     * Attempts to remove this Effect from the given RPGEntity
     * <p>
     * @param entity this effect is being removed by.
     */
    @Override
    public final void remove(IEntity entity) {
        if (entity instanceof Champion) {
            removeFromPlayer((Champion) entity);
        } else if (entity instanceof Monster) {
            removeFromMonster((Monster) entity);
        }
    }

    /**
     * Version specific implementation for removing this effect from an RPGChampion
     *
     * @param player this effect is being removed from.
     */
    @Override
    public void removeFromPlayer(Champion player) {
        if (!player.isEntityValid()) {
            return;
        }
        final Player p = player.getPlayer();
        if (!potionEffects.isEmpty()) {
            for (final PotionEffect pEffect : potionEffects) {
                p.removePotionEffect(pEffect.getType());
            }
        }
        if (!mobEffects.isEmpty()) {
            CraftBukkitHandler nmsHandler = CraftBukkitHandler.getInterface();
            nmsHandler.removeFakePotionEffectPackets(mobEffects, p);
        }
    }

    /**
     * Version specific implementation for removing this effect from an RPGMonster
     * @param monster this effect is being removed from.
     */
    @Override
    public void removeFromMonster(Monster monster) {
        if (!monster.isEntityValid()) {
            return;
        }
        if (!potionEffects.isEmpty()) {
            final LivingEntity entity = monster.getEntity();
            for (final PotionEffect effect : potionEffects) {
                entity.removePotionEffect(effect.getType());
            }
        }
    }
}

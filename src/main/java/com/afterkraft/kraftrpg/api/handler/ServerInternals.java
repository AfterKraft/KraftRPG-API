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
package com.afterkraft.kraftrpg.api.handler;

import javax.xml.stream.Location;
import java.util.Map;
import java.util.Set;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.projectile.Arrow;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.potion.PotionEffectType;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.entity.damage.InsentientDamageEvent.DamageType;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.util.FixedPoint;
import com.afterkraft.kraftrpg.common.DamageCause;

/**
 * Standard utility class for handling version and platform specific code.
 */
public abstract class ServerInternals {
    public static final double UNSET_VALUE = Double.longBitsToDouble(0xcc123);

    protected static final String DAMAGE_STRING = "Damage";
    protected static final String EXPERIENCE_STRING = "Experience";
    protected static final String SPAWNX_STRING = "SpawnX";
    protected static final String SPAWNY_STRING = "SpawnY";
    protected static final String SPAWNZ_STRING = "SpawnZ";
    protected static final String SPAWNREASON_STRING = "SpawnReason";

    public static ServerType serverType;
    private static ServerInternals activeInterface;
    protected RPGPlugin plugin;

    protected ServerInternals(ServerType type) {
        serverType = type;
    }

    public static ServerInternals getInterface() {
        if (activeInterface == null) {
            // redo this later in the implementation.
        }
        return activeInterface;
    }

    protected static String getSoundName(EntityType type) {
        if (type == EntityTypes.BLAZE) {
            return "mob.blaze.death";
        } else if (type == EntityTypes.CHICKEN) {
            return "mob.chickenhurt";
        } else if (type == EntityTypes.CREEPER) {
            return "mob.creeperdeath";
        } else if (type == EntityTypes.MAGMA_CUBE) {
            return "mob.slime";
        } else if (type == EntityTypes.SLIME) {
            return "mob.slime";
        } else if (type == EntityTypes.SKELETON) {
            return "mob.skeletonhurt";
        } else if (type == EntityTypes.IRON_GOLEM) {
            return "mob.irongolem.death";
        } else if (type == EntityTypes.GHAST) {
            return "mob.ghast.death";
        } else if (type == EntityTypes.PIG) {
            return "mob.pigdeath";
        } else if (type == EntityTypes.OCELOT) {
            return "mob.cat.hitt";
        } else if (type == EntityTypes.SHEEP) {
            return "mob.sheep";
        } else if (type == EntityTypes.SPIDER) {
            return "mob.spiderdeath";
        } else if (type == EntityTypes.WOLF) {
            return "mob.wolf.death";
        } else if (type == EntityTypes.ZOMBIE) {
            return "mob.zombiedeath";
        } else
            return "damage.hurtflesh";
    }

    public abstract void loadExtraListeners();

    public abstract Location getSpawnLocation(Living entity);

    public abstract Cause getSpawnReason(Living entity, Cause provided);

    public abstract FixedPoint getMonsterExperience(Living entity, FixedPoint value);

    public abstract void setMonsterExperience(Living entity, FixedPoint experience);

    public abstract double getEntityDamage(Living entity, double calculated);

    /**
     * Check if a given EntityAttributeType is present in the given Living.
     *
     * @param entity entity to inspect
     * @param type   attribute to check
     *
     * @return true if set, false if not
     */
    public abstract boolean isAttributeSet(Living entity, EntityAttributeType type);

    /**
     * Get the value of an EntityAttributeType stored in the given entity.
     *
     * @param entity       entity to inspect
     * @param type         attribute to check
     * @param defaultValue value to return if not set
     *
     * @return double set value, or defaultValue if not
     */
    public abstract double getAttribute(Living entity, EntityAttributeType type,
                                        double defaultValue);

    /**
     * Set the value of an EntityAttributeType for the given entity.
     *
     * @param entity   entity to change
     * @param type     attribute to use
     * @param newValue value to set
     *
     * @return previous value, or the special value {@link #UNSET_VALUE} if not already set
     */
    public abstract double setAttribute(Living entity, EntityAttributeType type,
                                        double newValue);

    /**
     * Gets the stored value of an EntityAttributeType, or sets it to the given value if it wasn't
     * present before.
     *
     * @param entity       entity to inspect/change
     * @param type         attribute to use
     * @param valueIfEmpty value to set if empty
     *
     * @return current value
     */
    public abstract double getOrSetAttribute(Living entity, EntityAttributeType type,
                                             double valueIfEmpty);

    //NMS methods required by listeners
    public abstract double getPostArmorDamage(Living defender, double damage);


    public abstract void setPlayerExpZero(Player player);

    public abstract void modifyArrowDamage(Arrow arrow, double damage);

    //NMS methods required by skills

    public abstract boolean damageCheck(Insentient attacker, IEntity victim);

    public abstract void knockBack(Insentient target, Insentient attacker, double damage);

    public abstract void knockBack(Living target, Living attacker, double damage);

    public abstract boolean healEntity(Insentient being, double tickHealth, ISkill skill,
                                       Insentient applier);

    public abstract boolean damageEntity(Living target, Insentient attacker, 
                                         ISkill skill,
                                         double damage, DamageCause cause, boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                         double damage, DamageCause cause, boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                         Map<DamageType, Double> modifiers, DamageCause cause,
                                         boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                         Map<DamageType, Double> modifiers, DamageCause cause,
                                         boolean knockback, boolean ignoreDamageCheck);

    public abstract void refreshLastPlayerDamageTime(Living entity);


    //NMS methods required by effects
    public abstract void hidePlayer(Insentient player);

    public abstract void sendFakePotionEffectPacket(PotionEffect effect, Player player);

    public abstract void sendFakePotionEffectPackets(Set<PotionEffect> effects, Player player);

    public abstract void removeFakePotionEffectPacket(PotionEffect effect, Player player);

    public abstract void removeFakePotionEffectPackets(Set<PotionEffect> effects, Player player);

    //Bukkit specific NMS Requirements to fulfill deficiencies in API
    public abstract void setProjectileDamage(IEntity arrow, double damage);

    //Utility functions
    protected abstract float getSoundStrength(Living entity);

    public abstract void addNBTAttributes();

    public abstract int getArmorIndexHelmet();

    public abstract int getArmorIndexChestPlate();

    public abstract int getArmorIndexLeggings();

    public abstract int getArmorIndexBoots();

    public abstract Map<String, PotionEffectType> getAlternatePotionEffectNames();

    /**
     * List of acknowledged server types supported by KraftRPG.
     */
    public static enum ServerType {
        BUKKIT,
        SPIGOT,
        TWEAKKIT
    }
}

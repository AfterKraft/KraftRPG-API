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

import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.entity.damage.InsentientDamageEvent.DamageType;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.util.FixedPoint;


public abstract class ServerInternals {
    public static final double UNSET_VALUE = Double.longBitsToDouble(0xcc123);

    protected final static String DAMAGE_STRING = "Damage";
    protected final static String EXPERIENCE_STRING = "Experience";
    protected final static String SPAWNX_STRING = "SpawnX";
    protected final static String SPAWNY_STRING = "SpawnY";
    protected final static String SPAWNZ_STRING = "SpawnZ";
    protected final static String SPAWNREASON_STRING = "SpawnReason";

    public static ServerType serverType;
    private static ServerInternals activeInterface;
    protected RPGPlugin plugin;

    protected ServerInternals(ServerType type) {
        serverType = type;
    }

    public static ServerInternals getInterface() {
        if (activeInterface == null) {
            // Get minecraft version
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);
            if (version.equals("craftbukkit")) {
                version = "pre";
            }
            String serverString = Bukkit.getServer().getVersion().split("-")[1].toLowerCase();
            if (serverString.equalsIgnoreCase("bukkit") || serverString.equalsIgnoreCase("craftbukkit")) {
                serverType = ServerType.BUKKIT;
            } else if (serverString.equalsIgnoreCase("spigot")) {
                serverType = ServerType.SPIGOT;
            } else if (serverString.equalsIgnoreCase("tweakkit")) {
                serverType = ServerType.TWEAKKIT;
            }
            if (serverType == null) {
                Bukkit.getLogger().info("KraftRPG could not detect your server mod type.");
                Bukkit.getLogger().info("It detected " + serverString + " which isn't known to KraftRPG.");
                Bukkit.getLogger().info("But don't worry! We're falling back on Bukkit compatibility");
                serverType = ServerType.BUKKIT;
            }
            try {
                Class<?> clazz = Class.forName("com.afterkraft.kraftrpg.compat." + version + ".RPGHandler");
                if (ServerInternals.class.isAssignableFrom(clazz)) {
                    activeInterface = (ServerInternals) clazz.getConstructor(ServerType.class).newInstance(serverType);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return activeInterface;
    }

    protected static String getSoundName(EntityType type) {
        switch (type) {
            case BLAZE:
                return "mob.blaze.death";
            case CHICKEN:
                return "mob.chickenhurt";
            case CREEPER:
                return "mob.creeperdeath";
            case MAGMA_CUBE:
            case SLIME:
                return "mob.slime";
            case SKELETON:
                return "mob.skeletonhurt";
            case IRON_GOLEM:
                return "mob.irongolem.death";
            case GHAST:
                return "mob.ghast.death";
            case PIG:
                return "mob.pigdeath";
            case OCELOT:
                return "mob.cat.hitt";
            case SHEEP:
                return "mob.sheep";
            case SPIDER:
                return "mob.spiderdeath";
            case WOLF:
                return "mob.wolf.death";
            case ZOMBIE:
                return "mob.zombiedeath";
            default:
                return "damage.hurtflesh";
        }
    }

    public abstract void loadExtraListeners();

    public abstract Location getSpawnLocation(LivingEntity entity);

    public abstract CreatureSpawnEvent.SpawnReason getSpawnReason(LivingEntity entity, SpawnReason provided);

    public abstract FixedPoint getMonsterExperience(LivingEntity entity, FixedPoint value);

    public abstract void setMonsterExperience(LivingEntity entity, FixedPoint experience);

    public abstract double getEntityDamage(LivingEntity entity, double calculated);

    /**
     * Check if a given EntityAttributeType is present in the given
     * LivingEntity.
     *
     * @param entity entity to inspect
     * @param type   attribute to check
     * @return true if set, false if not
     */
    public abstract boolean isAttributeSet(LivingEntity entity, EntityAttributeType type);

    /**
     * Get the value of an EntityAttributeType stored in the given entity.
     *
     * @param entity       entity to inspect
     * @param type         attribute to check
     * @param defaultValue value to return if not set
     * @return double set value, or defaultValue if not
     */
    public abstract double getAttribute(LivingEntity entity, EntityAttributeType type, double defaultValue);

    /**
     * Set the value of an EntityAttributeType for the given entity.
     *
     * @param entity   entity to change
     * @param type     attribute to use
     * @param newValue value to set
     * @return previous value, or the special value {@link #UNSET_VALUE} if
     * not already set
     */
    public abstract double setAttribute(LivingEntity entity, EntityAttributeType type, double newValue);

    /**
     * Gets the stored value of an EntityAttributeType, or sets it to the
     * given value if it wasn't present before.
     *
     * @param entity       entity to inspect/change
     * @param type         attribute to use
     * @param valueIfEmpty value to set if empty
     * @return current value
     */
    public abstract double getOrSetAttribute(LivingEntity entity, EntityAttributeType type, double valueIfEmpty);

    //NMS methods required by listeners
    public abstract double getPostArmorDamage(LivingEntity defender, double damage);

    public abstract double getPostArmorDamage(Insentient being, EntityDamageEvent event, double damage);

    public abstract void setPlayerExpZero(Player player);

    public abstract void modifyArrowDamage(Arrow arrow, double damage);

    //NMS methods required by skills

    public abstract boolean damageCheck(Insentient attacker, IEntity victim);

    public abstract void knockBack(Insentient target, Insentient attacker, double damage);

    public abstract void knockBack(LivingEntity target, LivingEntity attacker, double damage);

    public abstract boolean healEntity(Insentient being, double tickHealth, ISkill skill, Insentient applier);

    public abstract boolean damageEntity(LivingEntity target, Insentient attacker, ISkill skill, double damage, DamageCause cause, boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill, double damage, DamageCause cause, boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill, Map<DamageType, Double> modifiers, DamageCause cause, boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill, Map<DamageType, Double> modifiers, DamageCause cause, boolean knockback, boolean ignoreDamageCheck);

    public abstract void refreshLastPlayerDamageTime(LivingEntity entity);


    //NMS methods required by effects
    public abstract void hidePlayer(Insentient player);

    public abstract void sendFakePotionEffectPacket(PotionEffect effect, Player player);

    public abstract void sendFakePotionEffectPackets(Set<PotionEffect> effects, Player player);

    public abstract void removeFakePotionEffectPacket(PotionEffect effect, Player player);

    public abstract void removeFakePotionEffectPackets(Set<PotionEffect> effects, Player player);

    //Bukkit specific NMS Requirements to fulfill deficiencies in API
    public abstract void setProjectileDamage(IEntity arrow, double damage);

    //Utility functions
    protected abstract float getSoundStrength(LivingEntity entity);

    public abstract void playClientEffect(Player player, Location startLocation, String particle, Vector offset, float speed, int count, boolean sendToAll);

    public abstract Conversation getCurrentConversation(Player player);

    public abstract Prompt getCurrentPrompt(Conversation conversation);

    public abstract void addNBTAttributes();

    public abstract int getArmorIndexHelmet();

    public abstract int getArmorIndexChestPlate();

    public abstract int getArmorIndexLeggings();

    public abstract int getArmorIndexBoots();

    public abstract Map<String, PotionEffectType> getAlternatePotionEffectNames();

    public static enum ServerType {
        BUKKIT,
        SPIGOT,
        TWEAKKIT
    }
}

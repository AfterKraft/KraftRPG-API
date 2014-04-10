package com.afterkraft.kraftrpg.api.handler;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import com.afterkraft.kraftrpg.api.entity.Monster;

/**
 * @author gabizou
 */
public abstract class CraftBukkitHandler {

    private static CraftBukkitHandler activeInterface;
    protected static ServerType serverType;

    public static CraftBukkitHandler getInterface() {
        if(activeInterface == null) {
            //Get minecraft version
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);
            if (version.equals("craftbukkit")) {
                version = "pre";
            }
            final String serverString = Bukkit.getServer().getVersion().split("-")[1].toLowerCase();
            if (serverString.equalsIgnoreCase("craftbukkit")) {
                serverType = ServerType.BUKKIT;
            } else if (serverString.equalsIgnoreCase("spigot")) {
                serverType = ServerType.SPIGOT;
            } else if (serverString.equalsIgnoreCase("tweakkit")) {
                serverType = ServerType.TWEAKKIT;
            }
            try {
                final Class<?> clazz = Class.forName("com.afterkraft.kraftrpg.compat.v_" + version + ".RPGHandler");
                if (CraftBukkitHandler.class.isAssignableFrom(clazz)) {
                    activeInterface = (CraftBukkitHandler) clazz.getConstructor(ServerType.class).newInstance(serverType);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return activeInterface;
    }

    public CraftBukkitHandler(ServerType type) {
        serverType = type;
    }

    //NMS methods required by Entities
    public abstract EntityAttributeModifier getEntityAttribute(UUID uuid, String name);
    public abstract double loadOrCreate(EntityAttribute attribute, LivingEntity entity, double value);
    public abstract double loadOrCreateAttribute(Monster monster, LivingEntity entity, EntityAttribute.EntityAttributeType type, double value);

    //NMS methods required by listeners
    public abstract double getPostArmorDamage(LivingEntity defender, double damage);
    public abstract void setPlayerExpZero(Player player);

    //NMS methods required by skills
    public abstract boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause, boolean knockback);
    public abstract void knockBack(LivingEntity target, LivingEntity attacker, double damage);
    public abstract void refreshLastPlayerDamageTime(LivingEntity entity);

    //NMS methods required by effects
    public abstract void sendFakePotionEffectPacket(PotionEffect effect, Player player);
    public abstract void sendFakePotionEffectPackets(Set<PotionEffect> effects, Player player);
    public abstract void removeFakePotionEffectPacket(PotionEffect effect, Player player);
    public abstract void removeFakePotionEffectPackets(Set<PotionEffect> effects, Player player);

    //Bukkit specific NMS Requirements to fulfill deficiencies in API
    public abstract void setArrowDamage(Arrow arrow, double damage);

    //Utility functions
    protected abstract float getSoundStrength(LivingEntity entity);

    protected static String getSoundName(EntityType type) {
        switch(type) {
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

    public abstract void playClientEffect(Player player, Location startLocation, String particle, Vector offset, float speed, int count, boolean sendToAll);

    public static enum ServerType {
        BUKKIT,
        SPIGOT,
        TWEAKKIT
    }
}
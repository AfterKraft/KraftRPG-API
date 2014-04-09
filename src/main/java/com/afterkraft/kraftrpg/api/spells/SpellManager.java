package com.afterkraft.kraftrpg.api.spells;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Author: gabizou
 */
public abstract class SpellManager {

    public abstract Spell<? extends Spell.SpellArgument> getSpell(String name);

    public abstract void shutdown();


    public abstract void knockback(Spell spell, LivingEntity target, LivingEntity attacker, double damage);

    public final boolean damageEntity(Spell spell, LivingEntity target, LivingEntity attacker, double damage) {
        return damageEntity(spell, target, attacker, damage,
                spell.isType(SpellType.ABILITY_PROPERTY_AIR) || spell.isType(SpellType.ABILITY_PROPERTY_PHYSICAL) ?
                        EntityDamageEvent.DamageCause.ENTITY_ATTACK : EntityDamageEvent.DamageCause.MAGIC);
    }

    public final boolean damageEntity(Spell spell, LivingEntity target, LivingEntity attacker, double damage,
                                      EntityDamageEvent.DamageCause cause) {
        return damageEntity(spell, target, attacker, damage, cause, true);
    }

    public abstract boolean damageEntity(Spell spell,LivingEntity target, LivingEntity attacker, double damage,
                                         EntityDamageEvent.DamageCause cause, boolean knockback);

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

    protected abstract float getSoundStrength(Object entity);
}

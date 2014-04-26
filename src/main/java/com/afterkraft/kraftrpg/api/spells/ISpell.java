package com.afterkraft.kraftrpg.api.spells;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;

/**
 * Represents a usable Spell for KraftRPG.
 * The recommended use of implementation is {@link com.afterkraft.kraftrpg.api.spells.Spell}
 */
public interface ISpell {

    /**
     * Returns the generated permission node as a string for this ISpell.
     * @return the string name permission node for this ISpell
     */
    public String getPermissionNode();

    /**
     * Return the unique name of this ISpell
     * @return the name of this spell
     */
    public String getName();

    /**
     * Returns the default configuration for this spell.
     * @return the defalt configuration for this spell
     */
    public Configuration getDefaultConfig();

    /**
     * Return the description for this spell. This should be unique for every spell
     * @return the description for this spell
     */
    public String getDescription();

    /**
     * Set the description for this spell.
     * @param description the description for this spell
     */
    public void setDescription(String description);

    /**
     * Similar to {@link org.bukkit.plugin.Plugin#onEnable()}, should be used
     * to register any necessary listeners and managers for active use.
     */
    public void initialize();

    /**
     * Similar to {@link org.bukkit.plugin.Plugin#onDisable()}, should be
     * used to unregister any listeners and be prepared for removal.
     */
    public void shutdown();

    /**
     * Adds an Entity as a spell target.
     * @param entity to add as a target
     * @param champion the Champion using the Spell
     * @return true if successful
     */
    public boolean addSpellTarget(Entity entity, Champion champion);

    /**
     * Check if this ISpell is of the requested {@link SpellType}
     * @param type the type to check
     * @return true if this spell is of the requested type
     */
    public boolean isType(SpellType type);

    /**
     * Utility method to knockback the {@link org.bukkit.entity.LivingEntity} that is targetted from
     * the direction of the attacking LivingEntity
     * @param target the target to knockback
     * @param attacker the attacker to make seemingly performing the knockback
     * @param damage the damage to deal from the knockback
     */
    public void knockback(LivingEntity target, LivingEntity attacker, double damage);

    /**
     * Utility method to deal damage to the targetted {@link org.bukkit.entity.LivingEntity} by the attacker.
     * This will set the last damage cause and all other relations properly acting as if the
     * attacker indeed did attack the target.
     * @param target the targetted LivingEntity to deal damage
     * @param attacker the attacking LivingEntity
     * @param damage total damage to deal to the target
     * @return true if successful or if damage was not cancelled
     */
    public boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage);

    /**
     * Utility method to deal damage to the targetted {@link org.bukkit.entity.LivingEntity} by the attacker.
     * This will set the last damage cause and all other relations properly acting as if the
     * attacker indeed did attack the target.
     * @param target the targetted LivingEntity to deal damage
     * @param attacker the attacking LivingEntity
     * @param damage total damage to deal to the target
     * @param cause the actual damage cause to call for reason
     * @return true if successful or if damage was not cancelled
     */
    public boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause);

    /**
     * Utility method to deal damage to the targetted {@link org.bukkit.entity.LivingEntity} by the attacker.
     * This will set the last damage cause and all other relations properly acting as if the
     * attacker indeed did attack the target.
     * @param target the targetted LivingEntity to deal damage
     * @param attacker the attacking LivingEntity
     * @param damage total damage to deal to the target
     * @param cause the actual damage cause to call for reason
     * @param knockback if true, knockback the targetted entity away from the attacker
     * @return true if successful or if damage was not cancelled
     */
    public boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause, boolean knockback);

    /**
     *
     * @param broadcaster
     * @param receiver
     * @return
     */
    public boolean isInMessageRange(Champion broadcaster, Champion receiver);

    /**
     *
     * @param attacker
     * @param defending
     * @return
     */
    public boolean damageCheck(IEntity attacker, LivingEntity defending);

    public void awardExperience(Champion champion);
}

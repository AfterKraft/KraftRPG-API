package com.afterkraft.kraftrpg.api.spells;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler;

/**
 * Represents an intended implementation of ISpell.
 */
public abstract class Spell<T extends SpellArgument> implements ISpell<T> {

    public final RPGPlugin plugin;
    private final Configuration defaultConfig = new MemoryConfiguration();
    private final Set<SpellType> spellTypes = EnumSet.noneOf(SpellType.class);
    private final String name;
    private String description = "";
    private String usage = "";
    private boolean isEnabled = false;

    public Spell(RPGPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    /**
     * Return whether this Spell is enabled or not
     * @return whether this spell is enabled
     */
    public final boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * Sets the enabled state of this Spell
     *
     * @param enabled whether or not to set this spell as enabled or not
     */
    public final void setEnabled(final boolean enabled) {
        if (isEnabled != enabled) {
            isEnabled = enabled;

            if (isEnabled) {
                initialize();
            } else {
                shutdown();
            }
        }
    }

    public final String getPermissionNode() {
        return "kraftrpg.spell." + this.getName();
    }

    public final String getName() {
        return this.name;
    }

    public final Configuration getDefaultConfig() {
        return this.defaultConfig;
    }

    public final String getUsage() {
        return this.usage;
    }

    public final void setUsage(String usage) {
        this.usage = usage;
    }

    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final boolean isType(SpellType type) {
        return this.spellTypes.contains(type);
    }

    /**
     * Set this Spell's spell types.
     * @param types the SpellTypes to set
     */
    protected final void setSpellTypes(SpellType... types) {
        this.spellTypes.addAll(Arrays.asList(types));
    }

    @Override
    public boolean addSpellTarget(Entity entity, Champion champion) {
        if (entity == null || champion == null || !champion.isEntityValid()) {
            return false;
        }
        plugin.getSpellManager().addSpellTarget(entity, champion, this);
        return true;
    }

    @Override
    public final void knockback(LivingEntity target, LivingEntity attacker, double damage) {
        CraftBukkitHandler.getInterface().knockBack(target, attacker, damage);
    }

    @Override
    public final boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage) {
        return this.damageEntity(target, attacker, damage,
                this.isType(SpellType.ABILITY_PROPERTY_AIR) || this.isType(SpellType.ABILITY_PROPERTY_PHYSICAL) ?
                        EntityDamageEvent.DamageCause.ENTITY_ATTACK : EntityDamageEvent.DamageCause.MAGIC
        );
    }

    @Override
    public final boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause) {
        return this.damageEntity(target, attacker, damage, cause, true);
    }

    @Override
    public final boolean damageEntity(LivingEntity target, LivingEntity attacker, double damage, EntityDamageEvent.DamageCause cause, boolean knockback) {
        return CraftBukkitHandler.getInterface().damageEntity(target, attacker, damage, cause, knockback);
    }
}

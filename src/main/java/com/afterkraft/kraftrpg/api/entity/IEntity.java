package com.afterkraft.kraftrpg.api.entity;

import java.util.Set;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.effects.IEffect;

/**
 * Represents a living entity in KraftRPG. This is the base entity for KraftRPG.
 */
public interface IEntity {

    /**
     * Get the Bukkit name of this Entity
     *
     * @return the Bukkit name of this Entity
     */
    public String getName();

    /**
     * Check if the attached {@link org.bukkit.entity.LivingEntity} is still valid
     * and not null
     *
     * @return true if the LivingEntity is not null
     */
    public boolean isValid();

    /**
     * Check if the {@link org.bukkit.entity.LivingEntity#isValid()}. This also checks
     * if the reference of the entity {@link #isValid()}.
     *
     * @return true if the LivingEntity is alive and valid
     */
    public boolean isEntityValid();

    /**
     * Returns the LivingEntity if the LivingEntity {@link #isEntityValid()}.
     *
     * @return the linked LivingEntity if not null
     */
    public LivingEntity getEntity();

    /**
     * Reset the linked LivingEntity to the provided {@link org.bukkit.entity.LivingEntity} if the
     * {@link java.util.UUID} match for the old reference and the provided reference
     *
     * @param entity the LivingEntity to re-attach this IEntity to
     * @return true if successful, false if UUID did not match
     */
    public boolean setEntity(LivingEntity entity);

    /**
     * Returns (if available) the named {@link com.afterkraft.kraftrpg.api.entity.effects.IEffect}
     *
     * @param name the name of the desired Effect
     * @return the named Effect if not null
     */
    public IEffect getEffect(String name);

    /**
     * Returns an unmodifiable set of Effects this Champion has active.
     *
     * @return an unmodifiable set of Effects this Champion has active.
     */
    public Set<IEffect> getEffects();

    /**
     * Adds the given Effect to this Champion. Added Effects will be applied on the next tick so as to avoid
     *
     * @param IEffect
     */
    public void addEffect(IEffect IEffect);

    public boolean hasEffect(String name);

    public boolean hasEffectType(EffectType type);

    public void removeEffect(IEffect IEffect);

    public void manualRemoveEffect(IEffect IEffect);

    public void clearEffects();

    public void manualClearEffects();

    public boolean addMaxHealth(String key, double value);

    public boolean removeMaxHealth(String key);

    public void heal(double amount);

    public void clearHealthBonuses();

}

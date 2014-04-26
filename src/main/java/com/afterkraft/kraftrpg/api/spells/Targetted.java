package com.afterkraft.kraftrpg.api.spells;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * Represents an Active Spell that requires a {@link org.bukkit.entity.LivingEntity} as a target.
 */
public interface Targetted<T extends TargettedSpellArgument> extends Active<T> {

    /**
     * Primary designated method to use this spell on the targetted LivingEntity
     * @param champion
     * @param entity
     * @param argument
     * @return
     */
    public SpellCastResult use(Champion champion, LivingEntity entity, T argument);

    public SpellCastResult useDelayed(Champion champion, DelayedTargetedSpell<T> spell, T args);

}

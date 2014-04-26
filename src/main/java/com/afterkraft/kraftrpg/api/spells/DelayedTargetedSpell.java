package com.afterkraft.kraftrpg.api.spells;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.SpellCaster;

/**
 * @author gabizou
 */
public class DelayedTargetedSpell<T extends TargettedSpellArgument> extends DelayedSpell<T> implements DelayedTarget<T> {

    private LivingEntity target;

    public DelayedTargetedSpell(Active<T> spell, T args, SpellCaster caster, LivingEntity target, long startTime, long warmup) {
        super(spell, args, caster, startTime, warmup);
        this.target = target;
    }

    @Override
    public LivingEntity getTarget() {
        return target;
    }
}
package com.afterkraft.kraftrpg.api.spells;

import org.bukkit.entity.LivingEntity;

/**
 * @author gabizou
 */
public interface DelayedTarget<T extends SpellArgument> extends Delayed<T> {

    public LivingEntity getTarget();
}

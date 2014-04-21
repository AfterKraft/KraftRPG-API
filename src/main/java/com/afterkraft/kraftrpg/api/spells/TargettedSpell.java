package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.RPGPlugin;

/**
 * A spell that requires a {@link org.bukkit.entity.LivingEntity} as a target. Targetted spells
 * do
 *
 */
public abstract class TargettedSpell<T extends TargettedSpellArgument> extends ActiveSpell<T> implements Targetted<T> {

    public TargettedSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
    }


}

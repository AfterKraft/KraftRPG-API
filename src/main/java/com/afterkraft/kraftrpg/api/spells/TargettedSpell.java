package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.RPGPlugin;

/**
 * @author gabizou
 */
public abstract class TargettedSpell<T extends TargettedSpellArgument> extends Spell<T> {

    public TargettedSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
    }
}

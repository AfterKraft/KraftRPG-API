package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.effects.EffectType;

/**
 * @author gabizou
 */
public abstract class PassiveSpell<T extends SpellArgument> extends Spell<T> implements Passive {

    private String applyText = null;
    private String unapplyText = null;
    private EffectType[] effectTypes = null;

    public PassiveSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
        setUsage("Passive Spell!");
    }
}

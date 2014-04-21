package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.effects.EffectType;

/**
 * Default implementation of a Spell with the Passive interface
 */
public abstract class PassiveSpell extends Spell implements Passive {

    private String applyText = null;
    private String unapplyText = null;
    private EffectType[] effectTypes = null;

    public PassiveSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
    }
}

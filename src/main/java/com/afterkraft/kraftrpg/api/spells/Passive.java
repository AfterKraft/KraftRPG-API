package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.entity.SpellCaster;

/**
 * Represents a passive spell that can not be activated by command or bind. The
 * recommended use of implementation is {@link com.afterkraft.kraftrpg.api.spells.PassiveSpell}
 */
public interface Passive extends ISpell {

    /**
     * Attempts to apply this passive spell to the given {@link
     * com.afterkraft.kraftrpg.api.entity.SpellCaster}
     *
     * @param caster to attempt to apply this passive spell to
     */
    public void tryApplying(SpellCaster caster);
}

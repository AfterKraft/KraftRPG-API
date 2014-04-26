package com.afterkraft.kraftrpg.api.spells;

import java.util.Collection;

import org.bukkit.entity.Entity;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.SpellCaster;

/**
 * @author gabizou
 */
public interface SpellManager extends Manager {

    /**
     * Adds a spell to the spell mapping
     *
     * @param spell
     */
    public void addSpell(ISpell spell);

    /**
     * Returns a spell from it's name
     * If the spell is not in the spell mapping it will attempt to load it from file
     *
     * @param name
     * @return
     */
    public ISpell getSpell(String name);

    public boolean loadOutsourcedSpell(String name);
    /**
     * Returns a collection of all spells loaded in the spell manager
     *
     * @return
     */
    public Collection<ISpell> getSpells();

    /**
     * Checks if a spell has already been loaded
     *
     * @param name
     * @return
     */
    public boolean isLoaded(String name);

    /**
     * Removes a spell from the spell mapping
     *
     * @param spell
     */
    public void removeSpell(ISpell spell);

    /**
     * Check if the {@link com.afterkraft.kraftrpg.api.entity.SpellCaster} has an actively {@link com.afterkraft.kraftrpg.api.spells.Delayed} Spell.
     *
     * @param caster the caster in question
     * @return
     */
    public boolean isCasterDelayed(SpellCaster caster);

    /**
     *
     * @param caster
     * @return
     */
    public Delayed<? extends SpellArgument> getDelayedSpell(SpellCaster caster);

    public void setCompletedSpell(SpellCaster caster);

    public void addSpellTarget(Entity o, SpellCaster caster, ISpell skill);

    public SpellUseObject<? extends SpellArgument> getSpellTargetInfo(Entity o);

    public boolean isSpellTarget(Entity o);
}

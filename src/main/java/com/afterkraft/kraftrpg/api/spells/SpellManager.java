package com.afterkraft.kraftrpg.api.spells;

import java.util.Collection;

import com.afterkraft.kraftrpg.api.Manager;

/**
 * @author gabizou
 */
public interface SpellManager extends Manager {

    /**
     * Adds a spell to the spell mapping
     *
     * @param spell
     */
    public void addSpell(Spell<? extends SpellArgument> spell);

    /**
     * Returns a spell from it's name
     * If the spell is not in the spell mapping it will attempt to load it from file
     *
     * @param name
     * @return
     */
    public Spell<? extends SpellArgument> getSpell(String name);

    public boolean loadOutsourcedSpell(String name);
    /**
     * Returns a collection of all spells loaded in the spell manager
     *
     * @return
     */
    public Collection<Spell<? extends SpellArgument>> getSpells();
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
    public void removeSpell(Spell<? extends SpellArgument> spell);
}

package com.afterkraft.kraftrpg.api.spells;

/**
 * @author gabizou
 */
public class SpellBind {

    private final String spellName;
    private final Spell.SpellArgument arguments;

    public SpellBind(String spellName, Spell.SpellArgument argument) {
        this.spellName = spellName;
        this.arguments = argument;
    }

    public String getSpellName() {
        return this.spellName;
    }

    public Spell.SpellArgument getSpellArgument() {
        return this.arguments;
    }
}

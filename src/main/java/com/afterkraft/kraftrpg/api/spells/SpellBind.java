package com.afterkraft.kraftrpg.api.spells;

/**
 * @author gabizou
 */
public class SpellBind {

    private final String spellName;
    private final SpellArgument arguments;

    public SpellBind(String spellName, SpellArgument argument) {
        this.spellName = spellName;
        this.arguments = argument;
    }

    public String getSpellName() {
        return this.spellName;
    }

    public SpellArgument getSpellArgument() {
        return this.arguments;
    }
}

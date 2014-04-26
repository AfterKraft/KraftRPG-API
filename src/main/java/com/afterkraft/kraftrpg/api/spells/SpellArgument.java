package com.afterkraft.kraftrpg.api.spells;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SpellArgument is a raw instance Object that allows saving the state of a
 * Spell, either through binds or other systems. The raw arguments originally
 * passed to the
 * {@link Active#parse(com.afterkraft.kraftrpg.api.entity.SpellCaster, String[])}
 * method is kept for storage purposes and allows for a Spell to re-generate the
 * SpellArgument state after a Player login.
 */
public abstract class SpellArgument {

    private final String[] rawArgs;

    private SpellArgument() {
        this.rawArgs = new String[0];
    }

    public SpellArgument(String[] args) {
        this.rawArgs = args;
    }

    /**
     * A Useful in differentiating between SpellArguments.
     * 
     * @return the given name of this SpellArgument
     */
    public abstract String getName();

    /**
     * Return a copy of the original arguments parsed by this SpellArgument's
     * Spell. This is safe to use for storage purposes.
     * <p>
     *
     * @return an unmodifiable List of raw arguments
     */
    public final List<String> getRawArguments() {
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(
                this.rawArgs, this.rawArgs.length)));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SpellArgument)) {
            return false;
        }
        if (!(object.getClass().getName().equalsIgnoreCase(this.getClass().getName()))) {
            return false;
        }
        SpellArgument arg = (SpellArgument) object;
        return arg.rawArgs == null && this.rawArgs == null || Arrays.equals(this.rawArgs, arg.rawArgs);
    }
}
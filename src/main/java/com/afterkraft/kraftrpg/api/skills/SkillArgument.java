/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.api.skills;

import java.util.List;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * SkillArgument is a raw instance Object that allows saving the state of a Skill, either through
 * binds or other systems. The raw arguments originally passed to the {@link
 * Active#parse(com.afterkraft.kraftrpg.api.entity.SkillCaster, String[])} method is kept for
 * storage purposes and allows for a Skill to re-generate the SkillArgument state after a Player
 * login.
 */
public abstract class SkillArgument<T> {
    private final boolean required;
    protected boolean present;

    protected SkillArgument() {
        this(false);
    }

    protected SkillArgument(boolean required) {
        this.required = required;
    }

    public boolean isOptional() {
        return !this.required;
    }

    // parsed state
    public boolean isPresent() {
        return this.present;
    }

    /**
     * Return a string suitable for inclusion in a usage string. No colors, please.
     *
     * @param optional if this SkillArgument isOptional()
     *
     * @return partial usage string
     */
    public abstract String getUsageString(boolean optional);

    /**
     * Check if this SkillArgument is satisfied by the arguments in allArgs starting at index
     * startPosition, and return the width of this SkillArgument. <b>This will be called during both
     * parsing and tab completion</b>, so make sure to provide a complete implementation.
     *
     * @param caster        The caster using this argument
     * @param allArgs       Full arguments array
     * @param startPosition Where your arguments start
     *
     * @return Negative if no match, or number of args consumed if matched
     */
    public abstract int matches(SkillCaster caster, String[] allArgs, int startPosition);

    /**
     * Overwrite your state with that of the provided arguments starting at index startPosition.
     * This will never be called unless matches() returns true.
     *
     * @param caster        caster
     * @param allArgs       Full arguments array
     * @param startPosition Where your arguments start
     */
    public abstract void parse(SkillCaster caster, String[] allArgs, int startPosition);

    /**
     * This method is called instead of parse() when an optional SkillArgument is skipped (i.e. not
     * matched).
     *
     * @param caster caster
     */
    public abstract void skippedOptional(SkillCaster caster);

    public abstract Optional<T> getValue();

    /**
     * Erase the parsed state to blank values.
     */
    public abstract void clean();

    /**
     * Provide tab-completion suggestions for the last item in allArgs. Your arguments start at
     * startPosition.
     *
     * @param caster        trying to tab complete
     * @param allArgs       Full arguments array
     * @param startPosition Where your arguments start
     *
     * @return completion suggestions for the last item in allArgs
     */
    public abstract List<String> tabComplete(SkillCaster caster, String[] allArgs,
                                             int startPosition);
}

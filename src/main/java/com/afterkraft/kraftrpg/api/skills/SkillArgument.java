/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.skills;

import java.util.List;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * SkillArgument is a raw instance Object that allows saving the state of a
 * Skill, either through binds or other systems. The raw arguments originally
 * passed to the
 * {@link Active#parse(com.afterkraft.kraftrpg.api.entity.SkillCaster, String[])}
 * method is kept for storage purposes and allows for a Skill to re-generate
 * the SkillArgument state after a Player login.
 */
public abstract class SkillArgument {
    private final boolean required;
    protected boolean present;

    public SkillArgument() {
        this(false);
    }

    public SkillArgument(boolean required) {
        this.required = required;
    }

    public boolean isOptional() {
        return !required;
    }

    // parsed state
    public boolean isPresent() {
        return present;
    }

    /**
     * Return a string suitable for inclusion in a usage string. No colors,
     * please.
     * 
     * @param optional if this SkillArgument isOptional()
     * @return partial usage string
     */
    public abstract String getUsageString(boolean optional);

    /**
     * Check if this SkillArgument is satisfied by the arguments in allArgs
     * starting at index startPosition, and return the width of this
     * SkillArgument. <b>This will be called during both parsing and tab
     * completion</b>, so make sure to provide a complete implementation.
     * 
     * @param allArgs Full arguments array
     * @param startPosition Where your arguments start
     * @return Negative if no match, or number of args consumed if matched
     */
    public abstract int matches(SkillCaster caster, String[] allArgs, int startPosition);

    /**
     * Overwrite your state with that of the provided arguments starting at
     * index startPosition. This will never be called unless matches() returns
     * true.
     * 
     * @param caster caster
     * @param allArgs Full arguments array
     * @param startPosition Where your arguments start
     */
    public abstract void parse(SkillCaster caster, String[] allArgs, int startPosition);

    /**
     * This method is called instead of parse() when an optional SkillArgument
     * is skipped (i.e. not matched).
     * 
     * @param caster caster
     */
    public abstract void skippedOptional(SkillCaster caster);

    /**
     * Erase the parsed state to blank values.
     */
    public abstract void clean();

    /**
     * Provide tab-completion suggestions for the last item in allArgs. Your
     * arguments start at startPosition.
     * 
     * @param caster trying to tab complete
     * @param allArgs Full arguments array
     * @param startPosition Where your arguments start
     * @return completion suggestions for the last item in allArgs
     */
    public abstract List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition);
}

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
    private final boolean optional;
    protected boolean present;

    public SkillArgument() {
        this(false);
    }

    public SkillArgument(boolean optional) {
        this.optional = optional;
    }

    public boolean isOptional() {
        return optional;
    }

    // parsed state
    public boolean isPresent() {
        return present;
    }

    /**
     * Check if this SkillArgument is satisfied by the arguments in allArgs
     * starting at index startPosition.
     *
     * @param allArgs Full arguments array
     * @param startPosition Where your arguments start
     * @return true if parsing is possible
     */
    public abstract boolean matches(SkillCaster caster, String[] allArgs, int startPosition);

    /**
     * Overwrite your state with that of the provided arguments starting at
     * index startPosition.
     *
     * @param allArgs Full arguments array
     * @param startPosition Where your arguments start
     * @return number of arguments consumed
     */
    public abstract int parse(SkillCaster caster, String[] allArgs, int startPosition);

    /**
     * Provide tab-completion suggestions for the last item in allArgs. Your
     * arguments start at startPosition.
     *
     * @param allArgs Full arguments array
     * @param startPosition Where your arguments start
     * @return completion suggestions for the last item in allArgs
     */
    public abstract List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition);
}

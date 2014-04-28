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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SkillArgument is a raw instance Object that allows saving the state of a
 * Skill, either through binds or other systems. The raw arguments originally
 * passed to the {@link Active#parse(com.afterkraft.kraftrpg.api.entity.SkillCaster,
 * String[])} method is kept for storage purposes and allows for a Skill to
 * re-generate the SkillArgument state after a Player login.
 */
public abstract class SkillArgument {

    private final String[] rawArgs;

    private SkillArgument() {
        this.rawArgs = new String[0];
    }

    public SkillArgument(String[] args) {
        this.rawArgs = args;
    }

    /**
     * A Useful in differentiating between SkillArguments.
     *
     * @return the given name of this SkillArgument
     */
    public abstract String getName();

    /**
     * Return a copy of the original arguments parsed by this SkillArgument's
     * Skill. This is safe to use for storage purposes.
     * <p/>
     *
     * @return an unmodifiable List of raw arguments
     */
    public final List<String> getRawArguments() {
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.rawArgs, this.rawArgs.length)));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SkillArgument)) {
            return false;
        }
        if (!(object.getClass().getName().equalsIgnoreCase(this.getClass().getName()))) {
            return false;
        }
        SkillArgument arg = (SkillArgument) object;
        return arg.rawArgs == null && this.rawArgs == null || Arrays.equals(this.rawArgs, arg.rawArgs);
    }
}
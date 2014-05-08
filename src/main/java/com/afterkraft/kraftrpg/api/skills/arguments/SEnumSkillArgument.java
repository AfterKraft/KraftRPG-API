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
package com.afterkraft.kraftrpg.api.skills.arguments;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;

public class SEnumSkillArgument extends SkillArgument {
    private final String def;
    private final String[] choices;

    private String choice = null;

    public SEnumSkillArgument(boolean required, String def, String... choices) {
        super(required);
        this.def = def;
        this.choices = choices;
    }

    public String getChoice() {
        return choice;
    }

    // --------------------------------------------------------------

    @Override
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        if (ArrayUtils.contains(choices, arg)) {
            return 1;
        }
        return -1;
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        if (ArrayUtils.contains(choices, arg)) {
            choice = arg;
        } else {
            choice = def;
        }
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        choice = def;
    }

    @Override
    public void clean() {
        choice = def;
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition) {
        // TODO Auto-generated method stub
        return null;
    }
}

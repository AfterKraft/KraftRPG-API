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

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;
import com.afterkraft.kraftrpg.api.util.Utilities;

public class SEnumSkillArgument extends SkillArgument {
    private final String def;
    private final String[] choices;
    private final String usage;

    private String choice = null;

    public SEnumSkillArgument(boolean required, String def, String... choices) {
        super(required);
        this.def = def;
        this.choices = choices;

        StringBuilder sb = new StringBuilder(!required ? '[' : '<');
        for (String s : choices) {
            sb.append(s);
            sb.append('|');
        }
        if (choices.length != 0) {
            sb.setLength(sb.length() - 1);
        }
        sb.append(!required ? ']' : '>');
        this.usage = sb.toString();
    }

    public String getChoice() {
        return this.choice;
    }

    public boolean setChoice(String s) {
        if (ArrayUtils.contains(this.choices, s)) {
            this.choice = s;
            return true;
        }
        return false;
    }

    // --------------------------------------------------------------

    @Override
    public String getUsageString(boolean optional) {
        return this.usage;
    }

    @Override
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        if (ArrayUtils.contains(this.choices, arg)) {
            return 1;
        }
        return -1;
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        if (ArrayUtils.contains(this.choices, arg)) {
            this.choice = arg;
        } else {
            this.choice = this.def;
        }
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        this.choice = this.def;
    }

    @Override
    public void clean() {
        this.choice = this.def;
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition) {
        return Utilities.findMatches(allArgs[startPosition], Arrays.asList(this.choices));
    }
}

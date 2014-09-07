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

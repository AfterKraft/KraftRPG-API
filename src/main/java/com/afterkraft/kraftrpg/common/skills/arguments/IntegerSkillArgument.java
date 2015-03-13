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
package com.afterkraft.kraftrpg.common.skills.arguments;

import java.util.List;

import org.spongepowered.api.text.message.Message;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.common.skills.AbstractSkillArgument;

/**
 * A SkillArgument that parses and validates input as an integer.
 */
public class IntegerSkillArgument extends AbstractSkillArgument<Integer> {
    private int value;
    private String desc = "num";

    public IntegerSkillArgument(boolean required) {
        super(required);
    }

    public IntegerSkillArgument setDesc(String description) {
        this.desc = description;
        return this;
    }

    @Override
    public String getUsageString(boolean optional) {
        if (optional) {
            return '[' + this.desc + ']';
        } else {
            return '<' + this.desc + '>';
        }
    }

    @Override
    public int matches(SkillCaster caster, String[] allArgs,
                       int startPosition) {
        String arg = allArgs[startPosition];
        try {
            Integer.parseInt(arg);
            return 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // --------------------------------------------------------------

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        try {
            this.value = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            this.value = -Integer.MAX_VALUE;
        }
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        this.value = -Integer.MAX_VALUE;
    }

    @Override
    public Optional<Integer> getValue() {
        return Optional.fromNullable(this.value);
    }

    public void setValue(int val) {
        this.value = val;
    }

    @Override
    public void clean() {
        this.value = -Integer.MAX_VALUE;
    }

    @Override
    public List<Message> tabComplete(SkillCaster caster, String[] allArgs,
                                    int startPosition) {
        return Lists.newArrayList();
    }

}

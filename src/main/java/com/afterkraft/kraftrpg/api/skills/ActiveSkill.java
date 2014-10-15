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

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * See {@link Active}.
 */
public abstract class ActiveSkill extends Skill implements Active {
    private String usage = "";
    SkillArgument[] skillArguments;
    private SkillCaster parsedCaster;

    protected ActiveSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void shutdown() {
    }

    @Override
    public final String getUsage() {
        return this.usage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends SkillArgument> T getArgument(int index) {
        return (T) this.skillArguments[index];
    }

    @Override
    public SkillArgument[] getSkillArguments() {
        return this.skillArguments;
    }

    protected void addSkillArgument(SkillArgument argument) {
        checkArgument(argument != null, "Cannot add a null skill argument!");
        if (this.plugin.isEnabled()) {
            throw new IllegalStateException("KraftRPG is already enabled! Cannot modify Skill Arguments after being enabled.");
        }
        if (this.skillArguments == null) {
            this.skillArguments = new SkillArgument[] { argument };
            return;
        }
        SkillArgument[] newArgs = Arrays.copyOf(this.skillArguments, this.skillArguments.length + 1);
        newArgs[this.skillArguments.length] = argument;
        this.skillArguments = newArgs;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * As not all skills will want this method, subclasses should override it
     * if desired.
     *
     * @param caster
     */
    @Override
    public void onWarmUp(SkillCaster caster) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean parse(SkillCaster caster, String[] strings) {
        this.parsedCaster = caster;
        int stringIndex = 0, argIndex = 0;

        while (stringIndex < strings.length && argIndex < this.skillArguments.length) {
            SkillArgument current = this.skillArguments[argIndex];

            int width = current.matches(caster, strings, stringIndex);
            if (width >= 0) {
                current.parse(caster, strings, stringIndex);
                current.present = true;
                stringIndex += width;
            } else {
                if (current.isOptional()) {
                    current.present = false;
                } else {
                    return false;
                }
            }

            argIndex++;
        }

        while (argIndex < this.skillArguments.length) {
            SkillArgument current = this.skillArguments[argIndex];

            if (current.isOptional()) {
                current.present = false;
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] strings, int startIndex) {
        this.parsedCaster = caster;

        int stringIndex = startIndex, argIndex = 0;
        while (stringIndex < strings.length - 1 && argIndex < this.skillArguments.length) {
            SkillArgument current = this.skillArguments[argIndex];

            int width = current.matches(caster, strings, stringIndex);
            if (width >= 0) {
                stringIndex += width;
                argIndex++;
            } else {
                break;
            }
        }

        if (argIndex == this.skillArguments.length) {
            return ImmutableList.of();
        } else {
            return this.skillArguments[argIndex].tabComplete(caster, strings, stringIndex);
        }
    }

    @Override
    public SkillCastResult checkCustomRestrictions(SkillCaster caster, boolean forced) {
        return SkillCastResult.NORMAL;
    }

    @Override
    public void cleanState(SkillCaster caster) {
        assert caster == this.parsedCaster;

        for (SkillArgument arg : this.skillArguments) {
            arg.clean();
        }
    }

    public SkillCastResult checkCustomRestrictions(SkillCaster caster) {
        return SkillCastResult.NORMAL;
    }
}

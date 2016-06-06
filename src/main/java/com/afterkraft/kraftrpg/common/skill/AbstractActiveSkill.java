/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.common.skill;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import org.spongepowered.api.command.args.ChildCommandElementExecutor;
import org.spongepowered.api.text.Text;

import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skill.Active;
import com.afterkraft.kraftrpg.api.skill.SkillArgument;
import com.afterkraft.kraftrpg.api.skill.SkillCastResult;

/**
 * See {@link Active}.
 */
public abstract class AbstractActiveSkill extends AbstractSkill implements Active {
    SkillArgument<?>[] skillArguments = new SkillArgument<?>[]{};
    private String usage = "";
    private SkillCaster parsedCaster;
    private final ChildCommandElementExecutor childCommandElementExecutor;

    protected AbstractActiveSkill(RpgPlugin plugin, String name, Text description) {
        super(plugin, name, description);
        this.childCommandElementExecutor = new ChildCommandElementExecutor(null);
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
    public final <T extends SkillArgument<?>> Optional<T> getArgument(int index) {
        checkArgument(index >= 0 && index < this.skillArguments.length);
        return Optional.ofNullable((T) this.skillArguments[index]);
    }

    /**
     * {@inheritDoc}  As not all skills will want this method, subclasses should override it if
     * desired.
     *
     * @param caster The caster performing the warmup.
     */
    @Override
    public void onWarmUp(SkillCaster caster) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean parse(SkillCaster caster, String[] strings) {
        this.parsedCaster = caster;
        int stringIndex = 0;
        int argIndex = 0;

        while (stringIndex < strings.length
                && argIndex < this.skillArguments.length) {
            SkillArgument<?> current = this.skillArguments[argIndex];

            int width = current.matches(caster, strings, stringIndex);
            if (width >= 0) {
                current.parse(caster, strings, stringIndex);
                current.setPresent(true);
                stringIndex += width;
            } else {
                if (current.isOptional()) {
                    current.setPresent(false);
                } else {
                    return false;
                }
            }

            argIndex++;
        }

        while (argIndex < this.skillArguments.length) {
            SkillArgument<?> current = this.skillArguments[argIndex];

            if (current.isOptional()) {
                current.setPresent(false);
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Text> tabComplete(SkillCaster caster, String[] strings,
                                  int startIndex) {
        this.parsedCaster = caster;

        int stringIndex = startIndex;
        int argIndex = 0;
        while (stringIndex < strings.length - 1
                && argIndex < this.skillArguments.length) {
            SkillArgument<?> current = this.skillArguments[argIndex];

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
            return this.skillArguments[argIndex]
                    .tabComplete(caster, strings, stringIndex);
        }
    }

    @Override
    public SkillCastResult checkCustomRestrictions(SkillCaster caster,
                                                   boolean forced) {
        return SkillCastResult.SUCCESS;
    }

    @Override
    public void cleanState(SkillCaster caster) {
        assert caster == this.parsedCaster;

        for (SkillArgument<?> arg : this.skillArguments) {
            arg.clean();
        }
    }

    protected final void addSkillArgument(SkillArgument<?> argument) {
        checkState(!this.plugin.isEnabled(),
                   "RpgCommon is already enabled! " + "Cannot modify "
                           + "Skill Arguments after being enabled.");
        SkillArgument<?>[] newArgs = Arrays.copyOf(this.skillArguments,
                                                   this.skillArguments.length
                                                           + 1);
        newArgs[this.skillArguments.length] = argument;
        this.skillArguments = newArgs;
    }

}

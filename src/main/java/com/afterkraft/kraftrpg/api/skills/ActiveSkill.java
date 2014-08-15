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
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.util.SkillRequirement;

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
        Validate.notNull(argument, "Cannot add a null skill argument!");
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

    @Override
    public SkillRequirement getSkillRequirement(SkillCaster caster) {
        return SkillRequirement.TRUE;
    }

    public SkillCastResult checkCustomRestrictions(SkillCaster caster) {
        return SkillCastResult.NORMAL;
    }
}

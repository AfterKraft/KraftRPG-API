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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;
import com.afterkraft.kraftrpg.api.util.Utilities;


public class JEnumSkillArgument<T extends Enum<T>> extends SkillArgument {
    private final T def;
    private final Class<T> clazz;
    private final List<String> names;
    private T choice = null;

    public JEnumSkillArgument(boolean required, Class<T> clazz, T def) throws Exception {
        super(required);
        this.def = def;

        this.clazz = clazz;
        Method values = clazz.getDeclaredMethod("values", (Class[]) null);
        @SuppressWarnings("unchecked")
        T[] allValues = (T[]) values.invoke(null);

        this.names = new ArrayList<String>(allValues.length);
        for (T t : allValues) {
            String temp = t.toString();
            this.names.add(temp);
            if (!temp.equalsIgnoreCase(t.name())) {
                this.names.add(t.name());
            }
        }
    }

    public T getChoice() {
        return this.choice;
    }

    public void setChoice(T c) {
        this.choice = c;
    }

    // --------------------------------------------------------------

    @Override
    public String getUsageString(boolean optional) {
        if (optional) {
            return "[" + this.clazz.getSimpleName().toLowerCase() + "]";
        } else {
            return "<" + this.clazz.getSimpleName().toLowerCase() + ">";
        }
    }

    @Override
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        try {
            Enum.valueOf(this.clazz, arg);
            return 1;
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        try {
            this.choice = Enum.valueOf(this.clazz, arg);
        } catch (IllegalArgumentException e) {
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
        return Utilities.findMatches(allArgs[startPosition], this.names);
    }
}

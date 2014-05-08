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
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;

public class JEnumSkillArgument<T extends Enum<T>> extends SkillArgument {
    private final T def;
    private final Class<T> clazz;
    private final Method values;
    private T choice = null;

    public JEnumSkillArgument(boolean required, Class<T> clazz, T def) throws NoSuchMethodException, SecurityException {
        super(required);
        this.def = def;
        this.clazz = clazz;
        values = clazz.getDeclaredMethod("values", (Class[]) null);
    }

    public T getChoice() {
        return choice;
    }

    // --------------------------------------------------------------

    @Override
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        try {
            Enum.valueOf(clazz, arg);
            return 1;
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        try {
            choice = Enum.valueOf(clazz, arg);
        } catch (IllegalArgumentException e) {
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
        T[] allValues;

        return null;
    }
}

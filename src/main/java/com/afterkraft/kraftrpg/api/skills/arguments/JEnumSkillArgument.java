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

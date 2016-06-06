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
package com.afterkraft.kraftrpg.api.skill;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;


import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * A standard data structure representing a used skill with the SkillCaster and the validated
 * SkillArguments.
 */
public final class SkillUseObject {

    private final WeakReference<SkillCaster> entity;
    private final WeakReference<Skill> skill;
    private final WeakReference<List<SkillArgument<?>>> argument;


    public SkillUseObject(SkillCaster entity, Skill skill,
                          List<SkillArgument<?>> argument) {
        this.entity = new WeakReference<>(entity);
        this.skill = new WeakReference<>(skill);
        this.argument = new WeakReference<>(argument);
    }

    public Optional<SkillCaster> getCaster() {
        return Optional.ofNullable(this.entity.get());
    }

    public Optional<Skill> getSkill() {
        return Optional.ofNullable(this.skill.get());
    }

    public Optional<List<SkillArgument<?>>> getArgument() {
        return Optional.ofNullable(this.argument.get());
    }
}

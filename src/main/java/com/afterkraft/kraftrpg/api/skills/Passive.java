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

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * Represents a passive skill that can not be activated by command or bind.
 * The recommended use of implementation is
 * {@link com.afterkraft.kraftrpg.api.skills.PassiveSkill}
 */
public interface Passive extends ISkill {

    /**
     * Attempts to apply this passive skill to the given
     * {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}
     *
     * @param caster to attempt to apply this passive skill to
     * @return true if successful
     */
    public boolean apply(SkillCaster caster);

    /**
     * Attempts to remove the passive skill from the given SkillCaster. It
     * should be noted that all references to the caster should be forgotten
     * and any update checks should ignore this caster.
     *
     * @param caster to unapply this passive skill to
     */
    public void remove(SkillCaster caster);
}

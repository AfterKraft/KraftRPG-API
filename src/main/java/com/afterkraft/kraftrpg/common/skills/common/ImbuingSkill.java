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
package com.afterkraft.kraftrpg.common.skills.common;

import com.afterkraft.kraftrpg.api.effects.common.Imbuing;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.Active;

/**
 * A standard {@link com.afterkraft.kraftrpg.api.skills.ISkill} that will apply a {@link
 * com.afterkraft.kraftrpg.api.effects.common.Imbuing} effect on skill use. It has a default
 * implementation with the following common abstract skills: <ul> <li>{@link ArrowSkill}</li> </ul>
 */
public interface ImbuingSkill extends Active {

    /**
     * Abstract callback method by all implementations to apply an {@link Imbuing} effect according
     * to the provided SkillCaster.
     *
     * @param caster  The caster using this skill.
     * @param maxUses The configured max uses for the Imbued effect.
     */
    void addImbueEffect(SkillCaster caster, int maxUses);
}

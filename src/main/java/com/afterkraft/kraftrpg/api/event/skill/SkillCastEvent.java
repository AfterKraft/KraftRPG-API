/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.event.skill;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.CauseTracked;
import org.spongepowered.api.util.event.callback.CallbackList;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skill.Skill;

public class SkillCastEvent implements Event, CauseTracked {

    private final Skill skill;
    private final Cause cause;
    private final SkillCaster skillCaster;

    public SkillCastEvent(Skill skill, Cause cause, SkillCaster skillCaster) {
        this.skill = checkNotNull(skill);
        this.cause = checkNotNull(cause);
        this.skillCaster = checkNotNull(skillCaster);
    }

    public Skill getSkill() {
        return this.skill;
    }

    public SkillCaster getSkillCaster() {
        return this.skillCaster;
    }

    // This is being removed.
    @Override
    public CallbackList getCallbacks() {
        return null;
    }

    @Override
    public Cause getCause() {
        return this.cause;
    }
}

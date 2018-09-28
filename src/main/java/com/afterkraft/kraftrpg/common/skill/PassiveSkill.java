/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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

import org.spongepowered.api.text.Text;

import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skill.Passive;

/**
 * Default implementation of a Skill with the Passive interface
 */
public abstract class PassiveSkill extends AbstractSkill implements Passive {

    /**
     * Creates a new passive skill with the given description, name and plugin.
     *
     * @param plugin      The plugin
     * @param name        The name of this passive skill
     * @param description The description of this passive skill
     */
    protected PassiveSkill(RpgPlugin plugin, String name, Text description) {
        super(name, description);
    }

    @Override
    public boolean apply(SkillCaster caster) {
        return false;
    }

    @Override
    public void remove(SkillCaster caster) {

    }

    @Override
    public boolean isInMessageRange(SkillCaster broadcaster,
                                    Champion receiver) {
        return false;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void shutdown() {

    }
}

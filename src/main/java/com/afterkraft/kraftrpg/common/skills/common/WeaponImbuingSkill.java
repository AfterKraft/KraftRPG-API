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

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.effects.common.Imbuing;
import com.afterkraft.kraftrpg.common.effects.common.WeaponImbuingEffect;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.common.skills.ActiveSkill;
import com.afterkraft.kraftrpg.api.skills.SkillCastResult;

/**
 * A default implementation of an {@link ImbuingSkill} that applies a default {@link
 * WeaponImbuingEffect}. This is a common Skill that is intended to be extended and override the
 * following: <ul> <li>{@link #addImbueEffect(SkillCaster, int)}</li> </ul> Extended skills do not
 * need to override {@link #useSkill(SkillCaster)}  The purpose of this skill is to simplify
 * applying a customized {@link Imbuing} effect on a weapon and handle the various uses cases of
 * events and such.
 */
public abstract class WeaponImbuingSkill extends ActiveSkill implements ImbuingSkill {

    protected WeaponImbuingSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
        setDefault(CommonSettings.IMBUED_MAX_USE_COUNT, 1);

    }

    @Override
    public SkillCastResult useSkill(SkillCaster caster) {
        int maxShots = this.plugin.getSkillConfigManager().getUsedIntSetting(caster, this,
                CommonSettings.IMBUED_MAX_USE_COUNT);
        this.addImbueEffect(caster, maxShots);
        return SkillCastResult.NORMAL;
    }

    @Override
    public void addImbueEffect(SkillCaster caster, int maxUses) {
        WeaponImbuingEffect effect =
                new WeaponImbuingEffect(this, caster, "WeaponUseImbuingEffect");
        caster.addEffect(effect);
    }
}

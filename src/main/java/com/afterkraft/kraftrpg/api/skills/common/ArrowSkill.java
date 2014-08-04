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
package com.afterkraft.kraftrpg.api.skills.common;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.effects.common.ProjectileShotEffect;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.ActiveSkill;
import com.afterkraft.kraftrpg.api.skills.SkillCastResult;

/**
 * A default implementation of an {@link ImbuingSkill} that applies a default
 * {@link ProjectileShotEffect}. This is a common ArrowSkill that is intended
 * to be extended and override the following:
 * <ul>
 *     <li>{@link #addImbueEffect(SkillCaster, int)}</li>
 * </ul>
 * Extended skills do not need to override {@link #useSkill(SkillCaster)}
 */
public abstract class ArrowSkill extends ActiveSkill implements ImbuingSkill {

    protected ArrowSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
        setDefault(CommonSettings.IMBUED_MAX_USE_COUNT, 1);

    }

    @Override
    public SkillCastResult useSkill(SkillCaster caster) {
        int maxShots = this.plugin.getSkillConfigManager().getUsedIntSetting(caster, this, CommonSettings.IMBUED_MAX_USE_COUNT);
        this.addImbueEffect(caster, maxShots);
        return SkillCastResult.NORMAL;
    }

    @Override
    public void addImbueEffect(SkillCaster caster, int maxUses) {
        ProjectileShotEffect effect = new ProjectileShotEffect(this, caster, "ArrowShotImbueEffect");
        caster.addEffect(effect);
    }
}

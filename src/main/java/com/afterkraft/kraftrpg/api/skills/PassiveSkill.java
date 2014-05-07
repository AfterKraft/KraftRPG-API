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
package com.afterkraft.kraftrpg.api.skills;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.entity.effects.EffectType;

/**
 * Default implementation of a Skill with the Passive interface
 */
public class PassiveSkill extends Skill implements Passive {

    private String applyText = null;
    private String unapplyText = null;
    private EffectType[] effectTypes = null;

    public PassiveSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void tryApplying(Sentient caster) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public boolean isInMessageRange(Champion broadcaster, Champion receiver) {
        return false;
    }
}
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

import org.bukkit.configuration.Configuration;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * Default implementation of a Skill with the Passive interface
 */
public abstract class PassiveSkill extends Skill implements Passive {

    protected PassiveSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public boolean apply(SkillCaster caster) {
        return false;
    }

    @Override
    public void remove(SkillCaster caster) {

    }

    @Override
    public Configuration getDefaultConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isInMessageRange(SkillCaster broadcaster, Champion receiver) {
        return false;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void shutdown() {

    }
}

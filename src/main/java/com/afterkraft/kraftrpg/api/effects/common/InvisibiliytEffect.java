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
package com.afterkraft.kraftrpg.api.effects.common;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ImmutableSet;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.ExpirableEffect;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.Skill;

public class InvisibiliytEffect extends ExpirableEffect {

    public static final String DEFAULT_APPLY_TEXT = "You've become invisible to the world.";
    public static final String DEFAULT_EXPIRE_TEXT = "You are now visible to the world.";
    public static final Set<EffectType> DEFAULT_INVISIBLE_EFFECTTYPES = EnumSet.of(EffectType.INVISIBILITY, EffectType.BENEFICIAL, EffectType.UNTARGETABLE, EffectType.SILENT_ACTIONS);

    private static final Set<UUID> invisiblePlayers = new HashSet<UUID>();

    public InvisibiliytEffect(Skill skill, Insentient applier, String name, long duration) {
        this(skill, applier, name, duration, DEFAULT_APPLY_TEXT, DEFAULT_EXPIRE_TEXT);
    }

    public InvisibiliytEffect(Skill skill, Insentient applier, String name, long duration, String applyText, String expireText) {
        super(skill, applier, name, duration, applyText, expireText, DEFAULT_INVISIBLE_EFFECTTYPES);
    }

    public static Set<UUID> getInvisiblePlayers() {
        return ImmutableSet.copyOf(invisiblePlayers);
    }



}

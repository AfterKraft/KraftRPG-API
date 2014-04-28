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
package com.afterkraft.kraftrpg.api.entity.effects;

import java.util.WeakHashMap;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.EnterCombatReason;
import com.afterkraft.kraftrpg.api.entity.LeaveCombatReason;
import com.afterkraft.kraftrpg.api.entity.Mage;


public interface Combat extends IEffect {

    public boolean isInCombat();

    public boolean isInCombatWith(LivingEntity target);

    public void enterCombatWith(LivingEntity target, EnterCombatReason reason);

    public void leaveCombatWith(Champion champion, LivingEntity target, LeaveCombatReason reason);

    public void leaveCombatFromDeath(Mage mage);

    public void leaveCombatFromLogout(Mage mage);

    public void leaveCombatFromSuicide(Mage mage);

    public long getTimeLeft();

    public LivingEntity getLastCombatant();

    public WeakHashMap<LivingEntity, EnterCombatReason> getCombatants();

    public void clearCombatants();

    public void resetTimes();

}

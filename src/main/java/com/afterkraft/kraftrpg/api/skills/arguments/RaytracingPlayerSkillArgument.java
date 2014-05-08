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
package com.afterkraft.kraftrpg.api.skills.arguments;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.util.Utilities;
import com.google.common.base.Predicate;

public class RaytracingPlayerSkillArgument extends EntitySkillArgument<Player> {

    public RaytracingPlayerSkillArgument(double maxDistance, Predicate<Player> condition) {
        super(false, maxDistance, Player.class, condition);
    }

    // --------------------------------------------------------------

    @Override
    public String getUsageString(boolean optional) {
        return "[player]";
    }

    @Override
    @SuppressWarnings("deprecation")
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        Player p = Bukkit.getPlayerExact(arg);
        if (p != null) {
            return 1;
        }
        return 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        Player p = Bukkit.getPlayerExact(arg);
        if (p != null) {
            matchedEntity = p;
        } else {
            super.parse(caster, allArgs, startPosition);
        }
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        // do raytracing if arg not specified
        super.parse(caster, null, 0);
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition) {
        return Utilities.matchPlayers(allArgs[startPosition], (Player) caster.getEntity());
    }
}

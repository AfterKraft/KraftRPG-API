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
package com.afterkraft.kraftrpg.api.skills.arguments;

import java.lang.ref.WeakReference;
import java.util.List;

import com.google.common.base.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.util.Utilities;

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
            this.matchedEntity = new WeakReference<Player>(p);
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

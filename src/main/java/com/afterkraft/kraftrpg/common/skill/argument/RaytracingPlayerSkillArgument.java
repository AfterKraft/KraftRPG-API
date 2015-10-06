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
package com.afterkraft.kraftrpg.common.skill.argument;

import java.lang.ref.WeakReference;
import java.util.List;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.util.Utilities;

/**
 * A SkillArgument that performs raytracing calculations in search for a Player and checks the
 * provided Player name is both visible and valid.
 */
public class RaytracingPlayerSkillArgument extends EntitySkillArgument<Player> {

    public RaytracingPlayerSkillArgument(double maxDistance,
                                         Predicate<Player> condition) {
        super(false, maxDistance, Player.class, condition);
    }

    @Override
    public String getUsageString(boolean optional) {
        return "[player]";
    }

    @Override
    public int matches(SkillCaster caster, String[] allArgs,
                       int startPosition) {
        String arg = allArgs[startPosition];
        if (RpgCommon.getServer().getPlayer(arg).isPresent()) {
            return 1;
        }
        return 0;
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        Optional<Player> p = RpgCommon.getServer().getPlayer(arg);
        if (p.isPresent()) {
            this.matchedEntity = new WeakReference<>(p.get());
        } else {
            super.parse(caster, allArgs, startPosition);
        }
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        // do raytracing if arg not specified
        super.parse(caster, new String[]{""}, 0);
    }

    @Override
    public List<Text> tabComplete(SkillCaster caster, String[] allArgs,
                                  int startPosition) {
        ImmutableList.Builder<Text> builder = ImmutableList.builder();
        for (String message : Utilities.matchPlayers(allArgs[startPosition], (Player) caster
                .getEntity())) {
            builder.add(Texts.of(message));
        }
        return builder.build();
    }
}

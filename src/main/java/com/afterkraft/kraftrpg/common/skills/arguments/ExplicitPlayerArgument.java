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
package com.afterkraft.kraftrpg.common.skills.arguments;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.common.skills.AbstractSkillArgument;

/**
 * A SkillArgument that specifies an online Player by name. Useful for player lookups and
 * validation.
 */
public class ExplicitPlayerArgument extends AbstractSkillArgument<Player> {
    private final boolean defaultYou;

    @Nullable
    private WeakReference<Player> matchedPlayer;

    public ExplicitPlayerArgument(boolean required, boolean defaultYou) {
        super(required);
        this.defaultYou = defaultYou;
    }

    @Override
    public String getUsageString(boolean optional) {
        if (optional) {
            if (this.defaultYou) {
                return "[player=you]";
            }
            return "[player]";
        } else {
            return "<player>";
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public int matches(SkillCaster caster, String[] allArgs,
                       int startPosition) {
        String arg = allArgs[startPosition];
        Optional<Player> p = RpgCommon.getServer().getPlayer(arg);
        if (p.isPresent()) {
            return 1;
        }
        return -1;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        Optional<Player> option =
                RpgCommon.getServer().getPlayer(allArgs[startPosition]);
        if (option.isPresent()) {
            this.matchedPlayer = new WeakReference<>(option.get());
        }
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        if (this.defaultYou) {
            this.matchedPlayer = new WeakReference<>((Player) caster
                    .getEntity());
        } else {
            this.matchedPlayer = null;
        }
    }

    @Override
    public Optional<Player> getValue() {
        return this.matchedPlayer != null
                ? Optional.fromNullable(this.matchedPlayer.get())
                : Optional.<Player>absent();
    }

    @Override
    public void clean() {
        this.matchedPlayer = null;
    }

    @Override
    public List<Text> tabComplete(SkillCaster caster, String[] allArgs,
                                  int startPosition) {
        // TODO Auto-generated method stub
        return null;
    }

}

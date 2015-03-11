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
import java.util.List;
import java.util.regex.Matcher;

import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.util.Utilities;
import com.afterkraft.kraftrpg.common.skills.AbstractSkillArgument;

/**
 * A SkillArgument that parses, validates, and returns a specific location. This is comparable to
 * CommandBlocks parsing locations.
 */
public class ExplicitLocationArgument extends AbstractSkillArgument<Location> {
    private static final Location nullLocation =
            new Location(null, new Vector3d());
    @Nullable
    private Location location = nullLocation;

    /**
     * Creates a new location argument.
     *
     * @param required
     */
    public ExplicitLocationArgument(boolean required) {
        super(required);
    }

    public void setLocation(
            @Nullable
            Location loc) {
        this.location = loc;
    }

    @Override
    public String getUsageString(boolean optional) {
        if (optional) {
            return "[~x ~y ~z]";
        } else {
            return "<~x ~y ~z | $>";
        }
    }

    // --------------------------------------------------------------

    @Override
    public int matches(SkillCaster caster, String[] allArgs,
                       int startPosition) {
        String firstArg = allArgs[startPosition];
        if (firstArg.equalsIgnoreCase("$")) {
            this.location = caster.getLocation();
            return 1;
        }
        if (allArgs.length - startPosition < 3) {
            return -1;
        }

        for (int i = 0; i < 3; i++) {
            Matcher matcher =
                    Utilities.locationRegex.matcher(allArgs[startPosition + i]);
            if (!matcher.matches()) {
                return -1;
            }
        }
        return 3;
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String firstArg = allArgs[startPosition];
        if (firstArg.equalsIgnoreCase("$")) {
            this.location = caster.getLocation();
            return;
        }

        /*
        this.location = caster.getLocation().clone();

        double diffX = 0;
        double diffY = 0;
        double diffZ = 0;

        String curArg = allArgs[startPosition];

        if (curArg.startsWith("~")) {
            String number = curArg.substring(1);
            diffX = tryParseNum(number);
        } else {
            this.location.setX(tryParseNum(curArg));
        }

        curArg = allArgs[startPosition + 1];
        if (curArg.startsWith("~")) {
            String number = curArg.substring(1);
            diffY = tryParseNum(number);
        } else {
            this.location.setY(tryParseNum(curArg));
        }

        curArg = allArgs[startPosition + 2];
        if (curArg.startsWith("~")) {
            String number = curArg.substring(1);
            diffZ = tryParseNum(number);
        } else {
            this.location.setZ(tryParseNum(curArg));
        }

        this.location.add(diffX, diffY, diffZ);
        */
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        this.location = nullLocation;
    }

    @Override
    public Optional<Location> getValue() {
        return Optional.fromNullable(this.location);
    }

    @Override
    public void clean() {
        this.location = nullLocation;
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] allArgs,
                                    int startPosition) {
        int argsProvided = allArgs.length - startPosition;
        switch (argsProvided) {
            case 0:
            case 1:
            case 2:
            case 3:
                return ImmutableList.of("~");
            default:
                return ImmutableList.of();
        }
    }

    private double tryParseNum(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

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
import java.util.regex.Matcher;

import com.google.common.collect.ImmutableList;

import org.bukkit.Location;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;
import com.afterkraft.kraftrpg.api.util.Utilities;

public class ExplicitLocationArgument extends SkillArgument {
    private static final Location nullLocation = new Location(null, 0, -256, 0);
    private Location location = nullLocation;

    public ExplicitLocationArgument(boolean required) {
        super(required);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    // --------------------------------------------------------------

    @Override
    public String getUsageString(boolean optional) {
        if (optional) {
            return "[~x ~y ~z]";
        } else {
            return "<~x ~y ~z | $>";
        }
    }

    @Override
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        String firstArg = allArgs[startPosition];
        if (firstArg == "$") {
            location = caster.getLocation();
            return 1;
        }
        if (allArgs.length - startPosition < 3) {
            return -1;
        }

        for (int i = 0; i < 3; i++) {
            Matcher matcher = Utilities.locationRegex.matcher(allArgs[startPosition + i]);
            if (!matcher.matches()) {
                return -1;
            }
        }
        return 3;
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String firstArg = allArgs[startPosition];
        if (firstArg == "$") {
            location = caster.getLocation();
            return;
        }

        location = caster.getLocation().clone();

        double diffX = 0, diffY = 0, diffZ = 0;
        String curArg = allArgs[startPosition];

        if (curArg.startsWith("~")) {
            String number = curArg.substring(1);
            diffX = tryParseNum(number);
        } else {
            location.setX(tryParseNum(curArg));
        }

        curArg = allArgs[startPosition + 1];
        if (curArg.startsWith("~")) {
            String number = curArg.substring(1);
            diffY = tryParseNum(number);
        } else {
            location.setY(tryParseNum(curArg));
        }

        curArg = allArgs[startPosition + 2];
        if (curArg.startsWith("~")) {
            String number = curArg.substring(1);
            diffZ = tryParseNum(number);
        } else {
            location.setZ(tryParseNum(curArg));
        }

        location.add(diffX, diffY, diffZ);
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        location = nullLocation.clone();
    }

    @Override
    public void clean() {
        location = nullLocation;
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition) {
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

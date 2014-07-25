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

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;

public class IntegerSkillArgument extends SkillArgument {
    private int value;
    private String desc = "num";

    public IntegerSkillArgument(boolean required) {
        super(required);
    }

    public IntegerSkillArgument setDesc(String description) {
        this.desc = description;
        return this;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int val) {
        this.value = val;
    }

    // --------------------------------------------------------------

    @Override
    public String getUsageString(boolean optional) {
        if (optional) {
            return '[' + this.desc + ']';
        } else {
            return '<' + this.desc + '>';
        }
    }

    @Override
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        try {
            Integer.parseInt(arg);
            return 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        try {
            this.value = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            this.value = -Integer.MAX_VALUE;
        }
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        this.value = -Integer.MAX_VALUE;
    }

    @Override
    public void clean() {
        this.value = -Integer.MAX_VALUE;
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition) {
        // TODO Auto-generated method stub
        return null;
    }

}

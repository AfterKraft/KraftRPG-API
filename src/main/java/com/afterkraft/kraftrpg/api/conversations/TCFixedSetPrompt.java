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
package com.afterkraft.kraftrpg.api.conversations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.util.StringUtil;

/**
 * FixedSetPrompt is the base class for any prompt that requires a fixed set
 * response from the user.
 */
public abstract class TCFixedSetPrompt extends TCValidatingPrompt {

    protected List<String> fixedSet;

    /**
     * Creates a FixedSetPrompt from a set of strings.
     * <p>
     * foo = new FixedSetPrompt("bar", "cheese", "panda");
     * 
     * @param fixedSet A fixed set of strings, one of which the user must
     *            type.
     */
    public TCFixedSetPrompt(String... fixedSet) {
        super();
        this.fixedSet = Arrays.asList(fixedSet);
    }

    private TCFixedSetPrompt() {
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        return this.fixedSet.contains(input);
    }

    @Override
    public List<String> onTabComplete(ConversationContext context, String fullMessage, String lastToken) {
        List<String> matches = new ArrayList<String>();
        StringUtil.copyPartialMatches(fullMessage, matches, this.fixedSet);
        return matches;
    }

    /**
     * Utility function to create a formatted string containing all the
     * options declared in the constructor.
     * 
     * @return the options formatted like "[bar, cheese, panda]" if bar,
     *         cheese, and panda were the options used
     */
    protected String formatFixedSet() {
        return "[" + StringUtils.join(this.fixedSet, ", ") + "]";
    }
}

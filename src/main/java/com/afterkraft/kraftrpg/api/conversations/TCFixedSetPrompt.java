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
package com.afterkraft.kraftrpg.api.conversations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.util.StringUtil;

import com.google.common.base.Joiner;

/**
 * FixedSetPrompt is the base class for any prompt that requires a fixed set response from the
 * user.
 */
public abstract class TCFixedSetPrompt extends TCValidatingPrompt {

    protected List<String> fixedSet;

    /**
     * Creates a FixedSetPrompt from a set of strings.
     * <p/>
     * foo = new FixedSetPrompt("bar", "cheese", "panda");
     *
     * @param fixedSet A fixed set of strings, one of which the user must type.
     */
    protected TCFixedSetPrompt(String... fixedSet) {
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
    public List<String> onTabComplete(ConversationContext context, String fullMessage,
                                      String lastToken) {
        List<String> matches = new ArrayList<String>();
        StringUtil.copyPartialMatches(fullMessage, matches, this.fixedSet);
        return matches;
    }

    /**
     * Utility function to create a formatted string containing all the options declared in the
     * constructor.
     *
     * @return the options formatted like "[bar, cheese, panda]" if bar, cheese, and panda were the
     * options used
     */
    protected String formatFixedSet() {
        return "[" + Joiner.on(", ").join(this.fixedSet) + "]";
    }
}

package com.afterkraft.kraftrpg.api.conversations;

import org.apache.commons.lang.StringUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     *     type.
     */
    public TCFixedSetPrompt(String... fixedSet) {
        super();
        this.fixedSet = Arrays.asList(fixedSet);
    }

    private TCFixedSetPrompt() {}

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        return fixedSet.contains(input);
    }

    @Override
    public List<String> onTabComplete(ConversationContext context, String fullMessage, String lastToken) {
        List<String> matches = new ArrayList<String>();
        StringUtil.copyPartialMatches(fullMessage, matches, fixedSet);
        return matches;
    }

    /**
     * Utility function to create a formatted string containing all the
     * options declared in the constructor.
     *
     * @return the options formatted like "[bar, cheese, panda]" if bar,
     *     cheese, and panda were the options used
     */
    protected String formatFixedSet() {
        return "[" + StringUtils.join(fixedSet, ", ") + "]";
    }
}

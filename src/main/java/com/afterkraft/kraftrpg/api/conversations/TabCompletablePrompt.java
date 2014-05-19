package com.afterkraft.kraftrpg.api.conversations;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

import java.util.List;

public interface TabCompletablePrompt extends Prompt {
    public List<String> onTabComplete(ConversationContext context, String fullMessage, String lastToken);
}

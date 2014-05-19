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

import org.bukkit.conversations.*;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A subclass of Conversation that provides a getter for the current Prompt.
 */
public class KraftRPGConversation extends Conversation {
    /**
     * Initializes a new Conversation.
     *
     * @param plugin The plugin that owns this conversation.
     * @param forWhom The entity for whom this conversation is mediating.
     * @param firstPrompt The first prompt in the conversation graph.
     */
    public KraftRPGConversation(Plugin plugin, Conversable forWhom, Prompt firstPrompt) {
        super(plugin, forWhom, firstPrompt, new HashMap<Object, Object>());
    }

    /**
     * Initializes a new Conversation.
     *
     * @param plugin The plugin that owns this conversation.
     * @param forWhom The entity for whom this conversation is mediating.
     * @param firstPrompt The first prompt in the conversation graph.
     * @param initialSessionData Any initial values to put in the conversation
     *     context sessionData map.
     */
    public KraftRPGConversation(Plugin plugin, Conversable forWhom, Prompt firstPrompt, Map<Object, Object> initialSessionData) {
        super(plugin, forWhom, firstPrompt, initialSessionData);
    }

    public List<String> tabComplete(String fullText, String lastToken) {
        if (currentPrompt instanceof TabCompletablePrompt) {
            return ((TabCompletablePrompt) currentPrompt).onTabComplete(context, fullText, lastToken);
        }
        return null;
    }

    /**
     * Get the current Prompt.
     *
     * @return current prompt
     */
    public Prompt getCurrentPrompt() {
        return currentPrompt;
    }
}

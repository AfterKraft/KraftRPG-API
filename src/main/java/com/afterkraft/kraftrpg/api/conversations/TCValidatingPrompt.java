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

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 * ValidatingPrompt is the base class for any prompt that requires validation.
 * ValidatingPrompt will keep replaying the prompt text until the user enters
 * a valid response.
 */
public abstract class TCValidatingPrompt implements TabCompletablePrompt {
    public TCValidatingPrompt() {
        super();
    }

    /**
     * Ensures that the prompt waits for the user to provide input.
     *
     * @param context Context information about the conversation.
     * @return True.
     */
    @Override
    public boolean blocksForInput(ConversationContext context) {
        return true;
    }

    /**
     * Accepts and processes input from the user and validates it. If
     * validation fails, this prompt is returned for re-execution, otherwise
     * the next Prompt in the prompt graph is returned.
     *
     * @param context Context information about the conversation.
     * @param input   The input text from the user.
     * @return This prompt or the next Prompt in the prompt graph.
     */
    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if (isInputValid(context, input)) {
            return acceptValidatedInput(context, input);
        } else {
            String failPrompt = getFailedValidationText(context, input);
            if (failPrompt != null) {
                context.getForWhom().sendRawMessage(ChatColor.RED + failPrompt);
            }
            // Redisplay this prompt to the user to re-collect input
            return this;
        }
    }

    /**
     * Override this method to check the validity of the player's input.
     *
     * @param context Context information about the conversation.
     * @param input   The player's raw console input.
     * @return True or false depending on the validity of the input.
     */
    protected abstract boolean isInputValid(ConversationContext context, String input);

    /**
     * Override this method to accept and processes the validated input from
     * the user. Using the input, the next Prompt in the prompt graph should
     * be returned.
     *
     * @param context Context information about the conversation.
     * @param input   The validated input text from the user.
     * @return The next Prompt in the prompt graph.
     */
    protected abstract Prompt acceptValidatedInput(ConversationContext context, String input);

    /**
     * Optionally override this method to display an additional message if the
     * user enters an invalid input.
     *
     * @param context      Context information about the conversation.
     * @param invalidInput The invalid input provided by the user.
     * @return A message explaining how to correct the input.
     */
    protected String getFailedValidationText(ConversationContext context, String invalidInput) {
        return null;
    }
}

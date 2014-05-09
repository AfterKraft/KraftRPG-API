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
package com.afterkraft.kraftrpg.api.listeners;

import com.afterkraft.kraftrpg.api.Manager;

public interface ListenerManager extends Manager {

    /**
     * Adds an AbstractListener to be managed by the plugin. The listener
     * being added should NOT include events already handled by the
     * implementation, KraftRPG.
     * <p/>
     * The added listener must be able to initialize and shutdown in the event
     * of full plugin reload.
     * 
     * @param listener the listener being added to the list of managed
     *            Listeners
     */
    public void addListener(AbstractListener listener);

}

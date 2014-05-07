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
package com.afterkraft.kraftrpg.api.storage;

import java.util.List;
import java.util.UUID;

/**
 * A backend for storage of player data. All methods in this class may be
 * assumed to take a long time, and should be called on threads.
 */
public interface StorageBackend {

    /**
     * Attempt to initialize this StorageBackend. If this method does not
     * return true, or throws an exception, the plugin will be disabled.
     *
     * @return true if no errors
     */
    public boolean initialize() throws Throwable;

    public void shutdown();

    /**
     * Delete the data for the given player.
     *
     * @param uuid UUID of player to delete
     * @return true if player existed
     */
    public boolean removePlayer(UUID uuid);

    /**
     * Write the data for the given player.
     *
     * @param data
     * @return
     */
    public boolean savePlayer(UUID uuid, PlayerData data);

    /**
     * Load the data for the player with the given UUID. If shouldCreate is
     * true, this may not return null.
     *
     * @param uuid UUID of player to load
     * @param shouldCreate whether to allocate space for the player if not
     *            present
     * @return Champion with data, or null
     */
    public PlayerData loadPlayer(UUID uuid, boolean shouldCreate);

    /**
     * Return a list of every UUID with data.
     *
     * @return
     */
    public List<UUID> getAllStoredUsers();
}

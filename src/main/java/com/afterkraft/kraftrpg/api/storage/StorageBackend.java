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
package com.afterkraft.kraftrpg.api.storage;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Optional;

/**
 * A backend for storage of player data. All methods in this class may be assumed to take a long
 * time, and will be called off of the main thread. (Therefore, no Bukkit API except the Scheduler
 * is safe to access.)  The PlayerData objects you are provided in savePlayer() will be
 * snapshots, not the live data objects.
 */
public interface StorageBackend {

    /**
     * Attempt to initialize this StorageBackend. If this method does not return true, or throws an
     * exception, the plugin will be disabled.  (Main thread)
     *
     * @throws Throwable anything if an fatal problem occurs - this will halt the plugin
     */
    void initialize() throws Throwable;

    /**
     * Stop any long-running or maintenance tasks, cleanly terminate connections.  (Main
     * thread)
     */
    void shutdown();

    /**
     * Delete the data for the given player.  Assume different thread.
     *
     * @param uuid UUID of player to delete
     *
     * @return true if player existed
     */
    boolean removePlayer(UUID uuid);

    /**
     * Write the data for the given player.  Assume different thread.
     *
     * @param uuid The player's UUID
     * @param data A PlayerData snapshot object.
     *
     * @return unused, just return true for now
     */
    boolean savePlayer(UUID uuid, PlayerData data);

    /**
     * Load the data for the player with the given UUID. If shouldCreate is true, this may not
     * return null.  Assume different thread. (The implementation does not do this yet, however,
     * so try to keep the time down, please.)
     *
     * @param uuid         UUID of player to load
     * @param shouldCreate whether to allocate space for the player if not present
     *
     * @return Champion with data, or null
     */
    Optional<PlayerData> loadPlayer(UUID uuid, boolean shouldCreate);

    /**
     * Return a list of every UUID with data.  Assume that you're running on a different thread,
     * however you should still be able to do this fairly quickly.
     *
     * @return every UUID stored
     */
    List<UUID> getAllStoredUsers();
}

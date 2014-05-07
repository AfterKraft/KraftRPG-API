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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * Represents a storage implementation to save various things such as
 * Champions and more. This can be implemented by third parties to provide
 * support for other Storage APIs, though loaded dynamically like
 * {@link com.afterkraft.kraftrpg.api.skills.ISkill}s.
 * <p/>
 * RPGStorages should maintain an active cache system so as to reduce the need
 * to save all data Champion data at once.
 */
public abstract class RPGStorage {
    protected final RPGPlugin plugin;
    protected final StorageBackend backend;
    protected final Set<Champion> toSave;

    public RPGStorage(RPGPlugin plugin, StorageBackend backend) {
        this.plugin = plugin;
        this.backend = backend;
        toSave = new HashSet<Champion>();
    }

    /**
     * This constructor skips making a save queue. If you call this
     * constructor, you must override saveChampion().
     */
    protected RPGStorage(RPGPlugin plugin, StorageBackend backend, BukkitTask runningTask) {
        this.plugin = plugin;
        this.backend = backend;
        toSave = null;
    }

    public String getName() {
        return backend.getClass().getSimpleName();
    }

    /**
     * Load the Champion data.
     *
     * @param player the requested Player data
     * @return the loaded Champion instance if data exists, else returns null
     */
    public Champion loadChampion(Player player) {
        Champion temp = backend.loadPlayer(player.getUniqueId());
        if (temp == null)
            return null;

        temp.setPlayer(player);
        return temp;
    }

    /**
     * Saves the given {@link com.afterkraft.kraftrpg.api.entity.Champion}
     * data at some later point.
     */
    public void saveChampion(Champion champion) {
        synchronized (toSave) {
            toSave.add(champion);
        }
    }

    /**
     * Convert all data from the provided StorageBackend to the one currently
     * being used.
     *
     * @param from StorageBackend to convert from
     */
    public void doConversion(StorageBackend from) {
        List<UUID> uuids = from.getAllStoredUsers();

        for (UUID uuid : uuids) {
            backend.savePlayer(from.loadPlayer(uuid));
        }
    }

    public class SavingTask extends BukkitRunnable {
        public void run() {
            Set<Champion> data;
            synchronized (toSave) {
                data = new HashSet<Champion>(toSave);
                toSave.clear();
            }

            for (Champion champion : data) {
                backend.savePlayer(champion);
            }
        }
    }
}

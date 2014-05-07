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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public abstract class StorageFrontend {
    protected final RPGPlugin plugin;
    protected final StorageBackend backend;
    protected final Set<Champion> toSave;
    protected final Set<UUID> ignoredPlayers = new HashSet<UUID>();

    public StorageFrontend(RPGPlugin plugin, StorageBackend backend) {
        this.plugin = plugin;
        this.backend = backend;
        toSave = new HashSet<Champion>();
    }

    /**
     * This constructor skips making a save queue. If you call this
     * constructor, you must override saveChampion().
     */
    protected StorageFrontend(RPGPlugin plugin, StorageBackend backend, BukkitTask runningTask) {
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
    public Champion loadChampion(Player player, boolean shouldCreate) {
        PlayerData data = backend.loadPlayer(player.getUniqueId(), shouldCreate);
        if (data == null) {
            if (!shouldCreate) {
                return null;
            } else {
                data = new PlayerData();
            }
        }

        return plugin.getEntityManager().createChampion(player, data);
    }

    /**
     * Saves the given {@link com.afterkraft.kraftrpg.api.entity.Champion}
     * data at some later point.
     */
    public void saveChampion(Champion champion) {
        if (ignoredPlayers.contains(champion.getPlayer().getUniqueId())) {
            return;
        }
        synchronized (toSave) {
            toSave.add(champion);
        }
    }

    public void flush() {
        synchronized (toSave) {
            for (Champion champion : toSave) {
                backend.savePlayer(champion.getPlayer().getUniqueId(), champion.getData());
            }
        }
    }

    public void shutdown() {
        flush();
        backend.shutdown();
    }

    public void ignorePlayer(UUID uuid) {
        ignoredPlayers.add(uuid);
    }

    public void stopIgnoringPlayer(UUID uuid) {
        ignoredPlayers.remove(uuid);
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
            backend.savePlayer(uuid, from.loadPlayer(uuid, false));
        }
    }

    public class SavingTask extends BukkitRunnable {
        public void run() {
            Map<UUID, PlayerData> data = new HashMap<UUID, PlayerData>();
            synchronized (toSave) {
                for (Champion champion : toSave) {
                    data.put(champion.getPlayer().getUniqueId(), champion.getDataClone());
                }
            }

            for (Map.Entry<UUID, PlayerData> entry : data.entrySet()) {
                backend.savePlayer(entry.getKey(), entry.getValue());
            }
        }
    }
}

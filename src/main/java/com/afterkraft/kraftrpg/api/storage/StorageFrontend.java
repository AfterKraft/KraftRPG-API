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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * The proxy through which the plugin interacts with the StorageBackend.
 * 
 * A StorageFrontend handles things like batching of saves and caching of
 * loaded data. It also performs filtering of NPC data, and conversion between
 * two StorageBackends.
 */
public abstract class StorageFrontend {
    protected final RPGPlugin plugin;
    protected final StorageBackend backend;

    /**
     * This map builds up a to-do list for the saving task, which runs every
     * half-hour.
     */
    protected final Map<UUID, Champion> toSave;

    /**
     * This map is also a to-do list, but is for the editing of offline
     * players.
     */
    protected final Map<UUID, PlayerData> offlineToSave;

    /**
     * Players in this set are silently dropped by saveChampion().
     */
    protected final Set<UUID> ignoredPlayers = new HashSet<UUID>();

    public StorageFrontend(RPGPlugin plugin, StorageBackend backend) {
        this.plugin = plugin;
        this.backend = backend;
        toSave = new HashMap<UUID, Champion>();
        offlineToSave = new HashMap<UUID, PlayerData>();

        new SavingStarterTask().runTaskTimerAsynchronously(plugin, 20 * 60, 20 * 60);
    }

    /**
     * This constructor skips making a save queue. If you call this
     * constructor, you MUST override saveChampion().
     */
    protected StorageFrontend(RPGPlugin plugin, StorageBackend backend, boolean ignored) {
        this.plugin = plugin;
        this.backend = backend;
        toSave = null;
        offlineToSave = null;
    }

    /**
     * The name of a StorageFrontend follows the following format:
     * 
     * <pre>
     * [frontend-name]/[backend-name]
     * </pre>
     * 
     * In the default implementation, the frontend-name is "Default". You
     * should change this if you extend the class.
     * 
     * The backend-name is <code>backend.getClass().getSimpleName()</code>.
     * This should remain the same in your implementation.
     * 
     * @return name of storage format
     */
    public String getName() {
        return "Default/" + backend.getClass().getSimpleName();
    }

    /**
     * Load the Champion data.
     * 
     * @param player the requested Player data
     * @return the loaded Champion instance if data exists, else returns null
     */
    public Champion loadChampion(Player player, boolean shouldCreate) {
        UUID uuid = player.getUniqueId();

        // Check the saving queue for this player
        if (offlineToSave.containsKey(uuid)) {
            PlayerData data = offlineToSave.get(uuid);
            return plugin.getEntityManager().createChampion(player, data);
        }
        if (toSave.containsKey(uuid)) {
            Champion ret = toSave.get(uuid);
            ret.setPlayer(player);
            return ret;
        }

        PlayerData data = backend.loadPlayer(uuid, shouldCreate);
        if (data == null) {
            if (!shouldCreate) {
                return null;
            } else {
                data = new PlayerData();
            }
        }
        data.playerID = player.getUniqueId();
        data.lastKnownName = player.getName();

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

        toSave.put(champion.getPlayer().getUniqueId(), champion);
    }

    public PlayerData loadOfflineChampion(UUID uuid) {
        if (offlineToSave.containsKey(uuid)) {
            return offlineToSave.get(uuid);
        } else if (toSave.containsKey(uuid)) {
            return toSave.get(uuid).getData();
        } else if (Bukkit.getPlayer(uuid) != null) {
            Champion champ = plugin.getEntityManager().getChampion(uuid, true);
            if (champ != null) {
                return champ.getData();
            }
        }
        return backend.loadPlayer(uuid, false);
    }

    public void saveOfflineChampion(UUID uuid, PlayerData data) {
        if (toSave.containsKey(uuid)) {
            // XXX This is bad!
        }

        offlineToSave.put(uuid, data);
    }

    public void shutdown() {
        flush();
        backend.shutdown();
    }

    public void flush() {
        for (Champion champion : toSave.values()) {
            backend.savePlayer(champion.getPlayer().getUniqueId(), champion.getData());
        }
        toSave.clear();
        for (Map.Entry<UUID, PlayerData> entry : offlineToSave.entrySet()) {
            backend.savePlayer(entry.getKey(), entry.getValue());
        }
        offlineToSave.clear();
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

    protected class SavingStarterTask extends BukkitRunnable {
        // Main thread, just like everything else
        public void run() {
            Map<UUID, PlayerData> data = new HashMap<UUID, PlayerData>();

            data.putAll(offlineToSave);
            for (Map.Entry<UUID, Champion> entry : toSave.entrySet()) {
                data.put(entry.getKey(), entry.getValue().getDataClone());
            }
            toSave.clear();
            offlineToSave.clear();

            Bukkit.getScheduler().runTaskAsynchronously(plugin, new SavingWorker(data));
        }
    }

    protected class SavingWorker implements Runnable {
        private Map<UUID, PlayerData> data;

        public SavingWorker(Map<UUID, PlayerData> data) {
            this.data = data;
        }

        // ASYNC
        public void run() {
            for (Map.Entry<UUID, PlayerData> entry : data.entrySet()) {
                backend.savePlayer(entry.getKey(), entry.getValue());
            }
        }
    }
}

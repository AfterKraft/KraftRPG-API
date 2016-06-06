/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * The proxy through which the plugin interacts with the StorageBackend.  A StorageFrontend handles
 * things like batching of saves and caching of loaded data. It also performs filtering of NPC data,
 * and conversion between two StorageBackends.
 */
public abstract class StorageFrontend {
    protected final RpgPlugin plugin;
    protected final StorageBackend backend;

    /**
     * This map builds up a to-do list for the saving task, which runs every half-hour.
     */
    protected final Map<UUID, Champion> toSave;

    /**
     * This map is also a to-do list, but is for the editing of offline players.
     */
    protected final Map<UUID, PlayerData> offlineToSave;

    /**
     * Players in this set are silently dropped by saveChampion().
     */
    protected final Set<UUID> ignoredPlayers = Sets.newHashSet();

    protected StorageFrontend(RpgPlugin plugin, StorageBackend backend) {
        this.plugin = plugin;
        this.backend = backend;
        this.toSave = Maps.newHashMap();
        this.offlineToSave = Maps.newHashMap();

        Sponge.getGame().getScheduler().createTaskBuilder()
            .interval(1, TimeUnit.MINUTES)
            .execute(new SavingStarterTask())
            .name("Saving task")
            .submit(this.plugin);
    }

    /**
     * This constructor skips making a save queue. If you call this constructor, you MUST override
     * saveChampion().
     *
     * @param ignored Something....
     * @param plugin  The plugin instance
     * @param backend The backend storage instance
     */
    protected StorageFrontend(RpgPlugin plugin, StorageBackend backend,
                              boolean ignored) {
        this.plugin = plugin;
        this.backend = backend;
        this.toSave = ImmutableMap.of();
        this.offlineToSave = ImmutableMap.of();
    }

    /**
     * The name of a StorageFrontend follows the following format:
     * <pre>
     * [frontend-name]/[backend-name]
     * </pre>
     * In the default implementation, the frontend-name is "Default". You should change this if you
     * extend the class.  The backend-name is <code>backend.getClass().getSimpleName()</code>. This
     * should remain the same in your implementation.
     *
     * @return name of storage format
     */
    public String getName() {
        return "Default/" + this.backend.getClass().getSimpleName();
    }

    /**
     * Load the Champion data.
     *
     * @param player       the requested Player data
     * @param shouldCreate Whether the champion data should be generated into storage if it doesn't
     *                     exist.
     *
     * @return the loaded Champion instance if data exists, else returns null
     */
    public abstract Optional<Champion> loadChampion(Player player, boolean shouldCreate);
    /**
     * Saves the given {@link com.afterkraft.kraftrpg.api.entity.Champion} data at some later
     * point.
     *
     * @param champion The champion to save
     */
    public void saveChampion(Champion champion) {
        if (this.ignoredPlayers
                .contains(champion.getPlayer().get().getUniqueId())) {
            return;
        }

        this.toSave.put(champion.getPlayer().get().getUniqueId(), champion);
    }

    public Optional<PlayerData> loadOfflineChampion(UUID uuid) {
        if (this.offlineToSave.containsKey(uuid)) {
            return Optional.of(this.offlineToSave.get(uuid));
        } else if (this.toSave.containsKey(uuid)) {
            return Optional.of(this.toSave.get(uuid).getData());
        } else if (RpgCommon.getServer().getPlayer(uuid).isPresent()) {
            if (RpgCommon.getEntityManager().getChampion(uuid).isPresent()) {
                return Optional.of(RpgCommon.getEntityManager()
                                           .getChampion(uuid).get().getData());
            }
        }
        return this.backend.loadPlayer(uuid, false);
    }

    public void saveOfflineChampion(UUID uuid, PlayerData data) {
        if (this.toSave.containsKey(uuid)) {
            // XXX This is bad!
        }

        this.offlineToSave.put(uuid, data);
    }

    public void shutdown() {
        flush();
        this.backend.shutdown();
    }

    public void flush() {
        for (Champion champion : this.toSave.values()) {
            this.backend.savePlayer(champion.getPlayer().get().getUniqueId(),
                                    champion.getData());
        }
        this.toSave.clear();
        for (Map.Entry<UUID, PlayerData> entry : this.offlineToSave
                .entrySet()) {
            this.backend.savePlayer(entry.getKey(), entry.getValue());
        }
        this.offlineToSave.clear();
    }

    public void ignorePlayer(UUID uuid) {
        this.ignoredPlayers.add(uuid);
    }

    public void stopIgnoringPlayer(UUID uuid) {
        this.ignoredPlayers.remove(uuid);
    }

    /**
     * Convert all data from the provided StorageBackend to the one currently being used.
     *
     * @param from StorageBackend to convert from
     */
    public void doConversion(StorageBackend from) {
        List<UUID> uuids = from.getAllStoredUsers();

        for (UUID uuid : uuids) {
            this.backend.savePlayer(uuid, from.loadPlayer(uuid, false).get());
        }
    }

    /**
     * A task designed to periodically save all registered Champions at a prescribed time.
     */
    protected class SavingStarterTask implements Runnable {
        // Main thread, just like everything else
        @Override
        public void run() {
            Map<UUID, PlayerData> data = Maps.newHashMap();

            data.putAll(StorageFrontend.this.offlineToSave);
            for (Map.Entry<UUID, Champion> entry : StorageFrontend.this.toSave
                    .entrySet()) {
                data.put(entry.getKey(), entry.getValue().getData());
            }
            StorageFrontend.this.toSave.clear();
            StorageFrontend.this.offlineToSave.clear();

            Sponge.getScheduler().createTaskBuilder()
                .name("Saving worker")
                .execute(new SavingWorker(data))
                .submit(RpgCommon.getPlugin());
        }
    }

    /**
     * An Asynchronous task designed to perform the save operation of a Champion.
     */
    protected class SavingWorker implements Runnable {
        private Map<UUID, PlayerData> data;

        public SavingWorker(Map<UUID, PlayerData> data) {
            this.data = data;
        }

        // ASYNC
        @Override
        public void run() {
            for (Map.Entry<UUID, PlayerData> entry : this.data.entrySet()) {
                StorageFrontend.this.backend
                        .savePlayer(entry.getKey(), entry.getValue());
            }
        }
    }
}

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
package com.afterkraft.kraftrpg.api;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.storage.StorageBackend;
import com.afterkraft.kraftrpg.api.storage.StorageFrontendFactory;


public final class ExternalProviderRegistration {
    private static boolean pluginEnabled = false;

    private static RPGPlugin plugin;

    private static Map<String, StorageBackend> storageBackends = new HashMap<String, StorageBackend>();
    private static StorageFrontendFactory storageFrontend = new StorageFrontendFactory.DefaultFactory();
    private static Map<String, ISkill> providedSkills = new HashMap<String, ISkill>();

    public static void overrideStorageFrontend(StorageFrontendFactory newQueueManager) {
        check();
        storageFrontend = newQueueManager;
    }

    private static void check() {
        if (pluginEnabled) {
            throw new LateRegistrationException("KraftRPG is already loaded. Please do your registrations in onLoad().");
        }
    }

    public static void registerStorageBackend(StorageBackend storage, String... identifiers) {
        check();
        if (identifiers.length == 0) {
            throw new IllegalArgumentException("Need to provide a config file identifier");
        }

        for (String ident : identifiers) {
            storageBackends.put(ident.toLowerCase(), storage);
        }
    }

    public static void registerSkill(ISkill skill) {
        check();
        providedSkills.put(skill.getName(), skill);
    }

    public static void pluginLoaded(RPGPlugin p) {
        plugin = p;
    }

    public static RPGPlugin getPlugin() {
        return plugin;
    }

    /**
     * You should not call this - KraftRPG will call this once it has loaded.
     */
    public static void finish() {
        pluginEnabled = true;
        providedSkills = ImmutableMap.copyOf(providedSkills);
        storageBackends = ImmutableMap.copyOf(storageBackends);
    }

    public static Map<String, ISkill> getRegisteredSkills() {
        return providedSkills;
    }

    public static Map<String, StorageBackend> getStorageBackendMap() {
        return storageBackends;
    }

    public static StorageFrontendFactory getStorageFrontendOverride() {
        return storageFrontend;
    }
}

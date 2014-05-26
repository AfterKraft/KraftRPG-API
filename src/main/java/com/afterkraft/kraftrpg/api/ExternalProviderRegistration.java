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

import java.util.*;

import com.afterkraft.kraftrpg.api.skills.Skill;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.storage.StorageBackend;
import com.afterkraft.kraftrpg.api.storage.StorageFrontendFactory;


public final class ExternalProviderRegistration {
    private static boolean pluginEnabled = false;

    private static RPGPlugin plugin;

    private static Map<String, StorageBackend> storageBackends = new HashMap<String, StorageBackend>();
    private static StorageFrontendFactory storageFrontend = new StorageFrontendFactory.DefaultFactory();
    private static Set<String> providedSkillNames = new HashSet<String>();
    private static List<ISkill> providedSkills = new ArrayList<ISkill>();

    private static void check() {
        if (pluginEnabled) {
            throw new LateRegistrationException("KraftRPG is already loaded and enabled. Please do your registrations in onLoad().");
        }
    }

    /**
     * Provide a new storage frontend, or revert from another plugin's override
     * to the default.
     *
     * @param newQueueManager custom StorageFrontend, or null for default
     * @throws LateRegistrationException if called after KraftRPG has been loaded
     */
    public static void overrideStorageFrontend(StorageFrontendFactory newQueueManager) {
        check();
        if (newQueueManager == null) {
            storageFrontend = new StorageFrontendFactory.DefaultFactory();
        } else {
            storageFrontend = newQueueManager;
        }
    }

    /**
     * Register a new available StorageBackend with the given configuration
     * identifiers.
     *
     * @param storage     Uninitialized StorageBackend instance
     * @param identifiers Names it can be referenced by in config files and commands
     * @throws LateRegistrationException if called after KraftRPG has been loaded
     * @throws IllegalArgumentException  if no identifiers were provided; if
     *                                   storage is null
     */
    public static void registerStorageBackend(StorageBackend storage, String... identifiers) {
        check();
        if (identifiers.length == 0) {
            throw new IllegalArgumentException("Need to provide a config file identifier");
        }
        if (storage == null) {
            throw new IllegalArgumentException("Attempt to register a null StorageBackend");
        }

        for (String ident : identifiers) {
            storageBackends.put(ident.toLowerCase(), storage);
        }
    }

    /**
     * Register a new skill for KraftRPG to use.
     *
     * @param skill Skill to register
     * @return True if the skill does not have a duplicate name
     * @throws LateRegistrationException if called after KraftRPG has been loaded
     */
    public static boolean registerSkill(ISkill skill) {
        check();
        String name = Skill.getNormalizedName(skill.getName());
        if (!providedSkillNames.add(name)) {
            new IllegalArgumentException("Duplicate skill registration with name " + name)
                    .printStackTrace();
            return false;
        }

        providedSkills.add(skill);
        return true;
    }

    /**
     * Override a previous skill registration with the given skill.
     * <p/>
     * This method will always succeed.
     *
     * @param skill Skill to register
     * @return True if there was a previous registration to override (NOT a success indicator - can generally be ignored)
     * @throws LateRegistrationException if called after KraftRPG has been loaded
     */
    public static boolean overriddeSkill(ISkill skill) {
        check();

        String name = Skill.getNormalizedName(skill.getName());
        if (providedSkillNames.contains(name)) {
            // Remove previous definitions
            ListIterator<ISkill> iter = providedSkills.listIterator();
            while (iter.hasNext()) {
                ISkill sk = iter.next();
                if (Skill.getNormalizedName(sk.getName()).equals(name)) {
                    iter.remove();
                }
            }
            providedSkills.add(skill);
            return true;
        } else {
            providedSkillNames.add(name);
            providedSkills.add(skill);
            return false;
        }
    }

    /**
     * Store the RPGPlugin, for use in checks. KraftRPG will call this in its
     * onLoad() method.
     *
     * @param p plugin
     */
    public static void pluginLoaded(RPGPlugin p) {
        plugin = p;
    }

    public static RPGPlugin getPlugin() {
        return plugin;
    }

    /**
     * You should not call this - KraftRPG will call this in its onEnable(),
     * which closes any new registrations.
     */
    public static void finish() {
        pluginEnabled = true;
        providedSkills = ImmutableList.copyOf(providedSkills);
        storageBackends = ImmutableMap.copyOf(storageBackends);
    }

    public static List<ISkill> getRegisteredSkills() {
        return providedSkills;
    }

    public static Map<String, StorageBackend> getStorageBackendMap() {
        return storageBackends;
    }

    public static StorageFrontendFactory getStorageFrontendOverride() {
        return storageFrontend;
    }
}

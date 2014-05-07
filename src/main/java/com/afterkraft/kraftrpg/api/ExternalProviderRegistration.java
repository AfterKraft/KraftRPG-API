package com.afterkraft.kraftrpg.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.storage.StorageBackend;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public final class ExternalProviderRegistration {
    private static boolean pluginLoaded = false;

    private static Map<String, StorageBackend> storageBackends = new HashMap<String, StorageBackend>();
    private static List<ISkill> providedSkills = new ArrayList<ISkill>();

    public static void registerStorageBackend(StorageBackend storage, String... identifiers) {
        if (pluginLoaded) {
            throw new LateRegistrationException("KraftRPG is already loaded. Please do your registrations in onLoad().");
        }
        if (identifiers.length == 0) {
            throw new IllegalArgumentException("Need to provide a config file identifier");
        }

        for (String ident : identifiers) {
            storageBackends.put(ident, storage);
        }
    }

    public static void registerSkill(ISkill skill) {
        if (pluginLoaded) {
            throw new LateRegistrationException("KraftRPG is already loaded. Please do your registrations in onLoad().");
        }
        providedSkills.add(skill);
    }

    /**
     * You should not call this - KraftRPG will call this once it has loaded.
     */
    public static void finish() {
        pluginLoaded = true;
        providedSkills = ImmutableList.copyOf(providedSkills);
        storageBackends = ImmutableMap.copyOf(storageBackends);
    }

    public static List<ISkill> getRegisteredSkillList() {
        return providedSkills;
    }

    public static Map<String, StorageBackend> getStorageBackendMap() {
        return storageBackends;
    }
}

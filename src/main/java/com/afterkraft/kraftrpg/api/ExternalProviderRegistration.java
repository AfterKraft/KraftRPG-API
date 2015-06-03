/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import com.afterkraft.kraftrpg.api.entity.party.PartyManager;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.afterkraft.kraftrpg.api.storage.StorageBackend;
import com.afterkraft.kraftrpg.api.storage.StorageFrontendFactory;
import com.afterkraft.kraftrpg.common.skill.AbstractSkill;

/**
 * On load registration for various external providers of various services to further customize the
 * implementation of KraftRPG.
 */
public final class ExternalProviderRegistration {
    private static boolean pluginEnabled = false;

    private static RPGPlugin plugin;

    private static Map<String, StorageBackend> storageBackends = Maps.newHashMap();
    private static StorageFrontendFactory storageFrontend = null; // todo figure out storage
    private static Set<String> providedSkillNames = Sets.newHashSet();
    private static List<Skill> providedSkills = Lists.newArrayList();
    private static PartyManager partyManager;

    /**
     * Provide a new storage frontend, or revert from another plugin's override to the default.
     *
     * @param newQueueManager custom StorageFrontend, or null for default
     *
     * @throws LateRegistrationException if called after KraftRPG has been loaded
     */
    public static void overrideStorageFrontend(
            StorageFrontendFactory newQueueManager)
            throws LateRegistrationException {
        check();
        storageFrontend = checkNotNull(newQueueManager);
    }

    private static void check() throws LateRegistrationException {
        if (pluginEnabled) {
            throw new LateRegistrationException(
                    "KraftRPG is already loaded and enabled. Please do your registrations prior "
                            + "to ServerStartingEvent!");
        }
    }

    /**
     * Register a new available StorageBackend with the given configuration identifiers.
     *
     * @param storage     Uninitialized StorageBackend instance
     * @param identifiers Names it can be referenced by in config files and commands
     *
     * @return True if successful
     * @throws LateRegistrationException If called after KraftRPG has been loaded
     */
    public static boolean registerStorageBackend(StorageBackend storage, String... identifiers)
            throws LateRegistrationException {
        check();
        checkArgument(identifiers.length != 0, "Need to provide a config file identifier");
        checkNotNull(storage, "Attempt to register a null StorageBackend");

        for (String ident : identifiers) {
            storageBackends.put(ident.toLowerCase(), storage);
        }
        return true;
    }

    public static void registerPartyManager(PartyManager manager) {
        check();
        partyManager = checkNotNull(manager, "Attempt to register a null PartyManager");
    }

    /**
     * Register a new skill for KraftRPG to use.
     *
     * @param skill Skill to register
     *
     * @return True if the skill does not have a duplicate name
     * @throws LateRegistrationException if called after KraftRPG has been loaded
     */
    public static boolean registerSkill(Skill skill) throws LateRegistrationException {
        check();
        String name = AbstractSkill.getNormalizedName(skill.getName());
        if (!providedSkillNames.add(name)) {
            new IllegalArgumentException("Duplicate skill registration with name "
                                                 + name).printStackTrace();
            return false;
        }

        providedSkills.add(skill);
        return true;
    }

    /**
     * Override a previous skill registration with the given skill.  This method will always
     * succeed.
     *
     * @param skill Skill to register
     *
     * @return True if there was a previous registration to override (NOT a success indicator - can
     * generally be ignored)
     * @throws LateRegistrationException if called after KraftRPG has been loaded
     */
    public static boolean overrideSkill(Skill skill) throws LateRegistrationException {
        check();

        String name = AbstractSkill.getNormalizedName(skill.getName());
        if (providedSkillNames.contains(name)) {
            // Remove previous definitions
            ListIterator<Skill> iter = providedSkills.listIterator();
            while (iter.hasNext()) {
                Skill sk = iter.next();
                if (AbstractSkill.getNormalizedName(sk.getName()).equals(name)) {
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
     * Store the RPGPlugin, for use in checks. KraftRPG will call this in its onLoad() method.
     *
     * @param p plugin
     */
    public static void pluginLoaded(RPGPlugin p) {
        plugin = p;
    }

    /**
     * You should not call this - KraftRPG will call this in its onEnable(), which closes any new
     * registrations.
     */
    public static void finish() {
        pluginEnabled = true;
        providedSkills = ImmutableList.copyOf(providedSkills);
        storageBackends = ImmutableMap.copyOf(storageBackends);
    }


    public static List<Skill> getRegisteredSkills() {
        return providedSkills;
    }

    public static Map<String, StorageBackend> getStorageBackendMap() {
        return storageBackends;
    }

    public static StorageFrontendFactory getStorageFrontendOverride() {
        return storageFrontend;
    }

    public static PartyManager getPartyManager() {
        return partyManager;
    }
}

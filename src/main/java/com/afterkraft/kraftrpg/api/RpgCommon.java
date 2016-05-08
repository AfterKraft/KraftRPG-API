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

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;

import com.afterkraft.kraftrpg.api.entity.EntityManager;
import com.afterkraft.kraftrpg.api.entity.party.PartyService;
import com.afterkraft.kraftrpg.api.role.RoleManager;
import com.afterkraft.kraftrpg.api.skill.SkillConfigManager;
import com.afterkraft.kraftrpg.api.skill.SkillManager;
import com.afterkraft.kraftrpg.api.storage.StorageFrontend;
import com.afterkraft.kraftrpg.api.util.ConfigManager;
import com.afterkraft.kraftrpg.api.util.DamageManager;

/**
 * Utility class providing simple and fast method calls to various managers.
 */
public final class RpgCommon {

    private SkillConfigManager skillConfigManager;
    private EntityManager entityManager;
    private StorageFrontend storageFrontend;
    private ConfigManager configManager;
    private DamageManager damageManager;
    private SkillManager skillManager;
    private RoleManager roleManager;
    private PartyService partyService;
    private RpgPlugin plugin;
    private Logger logger;

    private RpgCommon() {}

    /**
     * Gets the currently used {@link SkillConfigManager}.
     *
     * @return The currently used skill configuration manager
     */
    public static SkillConfigManager getSkillConfigManager() {
        return Holder.INSTANCE.skillConfigManager;
    }

    /**
     * Gets the currently used {@link EntityManager}.
     *
     * @return The currently used entity manager
     */
    public static EntityManager getEntityManager() {
        return Holder.INSTANCE.entityManager;
    }

    /**
     * Gets the currently used {@link SkillConfigManager}.
     *
     * @return The currently used skill configuration manager
     */
    public static StorageFrontend getStorage() {
        return Holder.INSTANCE.storageFrontend;
    }

    /**
     * Gets the currently used {@link ConfigManager}.
     *
     * @return The currently used configuration manager
     */
    public static ConfigManager getConfigurationManager() {
        return Holder.INSTANCE.configManager;
    }

    /**
     * Gets the currently used {@link DamageManager}.
     *
     * @return The currently used damage manager
     */
    public static DamageManager getDamageManager() {
        return Holder.INSTANCE.damageManager;
    }

    /**
     * Gets the currently used {@link SkillManager}.
     *
     * @return The currently used skill manager
     */
    public static SkillManager getSkillManager() {
        return Holder.INSTANCE.skillManager;
    }

    /**
     * Gets the currently used {@link RoleManager}.
     *
     * @return The currently used role manager
     */
    public static RoleManager getRoleManager() {
        return Holder.INSTANCE.roleManager;
    }

    /**
     * Gets the currently used {@link PartyService}.
     *
     * @return The currently used party manager
     */
    public static PartyService getPartyManager() {
        return Holder.INSTANCE.partyService;
    }

    public static Server getServer() {
        return Sponge.getGame().getServer();
    }

    public static RpgPlugin getPlugin() {
        return checkNotNull(Holder.INSTANCE.plugin);
    }

    public static Logger getLogger() {
        return checkNotNull(Holder.INSTANCE.logger);
    }

    private static final class Holder {
        private static final RpgCommon INSTANCE = new RpgCommon();
    }
}

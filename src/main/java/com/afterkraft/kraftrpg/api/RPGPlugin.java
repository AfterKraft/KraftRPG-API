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
package com.afterkraft.kraftrpg.api;

import com.afterkraft.kraftrpg.api.entity.combat.CombatTracker;
import com.afterkraft.kraftrpg.api.entity.EntityManager;
import com.afterkraft.kraftrpg.api.entity.party.PartyManager;
import com.afterkraft.kraftrpg.api.listeners.ListenerManager;
import com.afterkraft.kraftrpg.api.roles.RoleManager;
import com.afterkraft.kraftrpg.api.skills.SkillConfigManager;
import com.afterkraft.kraftrpg.api.skills.SkillManager;
import com.afterkraft.kraftrpg.api.storage.StorageFrontend;
import com.afterkraft.kraftrpg.api.util.ConfigManager;
import com.afterkraft.kraftrpg.api.util.DamageManager;
import com.afterkraft.kraftrpg.api.util.Properties;

/**
 * Standard plugin for KraftRPG. This is provided to avoid leaking implementation that may change
 * between versions.
 */
public interface RPGPlugin {

    /**
     * Stop the plugin from enabling. This should be used when there is a fatal configuration
     * error.
     */
    void cancelEnable();

    /**
     * Gets the currently used {@link SkillConfigManager}.
     *
     * @return The currently used skill configuration manager
     */
    SkillConfigManager getSkillConfigManager();

    /**
     * Gets the currently used {@link CombatTracker}.
     *
     * @return The currently used combat manager
     */
    CombatTracker getCombatTracker();

    /**
     * Gets the currently used {@link EntityManager}.
     *
     * @return The currently used entity manager
     */
    EntityManager getEntityManager();

    /**
     * Gets the currently used {@link SkillConfigManager}.
     *
     * @return The currently used skill configuration manager
     */
    StorageFrontend getStorage();

    /**
     * Gets the currently used {@link ConfigManager}.
     *
     * @return The currently used configuration manager
     */
    ConfigManager getConfigurationManager();

    /**
     * Gets the currently used {@link DamageManager}.
     *
     * @return The currently used damage manager
     */
    DamageManager getDamageManager();

    /**
     * Gets the currently used {@link SkillManager}.
     *
     * @return The currently used skill manager
     */
    SkillManager getSkillManager();

    /**
     * Gets the currently used {@link RoleManager}.
     *
     * @return The currently used role manager
     */
    RoleManager getRoleManager();

    /**
     * Gets the currently used {@link PartyManager}.
     *
     * @return The currently used party manager
     */
    PartyManager getPartyManager();

    /**
     * Gets the currently used {@link Properties}.
     *
     * @return The currently used properties manager
     */
    Properties getProperties();

    /**
     * Gets the currently used {@link ListenerManager}.
     *
     * @return The currently used skill configuration manager
     */
    ListenerManager getListenerManager();

    /**
     * Checks that the plugin is enabled and ready for everything.
     *
     * @return Whether the plugin is enabled or not ready for use
     */
    boolean isEnabled();
}

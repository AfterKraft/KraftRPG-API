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

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import com.afterkraft.kraftrpg.api.effects.EffectManager;
import com.afterkraft.kraftrpg.api.entity.CombatTracker;
import com.afterkraft.kraftrpg.api.entity.EntityManager;
import com.afterkraft.kraftrpg.api.entity.party.PartyManager;
import com.afterkraft.kraftrpg.api.listeners.ListenerManager;
import com.afterkraft.kraftrpg.api.roles.RoleManager;
import com.afterkraft.kraftrpg.api.skills.ISkill;
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
public interface RPGPlugin extends Plugin {

    /**
     * Stop the plugin from enabling. This should be used when there is a fatal configuration
     * error.
     */
    void cancelEnable();

    SkillConfigManager getSkillConfigManager();

    CombatTracker getCombatTracker();

    EntityManager getEntityManager();

    EffectManager getEffectManager();

    StorageFrontend getStorage();

    ConfigManager getConfigurationManager();

    DamageManager getDamageManager();

    SkillManager getSkillManager();

    RoleManager getRoleManager();

    PartyManager getPartyManager();

    Properties getProperties();

    ListenerManager getListenerManager();

    void log(Level level, String msg);

    void logSkillThrowing(ISkill skill, String action, Throwable thrown, Object extraContext);

    void debugLog(Level level, String msg);

    void debugThrow(String sourceClass, String sourceMethod, Throwable thrown);
}

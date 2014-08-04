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

import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.Plugin;

import com.afterkraft.kraftrpg.api.entity.CombatTracker;
import com.afterkraft.kraftrpg.api.entity.EntityManager;
import com.afterkraft.kraftrpg.api.effects.EffectManager;
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


public interface RPGPlugin extends Plugin {
    /**
     * Stop the plugin from enabling. This should be used when there is a
     * fatal configuration error.
     */
    public void cancelEnable();

    public Permission getVaultPermissions();

    public Economy getVaultEconomy();

    public SkillConfigManager getSkillConfigManager();

    public CombatTracker getCombatTracker();

    public EntityManager getEntityManager();

    public EffectManager getEffectManager();

    public StorageFrontend getStorage();

    public ConfigManager getConfigurationManager();

    public DamageManager getDamageManager();

    public SkillManager getSkillManager();

    public RoleManager getRoleManager();

    public PartyManager getPartyManager();

    public Properties getProperties();

    public ListenerManager getListenerManager();

    public void log(Level level, String msg);

    public void logSkillThrowing(ISkill skill, String action, Throwable thrown, Object extraContext);

    public void debugLog(Level level, String msg);

    public void debugThrow(String sourceClass, String sourceMethod, Throwable thrown);
}

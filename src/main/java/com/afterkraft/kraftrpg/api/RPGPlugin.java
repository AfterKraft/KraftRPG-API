package com.afterkraft.kraftrpg.api;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import com.afterkraft.kraftrpg.api.entity.EntityManager;
import com.afterkraft.kraftrpg.api.entity.party.PartyManager;
import com.afterkraft.kraftrpg.api.entity.roles.RoleManager;
import com.afterkraft.kraftrpg.api.spells.SpellConfigManager;
import com.afterkraft.kraftrpg.api.spells.SpellManager;
import com.afterkraft.kraftrpg.api.storage.StorageManager;
import com.afterkraft.kraftrpg.api.util.ConfigManager;
import com.afterkraft.kraftrpg.api.util.DamageManager;
import com.afterkraft.kraftrpg.api.util.Properties;

/**
 * @author gabizou
 */
public interface RPGPlugin extends Plugin {

    public SpellConfigManager getSpellConfigManager();

    public EntityManager getEntityManager();

    public StorageManager getStorageManager();

    public ConfigManager getConfigurationManager();

    public DamageManager getDamageManager();

    public SpellManager getSpellManager();

    public RoleManager getRoleManager();

    public PartyManager getPartyManager();

    public Properties getProperties();

    public void log(Level level, String msg);

    public void debugLog(Level level, String msg);

    public void debugThrow(String sourceClass, String sourceMethod, Throwable thrown);
}

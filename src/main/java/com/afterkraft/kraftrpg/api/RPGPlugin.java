package com.afterkraft.kraftrpg.api;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import com.afterkraft.kraftrpg.api.entity.RPGEntity;
import com.afterkraft.kraftrpg.api.entity.RPGEntityManager;
import com.afterkraft.kraftrpg.api.entity.RPGMonster;
import com.afterkraft.kraftrpg.api.entity.RPGPlayer;
import com.afterkraft.kraftrpg.api.entity.effects.RPGEffect;
import com.afterkraft.kraftrpg.api.spells.SpellManager;

/**
 * Author: gabizou
 */
public interface RPGPlugin extends Plugin {

    public Class<? extends RPGEffect> getEffectClass();

    public Class<? extends RPGEntityManager> getEntityManagerClass();

    public Class<? extends SpellManager> getSpellManagerClass();

    public void log(Level level, String msg);

    public void debugLog(Level level, String msg);

    public void debugThrow(String sourceClass, String sourceMethod, Throwable thrown);
}

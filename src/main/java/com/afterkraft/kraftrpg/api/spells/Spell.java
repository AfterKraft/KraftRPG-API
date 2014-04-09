package com.afterkraft.kraftrpg.api.spells;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Entity;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.RPGPlayer;

/**
 * Author: gabizou
 */
public abstract class Spell {

    public final RPGPlugin plugin;
    private final Configuration defaultConfig = new MemoryConfiguration();
    private static Random random = new Random();
    private final Set<SpellType> spellTypes = EnumSet.noneOf(SpellType.class);

    public Spell(RPGPlugin plugin, String name) {
        this.plugin = plugin;
    }

    public abstract String getDescription(RPGPlayer rpgPlayer);



    public final void addSpellTarget(Entity entity, RPGPlayer player) {

    }

    public boolean isType(SpellType type) {
        return spellTypes.contains(type);
    }

    public class SpellArgument {

        protected SpellArgument() { }


    }
}

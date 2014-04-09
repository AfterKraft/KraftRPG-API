package com.afterkraft.kraftrpg.api.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.spells.SpellBind;

/**
 * Author: gabizou
 */
public abstract class RPGPlayer extends RPGEntity {

    private Map<Material, SpellBind> binds = new ConcurrentHashMap<Material, SpellBind>();


    protected RPGPlayer(RPGPlugin plugin, Player player){
        super(plugin, player, player.getName());
    }

    public final Player getPlayer() {
        return (Player) this.getEntity();
    }


}

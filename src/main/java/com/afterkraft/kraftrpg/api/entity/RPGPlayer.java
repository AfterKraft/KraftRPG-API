package com.afterkraft.kraftrpg.api.entity;

import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.RPGPlugin;

/**
 * Author: gabizou
 */
public abstract class RPGPlayer extends RPGEntity {

    protected RPGPlayer(RPGPlugin plugin, Player player){
        super(plugin, player, player.getName());
    }

    public final Player getPlayer() {
        return (Player) this.getEntity();
    }


}

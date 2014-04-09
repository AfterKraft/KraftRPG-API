package com.afterkraft.kraftrpg.api.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.afterkraft.storage.api.CommonStorage;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.RPGMonster;
import com.afterkraft.kraftrpg.api.entity.RPGPlayer;

/**
 * Author: gabizou
 */
public abstract class RPGStorage implements CommonStorage {

    protected final RPGPlugin plugin;
    private final String name;
    private List<UUID> playersNotToSave = new ArrayList<UUID>();

    public RPGStorage(final RPGPlugin plugin, final String name) {
        this.plugin = plugin;
        this.name = name;

    }

    //--- KraftRPG Specific methods that must be implemented

    public abstract boolean loadRPGPlayer(final Player player);

    public abstract boolean saveRPGPlayer(final RPGPlayer player);


    //--- CommonStorage methods that don't need to be implemented

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public void addPlayerNotToSave(Player player) {
        if (player == null) {
            return;
        }
        playersNotToSave.add(player.getUniqueId());
    }

    @Override
    public boolean isPlayerNotToSave(Player player) {
        return player != null && playersNotToSave.contains(player.getUniqueId());
    }

    @Override
    public void removePlayerNotToSave(Player player) {
        if (player == null) {
            return;
        }
        playersNotToSave.remove(player.getUniqueId());
    }

    @Override
    public List<UUID> getPlayerNotToSave() {
        return Collections.unmodifiableList(playersNotToSave);
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public void savePlayer(Player player) {

    }
}
